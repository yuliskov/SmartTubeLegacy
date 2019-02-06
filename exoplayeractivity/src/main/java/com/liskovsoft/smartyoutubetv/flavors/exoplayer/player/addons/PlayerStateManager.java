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
public class PlayerStateManager extends PlayerStateManagerBase {
    private static final String TAG = PlayerStateManager.class.getSimpleName();
    private static final int RENDERER_INDEX_VIDEO = PlayerCoreFragment.RENDERER_INDEX_VIDEO;
    private static final int RENDERER_INDEX_SUBTITLE = PlayerCoreFragment.RENDERER_INDEX_SUBTITLE;
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
        super(playerFragment.getActivity());
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
        //waitCodecInit();

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
        //waitCodecInit();

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
        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();

        if (info == null) {
            return;
        }

        TrackGroupArray groupArray = info.getTrackGroups(RENDERER_INDEX_SUBTITLE);

        Pair<Integer, Integer> trackGroupAndIndex = findProperSubtitleTrack(groupArray);

        restoreTrackGroupAndIndex(trackGroupAndIndex, RENDERER_INDEX_SUBTITLE);
    }

    private Pair<Integer, Integer> findProperSubtitleTrack(TrackGroupArray groupArray) {
        mDefaultSubtitleLang = null;
        String subName = mPrefs.getSubtitleLang();
        if (subName == null) { // default track selected
            return null;
        }

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
        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();

        if (info == null) {
            return null;
        }

        TrackGroupArray groupArray = info.getTrackGroups(RENDERER_INDEX_VIDEO);

        Set<MyFormat> fmts = findProperVideos(groupArray);
        MyFormat fmt = filterHighestVideo(fmts);

        if (fmt == null) {
            mDefaultTrackId = null;
            return null;
        }

        mDefaultTrackId = fmt.id;
        return fmt.pair;
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
        if (isTrackChanged && (Helpers.isDash(trackId) || isDefaultQualitySelected())) {
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

    private void restoreTrackGroupAndIndex(Pair<Integer, Integer> trackGroupAndIndex, int rendererIndex) {
        if (trackGroupAndIndex == null) {
            return;
        }

        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();
        TrackGroupArray trackGroupArray = info.getTrackGroups(rendererIndex);
        SelectionOverride override = new SelectionOverride(trackGroupAndIndex.first, trackGroupAndIndex.second);
        mSelector.setParameters(mSelector.buildUponParameters().setSelectionOverride(rendererIndex, trackGroupArray, override));
    }
}
