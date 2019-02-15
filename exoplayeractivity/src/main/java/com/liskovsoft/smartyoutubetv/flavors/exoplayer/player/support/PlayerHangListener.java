package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.os.Handler;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.trackstate.PlayerStateManager;

/**
 * <a href="https://t.me/SmartYouTubeTV/1058">The hang issue</a>
 */
public class PlayerHangListener extends AbstractPlayerEventListener {
    private static final int VIDEO_RENDERER_INDEX = 0;
    private static final long VIDEO_CHECK_TIMEOUT_MS = 10_000;
    private final Context mContext;
    private final Handler mHandler;
    private PlayerStateManager mStateManager;
    private ExoPlayer mPlayer;
    private DefaultTrackSelector mSelector;
    private boolean mHandlerStarted;
    private boolean mVideoLoading;
    private boolean mCancelPendingRoutines;

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

    public PlayerHangListener(Context context, PlayerStateManager stateManager) {
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
        if (mVideoLoading && !mCancelPendingRoutines) { // still loading?
            // MessageHelpers.showMessage(mContext, R.string.video_hang_msg);
            restoreTrackState(); // lets hope that this will help
        }

        mCancelPendingRoutines = false;
        mHandlerStarted = false;
    }

    private void restoreTrackState() {
        if (mStateManager != null) {
            mStateManager.restoreStatePartially();
        }
    }

    private void reloadSelectedTrack() {
        if (mSelector == null)
            return;

        MappedTrackInfo trackInfo = mSelector.getCurrentMappedTrackInfo();

        if (trackInfo == null)
            return;

        TrackGroupArray trackGroups = trackInfo.getTrackGroups(VIDEO_RENDERER_INDEX);
        SelectionOverride override = mSelector.getParameters().getSelectionOverride(VIDEO_RENDERER_INDEX, trackGroups);
        mSelector.setParameters(mSelector.buildUponParameters().setSelectionOverride(VIDEO_RENDERER_INDEX, trackGroups, override));
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // we're not handle errors, only hangs
        mCancelPendingRoutines = true;
    }
}
