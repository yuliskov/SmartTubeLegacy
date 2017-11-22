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

public class PlayerStateManager {
    private static final TrackSelection.Factory FIXED_FACTORY = new FixedTrackSelection.Factory();
    private final PlayerActivity mContext;
    private final SimpleExoPlayer mPlayer;
    private final DefaultTrackSelector mSelector;
    private ExoPreferences mPrefs;
    private long MIN_PERSIST_DURATION_MILLIS = 10 * 60 * 1000; // 10 min

    public PlayerStateManager(PlayerActivity context, SimpleExoPlayer player, DefaultTrackSelector selector) {
        mContext = context;
        mPlayer = player;
        mSelector = selector;
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
        if (mPrefs != null) { // run once
            return;
        }

        mPrefs = new ExoPreferences(mContext);

        restorePlayerState(rendererTrackGroupArrays);
    }

    private void restorePlayerState(TrackGroupArray[] rendererTrackGroupArrays) {
        String selectedTrackId = mPrefs.getSelectedTrackId();
        if (selectedTrackId == null) {
            return;
        }

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
        int rendererIndex = 0; // video
        TrackGroupArray groupArray = rendererTrackGroupArrays[rendererIndex];
        TrackGroup defaultTrackGroup = groupArray.get(0);
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);
                if (format.id.equals(selectedTrackId)) {
                    return new int[]{j, i};
                }
            }
        }
        // if track not found, return topmost
        return new int[]{0, (defaultTrackGroup.length - 1)};
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
        long currentPosition = mPlayer.getCurrentPosition();
        String title = mContext.getVideoMainTitle();
        mPrefs.setPosition(title, currentPosition);
    }

    private void persistTrackIndex() {
        String trackId = extractCurrentTrackId();
        mPrefs.setSelectedTrackId(trackId);
    }

    private String extractCurrentTrackId() {
        Format videoFormat = mPlayer.getVideoFormat();
        if (videoFormat == null) {
            return null;
        }
        return videoFormat.id;
    }
}
