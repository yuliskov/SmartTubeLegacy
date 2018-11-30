package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.os.Handler;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerCoreFragment;

public class PlayerHangListener implements Player.EventListener {
    private static final long VIDEO_CHECK_TIMEOUT_MS = 5_000;
    private final PlayerCoreFragment mPlayerCoreFragment;
    private boolean mHandlerStarted;
    private final Handler mHandler;
    private boolean mVideoLoading;
    private ExoPlayer mPlayer;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            reloadPlayer();
        }
    };

    public PlayerHangListener(PlayerCoreFragment playerCoreFragment, ExoPlayer player) {
        mPlayerCoreFragment = playerCoreFragment;
        mHandler = new Handler(mPlayerCoreFragment.getActivity().getMainLooper());
        mPlayer = player;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        mVideoLoading = playbackState == Player.STATE_IDLE || playbackState == Player.STATE_BUFFERING;

        if (mVideoLoading) {
            startReloadTimer();
            return;
        }

        stopReloadTimer();
    }

    private void startReloadTimer() {
        if (mHandlerStarted) // avoid multiple handlers
            return;

        mHandlerStarted = true;
        mHandler.postDelayed(mRunnable, VIDEO_CHECK_TIMEOUT_MS);
    }

    private void stopReloadTimer() {
        mHandlerStarted = false;
        mHandler.removeCallbacks(mRunnable);
    }

    private void reloadPlayer() {
        if (mVideoLoading) {
            mPlayer.setPlayWhenReady(false);
            mPlayer.setPlayWhenReady(true);
        }
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
