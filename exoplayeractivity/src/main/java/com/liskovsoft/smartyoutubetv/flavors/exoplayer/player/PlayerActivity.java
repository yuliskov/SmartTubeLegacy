package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.DetailDebugViewHelper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerButtonsManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerInitializer;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons.PlayerStateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.GenericSelectorDialog;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.SpeedDataSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.LayoutToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.TextToggleButton;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class PlayerActivity extends PlayerCoreActivity implements OnClickListener, Player.EventListener, PlaybackControlView.VisibilityListener {
    private static final String TAG = PlayerActivity.class.getName();

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
    
    private TrackSelectionHelper trackSelectionHelper;
    private DetailDebugViewHelper debugViewHelper;
    
    private int interfaceVisibilityState;
    private PlayerButtonsManager buttonsManager;
    private PlayerStateManager stateManager;
    private AutoFrameRateManager autoFrameRateManager;
    private PlayerInitializer playerInitializer;
    private boolean durationSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NOTE: completely disable open/close animation for activity
        overridePendingTransition(0, 0);

        buttonsManager = new PlayerButtonsManager(this);
        buttonsManager.syncButtonStates(); // onCheckedChanged depends on this
        playerInitializer = new PlayerInitializer(this);
    }

    @Override
    public void initializePlayer() {
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            super.initializePlayer();

            debugViewHelper = new DetailDebugViewHelper(player, debugViewGroup, PlayerActivity.this);

            // Do not move this code to another place!!! This statement must come after player initialization
            autoFrameRateManager = new AutoFrameRateManager(this, player);

            // applied one time at player's initialization
            //playerInitializer.applySurfaceFix(player, trackSelector);
            //playerInitializer.applySurfaceFix(player);

            playerInitializer.initVideoTitle();
        }
    }

    protected void initializeTrackSelector() {
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

        // TODO: modified: force all format support
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory) {
            @Override
            protected TrackSelection[] selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray[] rendererTrackGroupArrays, int[][][] rendererFormatSupports) throws ExoPlaybackException {

                if (stateManager == null) { // run once
                    stateManager = new PlayerStateManager(PlayerActivity.this, player, trackSelector);
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
                    PlayerActivity.this.showDebugView(show);
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
        setResult(Activity.RESULT_OK, intent);

        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        doGracefulExit(PlayerActivity.BUTTON_BACK);

        // moveTaskToBack(true); // don't exit at this point
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        if (stateManager != null) {
            stateManager.persistState();
            stateManager = null; // force restore state
        }

        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    // Activity input

    // NOTE: entry point to handle keys
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        event = translateEscapeToBack(event);

        if (isVolumeEvent(event))
            return false;
        
        if (isBackKey(event) || isUpKey(event)) {
            return handleBack(event);
        }

        // Show the controls on any key event.
        simpleExoPlayerView.showController();

        // If the event was not handled then see if the player view can handle it as a media key event.
        return super.dispatchKeyEvent(event) || simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }

    /**
     * Fix for the unknown usb remote controller (see <a href="https://smartyoutubetv.github.io/#comment-3742343397">disqus</a> for details).
     * @param event event
     * @return new event
     */
    private KeyEvent translateEscapeToBack(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_BACK);
        }
        return event;
    }

    private boolean isBackKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
    }

    private boolean isUpKey(KeyEvent event) {
        boolean isUp = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP;
        if (isUp) {
            View upBtn = findViewById(R.id.up_catch_button);
            return upBtn.isFocused();
        }
        return false;
    }

    private boolean handleBack(KeyEvent event) {
        boolean isUp = event.getAction() == KeyEvent.ACTION_UP;
        boolean isVisible = interfaceVisibilityState == View.VISIBLE;

        if (isVisible) {
            if (isUp) {
                simpleExoPlayerView.hideController();
            }
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    private boolean isVolumeEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP ||
            event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return true;
        }
        return false;
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
        if (view == retryButton) {
            initializePlayer();
        } else if (view.getParent() == debugRootView) {
            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                trackSelectionHelper.showSelectionDialog(
                        this,
                        ((TextToggleButton) view).getText(),
                        trackSelector.getCurrentMappedTrackInfo(),
                        (int) view.getTag()
                );
            }
        }
    }

    // PlaybackControlView.VisibilityListener implementation

    @Override
    public void onVisibilityChange(int visibility) {
        interfaceVisibilityState = visibility;

        playerTopBar.setVisibility(visibility);

        // NOTE: don't set to GONE or you will get fathom events
        if (visibility == View.VISIBLE)
            resetStateOfLayoutToggleButtons();
    }

    private void resetStateOfLayoutToggleButtons() {
        LayoutToggleButton toggleButton1 = (LayoutToggleButton) findViewById(R.id.player_options_btn);
        LayoutToggleButton toggleButton2 = (LayoutToggleButton) findViewById(R.id.player_quality_btn);
        toggleButton1.resetState();
        toggleButton2.resetState();
    }

    private void releasePlayer() {
        if (player != null) {
            debugViewHelper.stop();
            debugViewHelper = null;
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            trackSelectionHelper = null;
            eventLogger = null;
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
            doGracefulExit(PlayerActivity.BUTTON_NEXT); // force next track
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
        if (needRetrySource) {
            initializePlayer();
        }
    }

    public void setHidePlaybackErrors(boolean hideErrors) {
        ExoPreferences prefs = ExoPreferences.instance(this);
        prefs.setHidePlaybackErrors(hideErrors);
    }

    public boolean getHidePlaybackErrors() {
        ExoPreferences prefs = ExoPreferences.instance(this);
        return prefs.getHidePlaybackErrors();
    }

    public void setRepeatEnabled(final boolean enabled) {
        if (player == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PlayerActivity.this.setRepeatEnabled(enabled);
                }
            }, 1000);
            return;
        }

        int repeatMode = enabled ? Player.REPEAT_MODE_ONE : Player.REPEAT_MODE_OFF;
        player.setRepeatMode(repeatMode);
    }

    public void onSpeedClicked() {
        GenericSelectorDialog.create(this, new SpeedDataSource(this, player));
    }
}
