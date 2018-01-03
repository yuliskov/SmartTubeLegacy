package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

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
    private static final String AVC_PART = "avc";
    private final PlayerActivity mPlayerActivity;
    private final SimpleExoPlayer mPlayer;
    private final DefaultTrackSelector mSelector;
    private ExoPreferences mPrefs;
    private long MIN_PERSIST_DURATION_MILLIS = 5 * 60 * 1000; // don't save if total duration < 5 min
    private long MAX_TRAIL_DURATION_MILLIS = 2 * 60 * 1000; // don't save if 1 min of unseen video remains
    private long MAX_START_DURATION_MILLIS = 1 * 60 * 1000; // don't save if video just starts playing < 1 min
    private String mDefaultTrackId;

    public PlayerStateManager(PlayerActivity playerActivity, SimpleExoPlayer player, DefaultTrackSelector selector) {
        mPlayerActivity = playerActivity;
        mPlayer = player;
        mSelector = selector;
        mPrefs = new ExoPreferences(playerActivity);
    }

    public void restoreState(TrackGroupArray[] rendererTrackGroupArrays) {
        restoreTrackIndex(rendererTrackGroupArrays);
        restoreTrackPosition(rendererTrackGroupArrays);
    }

    private void restoreTrackPosition(TrackGroupArray[] groupArrays) {
        String title = mPlayerActivity.getMainTitle();
        long pos = mPrefs.getPosition(title);
        if (pos != C.TIME_UNSET)
            mPlayer.seekTo(pos);
    }

    private void restoreTrackIndex(TrackGroupArray[] rendererTrackGroupArrays) {
        String selectedTrackId = mPrefs.getSelectedTrackId();
        int selectedTrackHeight = mPrefs.getSelectedTrackHeight();

        loadTrack(rendererTrackGroupArrays, selectedTrackId, selectedTrackHeight);
    }

    /**
     * Switch track
     * @param rendererTrackGroupArrays source of tracks
     * @param selectedTrackId dash format id
     * @param selectedTrackHeight
     */
    private void loadTrack(TrackGroupArray[] rendererTrackGroupArrays, String selectedTrackId, int selectedTrackHeight) {
        int rendererIndex = 0; // video
        if (trackGroupIsEmpty(rendererTrackGroupArrays)) {
            return;
        }
        int[] trackGroupAndIndex = findTrackGroupAndIndex(rendererTrackGroupArrays, selectedTrackId, selectedTrackHeight);
        TrackGroupArray trackGroupArray = rendererTrackGroupArrays[rendererIndex];
        SelectionOverride override = new SelectionOverride(FIXED_FACTORY, trackGroupAndIndex[0], trackGroupAndIndex[1]);
        mSelector.setSelectionOverride(rendererIndex, trackGroupArray, override);
    }

    private int[] findTrackGroupAndIndex(TrackGroupArray[] rendererTrackGroupArrays, String selectedTrackId, int selectedTrackHeight) {
        mDefaultTrackId = null;

        int rendererIndex = 0; // video
        TrackGroupArray groupArray = rendererTrackGroupArrays[rendererIndex];
        TrackGroup defaultTrackGroup = groupArray.get(0);

        // search the same tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);
                if (tracksEquals(format.id, selectedTrackId)) {
                    mDefaultTrackId = format.id;
                    return new int[]{j, i};
                }
            }
        }

        // search the related tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);
                if (tracksRelated(format.id, selectedTrackId)) {
                    mDefaultTrackId = format.id;
                    return new int[]{j, i};
                }
            }
        }

        // search the same resolution tracks (LIVE translation)
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);
                if (format.height == selectedTrackHeight &&
                    format.codecs.contains(AVC_PART)) {
                    mDefaultTrackId = format.id;
                    return new int[]{j, i};
                }
            }
        }

        // if track not found, return topmost
        int lastIdx = defaultTrackGroup.length - 1;
        mDefaultTrackId = defaultTrackGroup.getFormat(lastIdx).id;
        return new int[]{0, lastIdx};
    }

    private boolean tracksEquals(String leftTrackId, String rightTrackId) {
        if (leftTrackId == null || rightTrackId == null) {
            return false;
        }

        return leftTrackId.equals(rightTrackId);
    }

    private boolean tracksRelated(String leftTrackId, String rightTrackId) {
        if (leftTrackId == null || rightTrackId == null) {
            return false;
        }

        int i = Integer.parseInt(leftTrackId);
        int j = Integer.parseInt(rightTrackId);
        // presume that 30fps and 60fps is the same format
        return Math.abs(i - j) == 162;
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
        String title = mPlayerActivity.getMainTitle();
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
        int height = extractCurrentTrackHeight();
        String codecs = extractCurrentTrackCodecs(); // there is a bug (null codecs) on some Live formats (strange id == "1/27")
        // mDefaultTrackId: usually this happens when video does not contain preferred format
        boolean isTrackChanged = codecs != null && trackId != null && !trackId.equals(mDefaultTrackId);
        if (isTrackChanged) {
            mPrefs.setSelectedTrackId(trackId);
            mPrefs.setSelectedTrackHeight(height);
        }
    }

    private String extractCurrentTrackId() {
        Format videoFormat = mPlayer.getVideoFormat();
        if (videoFormat == null) {
            return null;
        }
        return videoFormat.id;
    }

    private String extractCurrentTrackCodecs() {
        Format videoFormat = mPlayer.getVideoFormat();
        if (videoFormat == null) {
            return null;
        }
        return videoFormat.codecs;
    }

    private int extractCurrentTrackHeight() {
        Format videoFormat = mPlayer.getVideoFormat();
        if (videoFormat == null) {
            return 0;
        }
        return videoFormat.height;
    }
}
