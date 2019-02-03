package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerCoreFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;
import com.google.android.exoplayer2.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Restores saved position, quality and subtitles of the video
 */
public class PlayerStateManager {
    private static final String TAG = PlayerStateManager.class.getSimpleName();
    private static final String CODEC_AVC = "avc";
    private static final String CODEC_VP9 = "vp9";
    private static final String CODEC_VP9_HDR = "vp9.2";
    private static final int RENDERER_INDEX_VIDEO = PlayerCoreFragment.RENDERER_INDEX_VIDEO;
    private static final int RENDERER_INDEX_SUBTITLE = PlayerCoreFragment.RENDERER_INDEX_SUBTITLE;
    private static final int HEIGHT_PRECISION_PX = 10; // ten-pixel precision
    private static final float FPS_PRECISION = 10; // fps precision
    private static final long MIN_PERSIST_DURATION_MILLIS = 5 * 60 * 1000; // don't save if total duration < 5 min (most of songs)
    private static final long MAX_TRAIL_DURATION_MILLIS = 3 * 1000; // don't save if 3 sec of unseen video remains
    private static final long MAX_START_DURATION_MILLIS = 30 * 1000; // don't save if video just starts playing < 30 sec
    private static final long DECODER_INIT_TIME_MS = 1_000;
    private final ExoPlayerBaseFragment mPlayerFragment;
    private final SimpleExoPlayer mPlayer;
    private final DefaultTrackSelector mSelector;
    private ExoPreferences mPrefs;
    private String mDefaultTrackId;
    private String mDefaultSubtitleLang;

    public PlayerStateManager(ExoPlayerBaseFragment playerFragment, SimpleExoPlayer player, DefaultTrackSelector selector) {
        mPlayerFragment = playerFragment;
        mPlayer = player;
        mSelector = selector;
        mPrefs = new ExoPreferences(playerFragment.getActivity());
    }

    /**
     * Don't restore track position immediately!!<br/>
     * Instead use this method inside {@link Player.EventListener#onPlayerStateChanged onPlayerStateChanged} event<br/>
     * All earlier calls might produce an error because {@link MappedTrackInfo#getTrackGroups(int) getTrackGroups} could be null
     */
    public void restoreState() {
        waitCodecInit();

        restoreVideoTrack();
        restoreSubtitleTrack();
        restoreTrackPosition();
    }

    /**
     * Don't restore track position immediately!!<br/>
     * Instead use this method inside {@link Player.EventListener#onPlayerStateChanged onPlayerStateChanged} event<br/>
     * All earlier calls might produce an error because {@link MappedTrackInfo#getTrackGroups(int) getTrackGroups} could be null
     */
    public void restoreStatePartially() {
        waitCodecInit();

        restoreVideoTrack();
    }

    /**
     * Some decoders might not have enough time for initialization<br/>
     * <a href="https://github.com/yuliskov/SmartYouTubeTV/issues/203">GitHub issue #203</a><br/>
     * <a href="https://github.com/yuliskov/SmartYouTubeTV/issues/205">GitHub issue #205</a><br/>
     */
    private void waitCodecInit() {
        try {
            Thread.sleep(DECODER_INIT_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void restoreSubtitleTrack() {
        Pair<Integer, Integer> trackGroupAndIndex = findProperSubtitleTrack();

        restoreTrackGroupAndIndex(trackGroupAndIndex, RENDERER_INDEX_SUBTITLE);
    }

    private Pair<Integer, Integer> findProperSubtitleTrack() {
        mDefaultSubtitleLang = null;
        String subName = mPrefs.getSubtitleLang();
        if (subName == null) { // default track selected
            return null;
        }

        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();
        TrackGroupArray groupArray = info.getTrackGroups(RENDERER_INDEX_SUBTITLE);

        // search the same tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);

                if (subName.equals(format.language)) {
                    mDefaultSubtitleLang = format.language;
                    return new Pair<>(j, i);
                }
            }
        }

        return null;
    }

    private void restoreTrackPosition() {
        String title = mPlayerFragment.getMainTitle() + mPlayer.getDuration(); // create something like hash
        long pos = mPrefs.getPosition(title);

        if (pos != C.TIME_UNSET){
            mPlayer.seekTo(pos);
        }
    }

    /**
     * Restore track from prefs
     */
    private void restoreVideoTrack() {
        if (trackGroupIsEmpty()) {
            return;
        }

        Pair<Integer, Integer> trackGroupAndIndex = findProperVideoTrack();

        restoreTrackGroupAndIndex(trackGroupAndIndex, RENDERER_INDEX_VIDEO);
    }

    /**
     * Searches for the track by id and height.
     * @return pair consisted from track group index and track number
     */
    private Pair<Integer, Integer> findProperVideoTrack() {
        Set<MyFormat> fmts = findProperVideos();
        MyFormat fmt = filterHighestVideo(fmts);

        if (fmt == null) {
            mDefaultTrackId = null;
            return null;
        }

        mDefaultTrackId = fmt.id;
        return fmt.pair;
    }

    /**
     * Try to find a candidate(s) that match preferred settings
     * @return one or more matched video tracks as {@link MyFormat}
     */
    private Set<MyFormat> findProperVideos() {
        String trackId = mPrefs.getSelectedTrackId();
        int trackHeight = mPrefs.getSelectedTrackHeight();
        float trackFps = mPrefs.getSelectedTrackFps();
        String trackCodecs = mPrefs.getSelectedTrackCodecs();
        boolean preferHdr = PlayerUtil.isHdrCodec(trackCodecs);

        Set<MyFormat> result = new HashSet<>();
        Set<MyFormat> backed = new HashSet<>();

        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();
        TrackGroupArray groupArray = info.getTrackGroups(RENDERER_INDEX_VIDEO);

        // search the same tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);

                if (PlayerUtil.isPreferredFormat(mPlayerFragment.getActivity(), format)) {
                    MyFormat myFormat = new MyFormat(format, new Pair<>(j, i));

                    if (tracksEquals(format.id, trackId)) { // strict match found, stop search
                        result.clear();
                        result.add(myFormat);
                        break;
                    }

                    boolean codecMatch = trackCodecs == null || codecEquals(format.codecs, trackCodecs);
                    boolean heightMatch = heightEquals(format.height, trackHeight) || format.height <= trackHeight;
                    boolean fpsMatch = fpsEquals(format.frameRate, trackFps);
                    boolean hdrMatch = PlayerUtil.isHdrCodec(format.codecs) == preferHdr;

                    if (codecMatch && heightMatch && fpsMatch && hdrMatch) {
                        result.add(myFormat);
                        continue;
                    }

                    if (heightMatch) {
                        backed.add(myFormat);
                    }
                }
            }
        }

