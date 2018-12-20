package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.content.Context;
import android.os.Handler;
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

/**
 * <a href="https://t.me/SmartYouTubeTV/1058">The hang issue</a>
 */
public class PlayerHangListener implements Player.EventListener {
    private static final int VIDEO_RENDERER_INDEX = 0;
    private static final long VIDEO_CHECK_TIMEOUT_MS = 5_000;
    private final Context mContext;
    private final Handler mHandler;
    private PlayerStateManager2 mStateManager;
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

    public PlayerHangListener(Context context, DefaultTrackSelector selector) {
        this(context);
        mSelector = selector;
    }

    public PlayerHangListener(Context context, ExoPlayer player) {
        this(context);
        mPlayer = player;
    }

    public PlayerHangListener(Context context, PlayerStateManager2 stateManager) {
        this(context);
        mStateManager = stateManager;
    }

    public PlayerHangListener(Context context) {
        mContext = context;
        mHandler = new Handler(mContext.getMainLooper());
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
            MessageHelpers.showMessage(mContext, R.string.video_hang_msg);
            restoreTrackState(); // lets hope that this will help
        }

        mHandlerStarted = false;
    }

    private void restoreTrackState() {
        if (mStateManager != null) {
            mStateManager.restoreState();
        }
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
