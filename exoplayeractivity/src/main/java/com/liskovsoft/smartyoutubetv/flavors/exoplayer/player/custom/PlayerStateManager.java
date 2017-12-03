package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.util.HashMap;
import java.util.Map;

public class PlayerStateManager {
    private static final TrackSelection.Factory FIXED_FACTORY = new FixedTrackSelection.Factory();
    private final PlayerActivity mContext;
    private final SimpleExoPlayer mPlayer;
    private final DefaultTrackSelector mSelector;
    private ExoPreferences mPrefs;
    private long MIN_PERSIST_DURATION_MILLIS = 5 * 60 * 1000; // don't save if total duration < 5 min
    private long MAX_TRAIL_DURATION_MILLIS = 2 * 60 * 1000; // don't save if 1 min of unseen video remains
    private long MAX_START_DURATION_MILLIS = 1 * 60 * 1000; // don't save if video just starts playing < 1 min
    private String mDefaultTrackId;

    public PlayerStateManager(PlayerActivity context, SimpleExoPlayer player, DefaultTrackSelector selector) {
        mContext = context;
        mPlayer = player;
        mSelector = selector;
        mPrefs = new ExoPreferences(context);
    }

    public void restoreState(TrackGroupArray[] rendererTrackGroupArrays) {
        restoreTrackIndex(rendererTrackGroupArrays);
        restoreTrackPosition(rendererTrackGroupArrays);
    }

    private void restoreTrackPosition(TrackGroupArray[] groupArrays) {
        String title = mContext.getVideoMainTitle();
        long pos = mPrefs.getPosition(title);
        if (pos != C.TIME_UNSET)
            mPlayer.seekTo(pos);
    }

    private void restoreTrackIndex(TrackGroupArray[] rendererTrackGroupArrays) {
        String selectedTrackId = mPrefs.getSelectedTrackId();

        loadTrack(rendererTrackGroupArrays, selectedTrackId);
    }

    /**
     * Switch track
     * @param rendererTrackGroupArrays source of tracks
     * @param selectedTrackId dash format id
     */
    private void loadTrack(TrackGroupArray[] rendererTrackGroupArrays, String selectedTrackId) {
        int rendererIndex = 0; // video
        if (trackGroupIsEmpty(rendererTrackGroupArrays)) {
            return;
        }
        int[] trackGroupAndIndex = findTrackGroupAndIndex(rendererTrackGroupArrays, selectedTrackId);
        TrackGroupArray trackGroupArray = rendererTrackGroupArrays[rendererIndex];
        SelectionOverride override = new SelectionOverride(FIXED_FACTORY, trackGroupAndIndex[0], trackGroupAndIndex[1]);
        mSelector.setSelectionOverride(rendererIndex, trackGroupArray, override);
    }

    private int[] findTrackGroupAndIndex(TrackGroupArray[] rendererTrackGroupArrays, String selectedTrackId) {
        mDefaultTrackId = null;

        int rendererIndex = 0; // video
        TrackGroupArray groupArray = rendererTrackGroupArrays[rendererIndex];
        TrackGroup defaultTrackGroup = groupArray.get(0);
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);
                if (trackEquals(format.id, selectedTrackId)) {
                    return new int[]{j, i};
                }
            }
        }
        // if track not found, return topmost
        int lastIdx = defaultTrackGroup.length - 1;
        mDefaultTrackId = defaultTrackGroup.getFormat(lastIdx).id;
        return new int[]{0, lastIdx};
    }

    private boolean trackEquals(String leftTrackId, String rightTrackId) {
        if (leftTrackId == null || rightTrackId == null) {
            return false;
        }

        int i = Integer.parseInt(leftTrackId);
        int j = Integer.parseInt(rightTrackId);
        // presume that 30fps and 60fps is the same format
        if (Math.abs(i - j) == 162) {
            return true;
        }

        return leftTrackId.equals(rightTrackId);
    }

    private boolean trackGroupIsEmpty(TrackGroupArray[] rendererTrackGroupArrays) {
        int rendererIndex = 0; // video
        TrackGroupArray groupArray = rendererTrackGroupArrays[rendererIndex];
        return groupArray.length == 0;
    }
    
    public void persistState() {
        if (mPrefs == null) {
            return;
        }

        persistTrackIndex();
        persistTrackPosition();
    }

    private void persistTrackPosition() {
        long duration = mPlayer.getDuration();
        if (duration < MIN_PERSIST_DURATION_MILLIS) {
            return;
        }
        long position = mPlayer.getCurrentPosition();
        String title = mContext.getVideoMainTitle();
        boolean almostAllVideoSeen = (duration - position) < MAX_TRAIL_DURATION_MILLIS;
        boolean isVideoJustStarts = position < MAX_START_DURATION_MILLIS;
        if (almostAllVideoSeen || isVideoJustStarts) {
            mPrefs.resetPosition(title);
        } else {
            mPrefs.setPosition(title, position);
        }
    }

    private void persistTrackIndex() {
        String trackId = extractCurrentTrackId();
        // mDefaultTrackId: usually this happens when video does not contain preferred format
        boolean isTrackChanged = trackId != null && !trackId.equals(mDefaultTrackId);
        if (isTrackChanged) {
            mPrefs.setSelectedTrackId(trackId);
        }
    }

    private String extractCurrentTrackId() {
        Format videoFormat = mPlayer.getVideoFormat();
        if (videoFormat == null) {
            return null;
        }
        return videoFormat.id;
    }
}
