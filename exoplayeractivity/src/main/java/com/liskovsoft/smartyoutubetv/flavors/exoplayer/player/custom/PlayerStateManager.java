package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom;

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
        String title = mContext.getVideoTitle();
        long pos = mPrefs.getPosition(title);
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
        long currentPosition = mPlayer.getCurrentPosition();
        String title = mContext.getVideoTitle();
        mPrefs.setPosition(title, currentPosition);
    }

    private void persistTrackIndex() {
        String trackId = extractCurrentTrackId();
        mPrefs.setSelectedTrackId(trackId);
    }

    private String extractCurrentTrackId() {
        //int rendererIndex = 0; // video
        //SelectionOverride override = mSelector.getSelectionOverride(rendererIndex, mPlayer.getCurrentTrackGroups()); // NOTE: may fail
        //int groupIndex = override.groupIndex;
        //int trackIndex = override.tracks[0];
        //
        //MappedTrackInfo trackInfo = mSelector.getCurrentMappedTrackInfo();
        //TrackGroupArray trackGroups = trackInfo.getTrackGroups(rendererIndex);
        //Format format = trackGroups.get(groupIndex).getFormat(trackIndex);
        //return format.id;

        return mPlayer.getVideoFormat().id;
    }
}
