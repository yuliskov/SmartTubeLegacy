package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.os.Handler;
import android.widget.Toast;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerCoreFragment;

/**
 * <a href="https://t.me/SmartYouTubeTV/1058">The hang issue</a>
 */
public class PlayerHangListener implements Player.EventListener {
    private static final int VIDEO_RENDERER_INDEX = 0;
    private static final long VIDEO_CHECK_TIMEOUT_MS = 5_000;
    private final PlayerCoreFragment mPlayerCoreFragment;
    private final Handler mHandler;
    private ExoPlayer mPlayer;
    private DefaultTrackSelector mSelector;
    private boolean mHandlerStarted;
    private boolean mVideoLoading;
    private final Runnable mHangCheck = new Runnable() {
        @Override
        public void run() {
            hangCheck();
        }
    };

    public PlayerHangListener(PlayerCoreFragment playerCoreFragment, DefaultTrackSelector selector) {
        this(playerCoreFragment);
        mSelector = selector;
    }

    public PlayerHangListener(PlayerCoreFragment playerCoreFragment, ExoPlayer player) {
        this(playerCoreFragment);
        mPlayer = player;
    }

    public PlayerHangListener(PlayerCoreFragment playerCoreFragment) {
        mPlayerCoreFragment = playerCoreFragment;
        mHandler = new Handler(mPlayerCoreFragment.getActivity().getMainLooper());
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        mVideoLoading = playbackState == Player.STATE_IDLE || playbackState == Player.STATE_BUFFERING;

        if (mVideoLoading) {
            startReloadTimer();
        }
    }

    private void startReloadTimer() {
        if (mHandlerStarted) // avoid multiple handlers
            return;

        mHandlerStarted = true;
        mHandler.postDelayed(mHangCheck, VIDEO_CHECK_TIMEOUT_MS);
    }

    private void hangCheck() {
        if (mVideoLoading) { // still loading?
            MessageHelpers.showMessage(mPlayerCoreFragment.getActivity(), R.string.video_hang_msg);

            reloadSelectedTrack();
            // stopStartPlayer();
        }

        mHandlerStarted = false;
    }

    private void stopStartPlayer() {
        if (mPlayer == null)
            return;

        mPlayer.setPlayWhenReady(false); // pause

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPlayer.setPlayWhenReady(true); // resume
            }
        }, 1_000);
    }

    private void reloadSelectedTrack() {
        if (mSelector == null)
            return;

        MappedTrackInfo trackInfo = mSelector.getCurrentMappedTrackInfo();

        if (trackInfo == null)
            return;

        TrackGroupArray trackGroups = trackInfo.getTrackGroups(VIDEO_RENDERER_INDEX);
        SelectionOverride override = mSelector.getSelectionOverride(VIDEO_RENDERER_INDEX, trackGroups);
        mSelector.setSelectionOverride(VIDEO_RENDERER_INDEX, trackGroups, override);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
