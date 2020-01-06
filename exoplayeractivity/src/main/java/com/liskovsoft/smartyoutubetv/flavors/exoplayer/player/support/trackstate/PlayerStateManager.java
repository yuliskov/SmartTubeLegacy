package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.trackstate;

import android.os.Handler;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerCoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Restores saved position, quality and subtitles of the video
 */
public class PlayerStateManager extends PlayerStateManagerBase {
    private static final String TAG = PlayerStateManager.class.getSimpleName();
    private static final int RENDERER_INDEX_VIDEO = PlayerCoreFragment.RENDERER_INDEX_VIDEO;
    private static final int RENDERER_INDEX_SUBTITLE = PlayerCoreFragment.RENDERER_INDEX_SUBTITLE;
    private static final long MIN_PERSIST_DURATION_MILLIS = 5 * 60 * 1000; // don't save if total duration < 5 min (most of songs)
    private static final long MAX_TRAIL_DURATION_MILLIS = 10 * 1000; // don't save if 10 sec of unseen video remains
    private static final long MAX_START_DURATION_MILLIS = 30 * 1000; // don't save if video just starts playing < 30 sec
    private static final long DECODER_INIT_TIME_MS = 1_000;
    private final ExoPlayerBaseFragment mPlayerFragment;
    private SimpleExoPlayer mPlayer;
    private DefaultTrackSelector mSelector;
    private static List<String> sRestored = new ArrayList<>();

    public PlayerStateManager(ExoPlayerBaseFragment playerFragment, SimpleExoPlayer player, DefaultTrackSelector selector) {
        super(playerFragment.getActivity());

        mPlayerFragment = playerFragment;
        mPlayer = player;
        mSelector = selector;
    }

    /**
     * Don't restore track position immediately!!<br/>
     * Instead use this method inside {@link Player.EventListener#onPlayerStateChanged onPlayerStateChanged} event<br/>
     * All earlier calls might produce an error because {@link MappedTrackInfo#getTrackGroups(int) getTrackGroups} could be null
     */
    public void restoreState() {
        if (mPlayer == null || mSelector == null) {
            Log.d(TAG, "Not fully initialized!");
            return;
        }

        restoreVideoTrack();
        restoreAudioTrack();
        restoreSubtitleTrack();
        restoreTrackPosition();
    }

    /**
     * Don't restore track position immediately!!<br/>
     * Instead use this method inside {@link Player.EventListener#onPlayerStateChanged onPlayerStateChanged} event<br/>
     * All earlier calls might produce an error because {@link MappedTrackInfo#getTrackGroups(int) getTrackGroups} could be null
     */
    public void restoreStatePartially() {
        if (mPlayer == null || mSelector == null) {
            Log.d(TAG, "Not fully initialized!");
            return;
        }

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
        MyFormat fmt = findProperSubtitleFormat(groupArray);

        if (fmt == null) {
            return null;
        }

        return fmt.pair;
    }

    private void restoreTrackPosition() {
        long posPercents = CommonApplication.getPreferences().getCurrentVideoPosition();

        // prevent from restoring same data on two different video
        CommonApplication.getPreferences().setCurrentVideoPosition(-1);

        Log.d(TAG, "Real position of the video in percents: " + posPercents);

        long posMs;

        long duration = mPlayer.getDuration();
        String title = mPlayerFragment.getMainTitle() + duration; // create something like hash

        if (posPercents < 0 || posPercents > 97 || sRestored.contains(title)) { // app just started, video opened
            posMs = findProperVideoPosition(title);
        } else {
            posMs = (duration / 100) * posPercents;
            sRestored.add(title); // restore from web once, then use local data
        }

        if (posMs > 0 && posMs < (duration - MAX_TRAIL_DURATION_MILLIS)) {
            mPlayer.seekTo(posMs);
        }
    }

    private void restoreAudioTrack() {
        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();

        if (info == null) {
            return;
        }

        TrackGroupArray groupArray = info.getTrackGroups(ExoPlayerFragment.RENDERER_INDEX_AUDIO);

        MyFormat audioFormat = findProperAudioFormat(groupArray);

        if (audioFormat == null) {
            return;
        }

        Pair<Integer, Integer> trackGroupAndIndex = audioFormat.pair;

        restoreTrackGroupAndIndex(trackGroupAndIndex, ExoPlayerFragment.RENDERER_INDEX_AUDIO);
    }

    /**
     * Restore track from prefs
     */
    private void restoreVideoTrack() {
        new Handler().postDelayed(this::restoreVideoTrackReal, DECODER_INIT_TIME_MS);
    }

    private void restoreVideoTrackReal() {
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

        MyFormat fmt = findPreferredVideoFormat(groupArray);

        if (fmt == null) {
            return null;
        }

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
        if (mPlayer == null || mSelector == null) {
            Log.d(TAG, "Not fully initialized!");
            return;
        }

        Format videoFormat = mPlayer.getVideoFormat();
        Format audioFormat = mPlayer.getAudioFormat();

        if (videoFormat == null || audioFormat == null) {
            return;
        }

        MyFormat video = isDefaultQualitySelected(ExoPlayerFragment.RENDERER_INDEX_VIDEO) ? null : new MyFormat(videoFormat);
        MyFormat audio = isDefaultQualitySelected(ExoPlayerFragment.RENDERER_INDEX_AUDIO) ? null : new MyFormat(audioFormat);
        persistVideoParams(video);
        persistAudioParams(audio);
        persistVideoPosition();
        persistSubtitle();
    }

    private void persistSubtitle() {
        MappedTrackInfo trackInfo = mSelector.getCurrentMappedTrackInfo();

        if (trackInfo == null) {
            return;
        }

        TrackGroupArray groups = trackInfo.getTrackGroups(RENDERER_INDEX_SUBTITLE);
        SelectionOverride override = mSelector.getParameters().getSelectionOverride(RENDERER_INDEX_SUBTITLE, groups);

        persistSubtitleTrack(getFormatFromOverride(groups, override));
    }

    private MyFormat getFormatFromOverride(TrackGroupArray groups, SelectionOverride override) {
        if (override == null) {
            return null;
        }

        TrackGroup selectedGroup = groups.get(override.groupIndex);
        return new MyFormat(selectedGroup.getFormat(override.tracks[0]));
    }

    private void persistVideoPosition() {
        long duration = mPlayer.getDuration();
        if (duration < MIN_PERSIST_DURATION_MILLIS) {
            return;
        }
        long position = mPlayer.getCurrentPosition();
        String title = mPlayerFragment.getMainTitle() + duration; // create something like hash
        boolean almostAllVideoSeen = (duration - position) < MAX_TRAIL_DURATION_MILLIS;
        boolean isVideoJustStarts = position < MAX_START_DURATION_MILLIS;
        if (almostAllVideoSeen || isVideoJustStarts) {
            persistVideoTrackPosition(title, 0);
        } else {
            persistVideoTrackPosition(title, position);
        }
    }

    private boolean isDefaultQualitySelected(int rendererIndex) {
        if (mSelector == null) {
            return false;
        }

        MappedTrackInfo info = mSelector.getCurrentMappedTrackInfo();

        if (info == null) {
            return false;
        }

        TrackGroupArray groups = info.getTrackGroups(rendererIndex);

        if (groups == null) {
            return false;
        }

        SelectionOverride override = mSelector.getParameters().getSelectionOverride(rendererIndex, groups);

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