        return result.isEmpty() ? backed : result;
    }

    /**
     * Select format with same codec and highest bitrate. All track already have the same height.
     * @param fmts source (cannot be null)
     * @return best format (cannot be null)
     */
    private MyFormat filterHighestVideo(Set<MyFormat> fmts) {
        //String codecName = AVC_CODEC;
        MyFormat result = null;

        // select format with same codec and highest bitrate
        for (MyFormat fmt : fmts) {
            if (result == null) {
                result = fmt;
                continue;
            }

            // there is two levels of preference
            // by codec (e.g. avc), height, fps, bitrate

            //if (!codecEquals(result.codecs, codecName) && codecEquals(fmt.codecs, codecName)) {
            //    result = fmt;
            //    continue;
            //}
            //
            //// non-avc
            //if (codecEquals(result.codecs, codecName) && !codecEquals(fmt.codecs, codecName)) {
            //    continue;
            //}

            // don't relay on bitrate, since some video have improper bitrate measurement
            // because of this, I've add frameRate comparision
            if (result.height < fmt.height) {
                result = fmt;
                continue;
            }

            // don't relay on bitrate, since some video have improper bitrate measurement
            // because of this, I've add frameRate comparision
            if ((result.frameRate < fmt.frameRate) && (result.height == fmt.height)) {
                result = fmt;
                continue;
            }

            // don't relay on bitrate, since some video have improper bitrate measurement
            // because of this, I've add frameRate comparision
            if ((result.bitrate < fmt.bitrate) && (result.height == fmt.height)) {
                result = fmt;
            }
        }
        return result;
    }

    private boolean heightEquals(int leftHeight, int rightHeight) {
        return Math.abs(leftHeight - rightHeight) <= HEIGHT_PRECISION_PX;
    }

    private boolean fpsEquals(float leftFps, float rightFps) {
        return Math.abs(leftFps - rightFps) <= FPS_PRECISION;
    }

    private boolean tracksEquals(String leftTrackId, String rightTrackId) {
        if (leftTrackId == null || rightTrackId == null) {
            return false;
        }

        return leftTrackId.equals(rightTrackId);
    }

    /**
     * Check that there are a video tracks present
     * @return true if yes, or false if no
     */
    private boolean trackGroupIsEmpty() {
        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();

        if (info == null) {
            return true;
        }

        TrackGroupArray groupArray = info.getTrackGroups(RENDERER_INDEX_VIDEO);

        return groupArray == null || groupArray.length == 0;
    }

    public void persistState() {
        if (mPrefs == null) {
            return;
        }

        persistTrackParams();
        persistTrackPosition();
        persistSubtitleTrack();
    }

    private void persistSubtitleTrack() {
        MappedTrackInfo trackInfo = mSelector.getCurrentMappedTrackInfo();
        if (trackInfo == null) {
            return;
        }
        TrackGroupArray groups = trackInfo.getTrackGroups(RENDERER_INDEX_SUBTITLE);
        SelectionOverride override = mSelector.getParameters().getSelectionOverride(RENDERER_INDEX_SUBTITLE, groups);
        if (override == null && mDefaultSubtitleLang != null) { // user switched the track to auto mode
            mPrefs.setSubtitleLang(null);
            return;
        }

        if (override == null) {
            return;
        }

        Format fmt = getFormatFromOverride(groups, override);

        if (!Helpers.equals(fmt.language, mDefaultSubtitleLang)) {
            mPrefs.setSubtitleLang(fmt.language);
        }
    }

    private Format getFormatFromOverride(TrackGroupArray groups, SelectionOverride override) {
        TrackGroup selectedGroup = groups.get(override.groupIndex);
        return selectedGroup.getFormat(override.tracks[0]);
    }

    private void persistTrackPosition() {
        long duration = mPlayer.getDuration();
        if (duration < MIN_PERSIST_DURATION_MILLIS) {
            return;
        }
        long position = mPlayer.getCurrentPosition();
        String title = mPlayerFragment.getMainTitle() + duration; // create something like hash
        boolean almostAllVideoSeen = (duration - position) < MAX_TRAIL_DURATION_MILLIS;
        boolean isVideoJustStarts = position < MAX_START_DURATION_MILLIS;
        if (almostAllVideoSeen || isVideoJustStarts) {
            mPrefs.resetPosition(title);
        } else {
            mPrefs.setPosition(title, position);
        }
    }

    private void persistTrackParams() {
        String trackId = extractCurrentTrackId();
        int height = extractCurrentTrackHeight();
        String codecs = extractCurrentTrackCodecs(); // there is a bug (null codecs) on some Live formats (strange id == "1/27")
        float fps = extractCurrentTrackFps();

        // mDefaultTrackId: usually this happens when video does not contain preferred format
        boolean isTrackChanged = !Helpers.equals(trackId, mDefaultTrackId);

        // There is a bug (null codecs) on some Live formats (strange id == "1/27")
        if (isTrackChanged && Helpers.isDash(trackId)) {
            mPrefs.setSelectedTrackId(trackId);
            mPrefs.setSelectedTrackHeight(height);
            mPrefs.setSelectedTrackCodecs(codecs);
            mPrefs.setSelectedTrackFps(fps);
        }
    }

    private String extractCurrentTrackId() {
        if (isDefaultQualitySelected()) {
            return null;
        }

        Format videoFormat = mPlayer.getVideoFormat();

        if (videoFormat == null) {
            return null;
        }

        return videoFormat.id;
    }

    private int extractCurrentTrackHeight() {
        if (isDefaultQualitySelected()) {
            return 0;
        }

        Format videoFormat = mPlayer.getVideoFormat();

        if (videoFormat == null) {
            return 0;
        }

        return videoFormat.height;
    }

    private String extractCurrentTrackCodecs() {
        if (isDefaultQualitySelected()) {
            return null;
        }

        Format videoFormat = mPlayer.getVideoFormat();

        if (videoFormat == null) {
            return null;
        }

        return videoFormat.codecs;
    }

    private float extractCurrentTrackFps() {
        if (isDefaultQualitySelected()) {
            return 0;
        }

        Format videoFormat = mPlayer.getVideoFormat();

        if (videoFormat == null) {
            return 0;
        }

        return videoFormat.frameRate;
    }

    private boolean isDefaultQualitySelected() {
        if (mSelector == null) {
            return false;
        }

        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();

        if (info == null) {
            return false;
        }

        TrackGroupArray groups = info.getTrackGroups(RENDERER_INDEX_VIDEO);

        if (groups == null) {
            return false;
        }

        SelectionOverride override = mSelector.getParameters().getSelectionOverride(RENDERER_INDEX_VIDEO, groups);

        return override == null;
    }

    private boolean codecEquals(String codec1, String codec2) {
        return Helpers.equals(codecShort(codec1), codecShort(codec2));
    }

    private String codecShort(String codecName) {
        if (codecName == null) {
            return null;
        }

        // simplify codec name: use avc instead of avc.111333
        if (codecName.contains(CODEC_AVC)) {
            codecName = CODEC_AVC;
        } else if (codecName.contains(CODEC_VP9)) {
            codecName = CODEC_VP9;
        }

        return codecName;
    }

    private void restoreTrackGroupAndIndex(Pair<Integer, Integer> trackGroupAndIndex, int rendererIndex) {
        if (trackGroupAndIndex == null) {
            return;
        }

        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();
        TrackGroupArray trackGroupArray = info.getTrackGroups(rendererIndex);
        SelectionOverride override = new SelectionOverride(trackGroupAndIndex.first, trackGroupAndIndex.second);
        mSelector.setParameters(mSelector.buildUponParameters().setSelectionOverride(rendererIndex, trackGroupArray, override));
    }

    /**
     * Simple wrapper around {@link Format} class
     */
    private class MyFormat {
        public final String id;
        public final int bitrate;
        public final float frameRate;
        public final String codecs;
        public final int height;
        public final int width;
        public final Pair<Integer, Integer> pair;

        public MyFormat(Format format, Pair<Integer, Integer> pair) {
            id = format.id;
            bitrate = format.bitrate;
            frameRate = format.frameRate;
            codecs = format.codecs;
            width = format.width;
            height = format.height;
            this.pair = pair;
        }
    }
}
