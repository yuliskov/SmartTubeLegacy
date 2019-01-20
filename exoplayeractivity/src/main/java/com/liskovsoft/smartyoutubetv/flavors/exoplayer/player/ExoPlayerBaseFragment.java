package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.dialogs.SingleChoiceSelectorDialog;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.AspectRatioManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.MyDebugViewHelper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerButtonsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerHangListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerInitializer;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerStateManager2;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.RestrictCodecDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.SpeedDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.LayoutToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.TextToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;
import com.liskovsoft.smartyoutubetv.fragments.PlayerListener;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class ExoPlayerBaseFragment extends PlayerCoreFragment {
    private static final String TAG = ExoPlayerBaseFragment.class.getName();

    public static final String BUTTON_USER_PAGE = "button_user_page";
    public static final String BUTTON_LIKE = "button_like";
    public static final String BUTTON_DISLIKE = "button_dislike";
    public static final String BUTTON_SUBSCRIBE = "button_subscribe";
    public static final String BUTTON_PREV = "button_prev";
    public static final String BUTTON_NEXT = "button_next";
    public static final String BUTTON_BACK = "button_back";
    public static final String BUTTON_SUGGESTIONS = "button_suggestions";
    public static final String VIDEO_DATE = "video_date";
    public static final String SCREEN_WIDTH = "screen_width";
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_AUTHOR = "video_author";
    public static final String VIDEO_VIEW_COUNT = "video_views";
    public static final String VIDEO_ID = "video_id";
    public static final String TRACK_ENDED = "track_ended";
    public static final String DISPLAY_MODE_ID = "display_mode_id";
    public static final String VIDEO_CANCELED = "video_canceled";
    private static final float TEXT_SIZE_SMALL = 14;
    private static final float SCREEN_WIDTH_SMALL = 640;

    private MyDebugViewHelper mDebugViewHelper;

    private boolean mIsDurationSet;
    private int mInterfaceVisibilityState = View.INVISIBLE;
    private PlayerButtonsManager mButtonsManager;
    private AutoFrameRateManager mAutoFrameRateManager;
    private PlayerStateManager2 mStateManager;
    private AspectRatioManager mAspectRatioManager;
    protected PlayerInitializer mPlayerInitializer;
    private String mSpeed = "1.0";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // NOTE: completely disable open/close animation for activity
        // overridePendingTransition(0, 0);

        mButtonsManager = new PlayerButtonsManager(this);
        mPlayerInitializer = new PlayerInitializer(this);
        mAspectRatioManager = new AspectRatioManager(getActivity(), mSimpleExoPlayerView);
    }

    @Override
    public void initializePlayer() {
        if (getIntent() == null)
            return;

        boolean needNewPlayer = mPlayer == null;
        super.initializePlayer();

        if (needNewPlayer) {
            mDebugViewHelper = new MyDebugViewHelper(mPlayer, mDebugViewGroup, getActivity());

            // Do not move this code to another place!!! This statement must come after player initialization
            mAutoFrameRateManager = new AutoFrameRateManager(getActivity(), mPlayer);
            mAutoFrameRateManager.saveOriginalState();

            mPlayerInitializer.initVideoTitle();

            mStateManager = new PlayerStateManager2(this, mPlayer, mTrackSelector);

            // mPlayer.addListener(new PlayerHangListener(getActivity(), mStateManager));
        }
    }

    private void initializeUiScale() {
        int width = getIntent().getIntExtra(SCREEN_WIDTH, 0);

        if (getIntent() == null || width == 0) {
            return;
        }

        float scaledWidth = width * TEXT_SIZE_SMALL / SCREEN_WIDTH_SMALL;

        mLoadingView.setTextSize(scaledWidth);
    }

    protected void initializeTrackSelector() {
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        
        mTrackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

        // Commented out because of bug: can't instantiate OMX decoder...
        // NOTE: 'Tunneled video playback' (HDR and others) (https://medium.com/google-exoplayer/tunneled-video-playback-in-exoplayer-84f084a8094d)
        // Enable tunneling if supported by the current media and device configuration.
        //if (Util.SDK_INT >= 21)
        //    trackSelector.setTunnelingAudioSessionId(C.generateAudioSessionIdV21(this));

        mTrackSelectionHelper = new TrackSelectionHelper(mTrackSelector, adaptiveTrackSelectionFactory);
        mEventLogger = new EventLogger(mTrackSelector);
    }

    public void showDebugView(final boolean show) {
        if (mDebugViewHelper == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ExoPlayerBaseFragment.this.showDebugView(show);
                }
            }, 1000);
            return;
        }

        if (show) {
            mDebugViewGroup.setVisibility(View.VISIBLE);
            mDebugViewHelper.start();
        } else {
            mDebugViewGroup.setVisibility(View.GONE);
            mDebugViewHelper.stop();
        }
    }

    public String getMainTitle() {
        return mPlayerInitializer.getMainTitle();
    }

    public void onCheckedChanged(@NonNull ToggleButtonBase compoundButton, boolean b) {
        if (mButtonsManager != null)
            mButtonsManager.onCheckedChanged(compoundButton, b);
    }

    public void doGracefulExit() {
        Intent intent = mButtonsManager.createResultIntent();
        doGracefulExit(intent);
    }

    public void doGracefulExit(String action) {
        Intent intent = mButtonsManager.createResultIntent();
        intent.putExtra(action, true);
        doGracefulExit(intent);
    }

    private void doGracefulExit(Intent intent) {
        if (mAutoFrameRateManager != null) {
            intent.putExtra(DISPLAY_MODE_ID, mAutoFrameRateManager.getCurrentModeId());
            mAutoFrameRateManager.restoreOriginalState();
        }

        ((PlayerListener) getActivity()).onPlayerClosed(intent);
    }

    protected void syncButtonStates() {
        if (mButtonsManager != null)
            mButtonsManager.syncButtonStates();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mStateManager != null)
            mStateManager.persistState(); // player about to crash
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            // finish();
        }
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.restrict_codec_btn) {
            SingleChoiceSelectorDialog.create(getActivity(), new RestrictCodecDialogSource(getActivity()));
        }
    }

    @Override
    void addCustomButtonToQualitySection() {
        addRestrictCodecButton();
    }

    private void addRestrictCodecButton() {
        TextToggleButton button = new TextToggleButton(getActivity());
        button.setId(R.id.restrict_codec_btn);
        button.setText(R.string.restrict);
        button.setOnClickListener(this);
        mDebugRootView.addView(button, mDebugRootView.getChildCount() - 1);
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onVisibilityChange(int visibility) {
        mInterfaceVisibilityState = visibility;

        mPlayerTopBar.setVisibility(visibility);

        // NOTE: don't set to GONE or you will get fathom events
        if (visibility == View.VISIBLE) {
            resetStateOfLayoutToggleButtons();
            updateClockView();
        }
    }

    private void updateClockView() {
        View root = getView();
        if (root == null)
            throw new IllegalStateException("Fragment's root view is null");
        TextView clock = root.findViewById(R.id.clock);
        Time time = new Time();
        time.setToNow();
        // details about format: http://php.net/manual/en/function.strftime.php
        String currentTime = getString(R.string.time_title, time.format("%a %e %h %R"));
        clock.setText(currentTime);
    }

    private void updateQualityTitle() {
        View root = getView();

        if (root == null) {
            throw new IllegalStateException("Fragment's root view is null");
        }

        if (mTrackSelector == null) {
            return;
        }

        TextView quality = root.findViewById(R.id.video_quality);
        Format format = PlayerUtil.getCurrentlyPlayingTrack(mTrackSelector);

        if (format == null)
            return;

        String separator = "/";

        String qualityLabel = PlayerUtil.extractQualityLabel(format);

        int fpsNum = PlayerUtil.extractFps(format);
        String fps = fpsNum == 0 ? "" : String.valueOf(fpsNum);

        String codec = PlayerUtil.extractCodec(format);

        String qualityString;

        if (!fps.isEmpty()) {
            fps = separator + fps;
        }

        if (!codec.isEmpty()) {
            codec = separator + codec;
        }

        qualityString = String.format("%s%s%s", qualityLabel, fps, codec);

        quality.setText(qualityString);
    }

    private void resetStateOfLayoutToggleButtons() {
        View root = getView();
        if (root == null)
            throw new IllegalStateException("Fragment's root view is null");
        LayoutToggleButton toggleButton1 = root.findViewById(R.id.player_options_btn);
        LayoutToggleButton toggleButton2 = root.findViewById(R.id.player_quality_btn);
        toggleButton1.resetState();
        toggleButton2.resetState();
    }

    /**
     * Entry point: open brand new video
     * @param intent video info
     */
    protected void openVideoFromIntent(Intent intent) {
        Log.d(TAG, "Open video from intent=" + intent);
        releasePlayer(); // dispose player
        mShouldAutoPlay = true; // force autoplay
        clearResumePosition(); // restore position will be done later from the app storage
        setIntent(intent);
        syncButtonStates(); // onCheckedChanged depends on this
        initializePlayer();
        initializeUiScale();
    }

    /**
     * Used when exoplayer's fragment no longer visible (e.g. paused/resumed/stopped/started)
     */
    protected void releasePlayer() {
        if (mAutoFrameRateManager != null) {
            mAutoFrameRateManager.restoreOriginalState();
        }

        if (mPlayer != null) {
            mShouldAutoPlay = mPlayer.getPlayWhenReady(); // save paused state
            updateResumePosition(); // save position
            mPlayer.release();
        }

        if (mSimpleExoPlayerView != null) {
            mSimpleExoPlayerView.hideController();
        }

        if (mStateManager != null) {
            mStateManager.persistState();
        }

        if (mDebugViewHelper != null) {
            mDebugViewHelper.stop();
        }

        mAutoFrameRateManager = null;
        mPlayer = null;
        mStateManager = null; // force restore state
        mDebugViewHelper = null;
        mTrackSelector = null;
        mTrackSelectionHelper = null;
        mEventLogger = null;
        mIsDurationSet = false;
        mRetryCount = 0;
    }

    // ExoPlayer.EventListener implementation

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_READY && !mIsDurationSet) {
            mIsDurationSet = true; // run once per video
            if (mStateManager != null) {
                // stateManage should be initialized here
                mStateManager.restoreState();
                updateQualityTitle();
            }

            if (mPlayer != null) {
                mPlayer.setPlayWhenReady(mShouldAutoPlay);
            }
        }

        if (playbackState == Player.STATE_ENDED) {
            // doGracefulExit(PlayerActivity.BUTTON_NEXT); // force next track
            doGracefulExit(ExoPlayerBaseFragment.TRACK_ENDED);
        }

        if (playbackState == Player.STATE_READY) {
            mAutoFrameRateManager.apply();
            mPlayerInitializer.initTimeBar(mPlayer); // set proper time increments
        }

        showLoadingMessage(playbackState);

        super.onPlayerStateChanged(playWhenReady, playbackState);
    }

    private void showLoadingMessage(int playbackState) {
        int visibility = playbackState == Player.STATE_IDLE ||
                playbackState == Player.STATE_BUFFERING ? View.VISIBLE : View.GONE;
        mLoadingView.setVisibility(visibility);
    }

    public AutoFrameRateManager getAutoFrameRateManager() {
        return mAutoFrameRateManager;
    }

    public AspectRatioManager getAspectRatioManager() {
        return mAspectRatioManager;
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        // NOTE: moving to ExoPlayer 2.5.3
    }

    @Override
    public void onPositionDiscontinuity() {
        if (mNeedRetrySource) {
            // This will only occur if the user has performed a seek whilst in the error state. Update the
            // resume position so that if the user then retries, playback will resume from the position to
            // which they seeked.
            updateResumePosition();
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        // Do nothing.
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
    }

    /**
     * At this point user've changed video quality
     */
    void retryIfNeeded() {
        if (mStateManager != null)
            mStateManager.persistState();

        if (mNeedRetrySource) {
            initializePlayer();
        }

        updateQualityTitle();
    }

    public void setHidePlaybackErrors(boolean hideErrors) {
        ExoPreferences prefs = ExoPreferences.instance(getActivity());
        prefs.setHidePlaybackErrors(hideErrors);
    }

    public boolean getHidePlaybackErrors() {
        ExoPreferences prefs = ExoPreferences.instance(getActivity());
        return prefs.getHidePlaybackErrors();
    }

    public void setRepeatEnabled(final boolean enabled) {
        if (mPlayer == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ExoPlayerBaseFragment.this.setRepeatEnabled(enabled);
                }
            }, 1000);
            return;
        }

        int repeatMode = enabled ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF;
        mPlayer.setRepeatMode(repeatMode);
    }

    public void onSpeedClicked() {
        SingleChoiceSelectorDialog.create(getActivity(), new SpeedDialogSource(getActivity(), mPlayer));
    }

    protected boolean isUiVisible() {
        return mInterfaceVisibilityState == View.VISIBLE;
    }
}
