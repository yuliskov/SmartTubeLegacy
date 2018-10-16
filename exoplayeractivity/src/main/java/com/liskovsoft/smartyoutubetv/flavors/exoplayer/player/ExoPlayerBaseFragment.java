package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.TwoFragmentsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.PlayerListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.DetailDebugViewHelper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerButtonsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerInitializer;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerStateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.GenericSelectorDialog;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.RestrictCodecDataSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.SpeedDataSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.LayoutToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.TextToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class ExoPlayerBaseFragment extends PlayerCoreFragment implements OnClickListener, Player.EventListener, PlaybackControlView.VisibilityListener {
    public static final int REQUEST_CODE = 123;
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
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_AUTHOR = "video_author";
    public static final String VIDEO_VIEW_COUNT = "video_views";
    public static final String VIDEO_ID = "video_id";
    public static final String TRACK_ENDED = "track_ended";
    public static final String DISPLAY_MODE_ID = "display_mode_id";

    private DetailDebugViewHelper debugViewHelper;

    private boolean durationSet;
    private int interfaceVisibilityState;
    private PlayerButtonsManager buttonsManager;
    private PlayerStateManager stateManager;
    private AutoFrameRateManager autoFrameRateManager;
    private PlayerInitializer playerInitializer;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // fix lang in case activity has been destroyed and then restored
        // setupLang();
        super.onActivityCreated(savedInstanceState);
        // NOTE: completely disable open/close animation for activity
        // overridePendingTransition(0, 0);

        buttonsManager = new PlayerButtonsManager(this);
        playerInitializer = new PlayerInitializer(this);
    }

    @Override
    public void initializePlayer() {
        if (getIntent() == null)
            return;

        boolean needNewPlayer = player == null;
        super.initializePlayer();

        if (needNewPlayer) {
            debugViewHelper = new DetailDebugViewHelper(player, debugViewGroup, getActivity());

            // Do not move this code to another place!!! This statement must come after player initialization
            autoFrameRateManager = new AutoFrameRateManager(getActivity(), player);

            playerInitializer.initVideoTitle();
        }
    }

    protected void initializeTrackSelector() {
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory) {
            @Override
            protected TrackSelection[] selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray[] rendererTrackGroupArrays, int[][][] rendererFormatSupports) throws ExoPlaybackException {

                if (stateManager == null) { // run once
                    stateManager = new PlayerStateManager(ExoPlayerBaseFragment.this, player, trackSelector);
                    stateManager.restoreState(rendererTrackGroupArrays);
                }

                forceAllFormatsSupport(rendererFormatSupports);

                return super.selectTracks(rendererCapabilities, rendererTrackGroupArrays, rendererFormatSupports);
            }

            private void forceAllFormatsSupport(int[][][] rendererFormatSupports) {
                if (rendererFormatSupports == null) {
                    return;
                }

                for (int i = 0; i < rendererFormatSupports.length; i++) {
                    if (rendererFormatSupports[i] == null) {
                        continue;
                    }
                    for (int j = 0; j < rendererFormatSupports[i].length; j++) {
                        if (rendererFormatSupports[i][j] == null) {
                            continue;
                        }
                        for (int k = 0; k < rendererFormatSupports[i][j].length; k++) {
                            int supportLevel = rendererFormatSupports[i][j][k];
                            int notSupported = 6;
                            int formatSupported = 7;
                            if (supportLevel == notSupported) {
                                rendererFormatSupports[i][j][k] = formatSupported;
                            }
                        }
                    }
                }
            }
        };

        // Commented out because of bug: can't instantiate OMX decoder...
        // NOTE: 'Tunneled video playback' (HDR and others) (https://medium.com/google-exoplayer/tunneled-video-playback-in-exoplayer-84f084a8094d)
        // Enable tunneling if supported by the current media and device configuration.
        //if (Util.SDK_INT >= 21)
        //    trackSelector.setTunnelingAudioSessionId(C.generateAudioSessionIdV21(this));

        trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
        eventLogger = new EventLogger(trackSelector);
    }

    public void showDebugView(final boolean show) {
        if (debugViewHelper == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ExoPlayerBaseFragment.this.showDebugView(show);
                }
            }, 1000);
            return;
        }

        if (show) {
            debugViewGroup.setVisibility(View.VISIBLE);
            debugViewHelper.start();
        } else {
            debugViewGroup.setVisibility(View.GONE);
            debugViewHelper.stop();
        }
    }

    public String getMainTitle() {
        return playerInitializer.getMainTitle();
    }

    public void onCheckedChanged(@NonNull ToggleButtonBase compoundButton, boolean b) {
        if (buttonsManager != null)
            buttonsManager.onCheckedChanged(compoundButton, b);
    }

    public void doGracefulExit() {
        Intent intent = buttonsManager.createResultIntent();
        doGracefulExit(intent);
    }

    public void doGracefulExit(String action) {
        Intent intent = buttonsManager.createResultIntent();
        intent.putExtra(action, true);
        doGracefulExit(intent);
    }

    private void doGracefulExit(Intent intent) {
        if (autoFrameRateManager != null) {
            intent.putExtra(DISPLAY_MODE_ID, autoFrameRateManager.getCurrentModeId());
        }

        ((PlayerListener) getActivity()).onPlayerClosed(intent);

        // TODO: handle player's exit
        //setResult(Activity.RESULT_OK, intent);
        //
        //finish();
        //overridePendingTransition(0, 0);
    }

    protected void syncButtonStates() {
        if (buttonsManager != null)
            buttonsManager.syncButtonStates();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (stateManager != null)
            stateManager.persistState(); // player about to crash
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
            GenericSelectorDialog.create(getActivity(), new RestrictCodecDataSource(getActivity()));
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
        debugRootView.addView(button, debugRootView.getChildCount() - 1);
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onVisibilityChange(int visibility) {
        interfaceVisibilityState = visibility;

        playerTopBar.setVisibility(visibility);

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
        releasePlayer(); // forget previous state
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
        syncButtonStates(); // onCheckedChanged depends on this
        initializePlayer();
    }

    /**
     * Used when exoplayer's fragment no longer visible (e.g. paused)
     */
    protected void releasePlayer() {
        if (player != null) {
            simpleExoPlayerView.hideController();
            if (stateManager != null)
                stateManager.persistState();
            if (debugViewHelper != null)
                debugViewHelper.stop();
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            debugViewHelper = null;
            player = null;
            trackSelector = null;
            trackSelectionHelper = null;
            eventLogger = null;
            stateManager = null; // force restore state
            durationSet = false;
        }
    }

    // ExoPlayer.EventListener implementation

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_READY && !durationSet) {
            durationSet = true; // run once per video
            if (stateManager != null)
                stateManager.restoreTrackPosition(); // stateManage already should be initialized
        }

        if (playbackState == Player.STATE_ENDED) {
            // doGracefulExit(PlayerActivity.BUTTON_NEXT); // force next track
            doGracefulExit(ExoPlayerBaseFragment.TRACK_ENDED);
        }

        if (playbackState == Player.STATE_READY) {
            autoFrameRateManager.apply();
            playerInitializer.initTimeBar(player); // set proper time increments
        }

        super.onPlayerStateChanged(playWhenReady, playbackState);
    }

    public AutoFrameRateManager getAutoFrameRateManager() {
        return autoFrameRateManager;
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        // NOTE: moving to ExoPlayer 2.5.3
    }

    @Override
    public void onPositionDiscontinuity() {
        if (needRetrySource) {
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

    void retryIfNeeded() {
        if (stateManager != null)
            stateManager.persistState();

        if (needRetrySource) {
            initializePlayer();
        }
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
        if (player == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ExoPlayerBaseFragment.this.setRepeatEnabled(enabled);
                }
            }, 1000);
            return;
        }

        int repeatMode = enabled ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF;
        player.setRepeatMode(repeatMode);
    }

    public void onSpeedClicked() {
        GenericSelectorDialog.create(getActivity(), new SpeedDataSource(getActivity(), player));
    }

    protected boolean isUiVisible() {
        return interfaceVisibilityState == View.VISIBLE;
    }
}
