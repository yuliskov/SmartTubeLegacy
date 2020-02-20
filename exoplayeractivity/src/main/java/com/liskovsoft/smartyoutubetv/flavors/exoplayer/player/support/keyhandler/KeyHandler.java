package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.keyhandler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.PlayerInterface;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.keytranslator.KeyTranslator;
import com.liskovsoft.smartyoutubetv.keytranslator.PlayerKeyTranslator;

import java.util.HashMap;

public class KeyHandler {
    private final Activity mActivity;
    private PlayerInterface mFragment;
    private KeyTranslator mTranslator;
    private Boolean mEnableOKPause;
    private HashMap<Integer, Runnable> mActions;
    private HashMap<Integer, Integer> mAdditionalMapping;
    private boolean mAutoShowPlayerUI;
    private static final long DEFAULT_FAST_FORWARD_REWIND_MS = 10_000;
    private final Runnable mOnToggle = () -> {
        if (mFragment.getExoPlayerView() != null) {
            mFragment.getExoPlayerView().setControllerAutoShow(mAutoShowPlayerUI);
        }

        if (mFragment.getPlayer() != null) {
            mFragment.getPlayer().setPlayWhenReady(!mFragment.getPlayer().getPlayWhenReady());
        }
    };
    private final Runnable mOnPlay = () -> {
        SimpleExoPlayer player = mFragment.getPlayer();
        PlayerView playerView = mFragment.getExoPlayerView();


        if (playerView != null && player != null && !player.getPlayWhenReady()) {
            player.setPlayWhenReady(true);
            playerView.hideController();
        }
    };
    private final Runnable mOnPause = () -> {
        SimpleExoPlayer player = mFragment.getPlayer();
        PlayerView playerView = mFragment.getExoPlayerView();

        if (playerView != null && player != null && player.getPlayWhenReady()) {
            mFragment.getExoPlayerView().setControllerAutoShow(mAutoShowPlayerUI);
            player.setPlayWhenReady(false);
        }
    };
    private final Runnable mOnFastForward = () -> {
        if (mFragment.getPlayer() != null) {
            seekTo(mFragment.getPlayer().getCurrentPosition() + DEFAULT_FAST_FORWARD_REWIND_MS);
        }
    };
    private final Runnable mOnRewind = () -> {
        if (mFragment.getPlayer() != null) {
            seekTo(mFragment.getPlayer().getCurrentPosition() - DEFAULT_FAST_FORWARD_REWIND_MS);
        }
    };
    private final Runnable mOnStop = () -> mFragment.onBackPressed();

    @TargetApi(23)
    private final Runnable mOnNext = () -> mFragment.getExoPlayerView().findViewById(R.id.exo_next2).callOnClick();
    @TargetApi(23)
    private final Runnable mOnPrev = () -> mFragment.getExoPlayerView().findViewById(R.id.exo_prev).callOnClick();

    public KeyHandler(Activity activity, PlayerInterface playerFragment, HashMap<Integer, Integer> additionalMapping) {
        mActivity = activity;
        mFragment = playerFragment;
        mTranslator = new PlayerKeyTranslator();
        mAdditionalMapping = additionalMapping;
        mAutoShowPlayerUI = CommonApplication.getPreferences().getAutoShowPlayerUI();

        initActionMapping();
    }

    public KeyHandler(Activity activity, PlayerInterface playerFragment) {
        this(activity, playerFragment, null);
    }

    protected void setAdditionalMapping(HashMap<Integer, Integer> mapping) {
        mAdditionalMapping = mapping;
    }

    private void initActionMapping() {
        mActions = new HashMap<>();

        mActions.put(KeyEvent.KEYCODE_MEDIA_PLAY, mOnPlay);
        mActions.put(KeyEvent.KEYCODE_MEDIA_PAUSE, mOnPause);
        mActions.put(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, mOnToggle);
        mActions.put(KeyEvent.KEYCODE_MEDIA_NEXT, mOnNext);
        mActions.put(KeyEvent.KEYCODE_MEDIA_PREVIOUS, mOnPrev);
        mActions.put(KeyEvent.KEYCODE_MEDIA_STOP, mOnStop);
        mActions.put(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD, mOnFastForward);
        mActions.put(KeyEvent.KEYCODE_MEDIA_REWIND, mOnRewind);
        mActions.put(KeyEvent.KEYCODE_CHANNEL_UP, mOnNext);
        mActions.put(KeyEvent.KEYCODE_CHANNEL_DOWN, mOnPrev);
    }

    public boolean handle(KeyEvent event) {
        event = mTranslator.doTranslateKeys(event);
        setDispatchEvent(event);

        if (isVolumeEvent(event)) {
            return false;
        }

        boolean isUpAction = event.getAction() == KeyEvent.ACTION_UP;
        boolean isDownAction = event.getAction() == KeyEvent.ACTION_DOWN;

        boolean uiVisible = mFragment.isUiVisible();

        if (isBackKey(event) && !uiVisible) {
            if (isUpAction) {
                mFragment.onBackPressed();
            }
            return true;
        }

        if (isBackKey(event) || isOutFakeKey(event)) {
            return hideUI(event);
        }

        if (applyMediaKeys(event)) {
            return true;
        }

        // Show the controls on any key event.
        if (!uiVisible && isDownAction) {
            mFragment.getExoPlayerView().showController();
        }

        if (uiVisible && isMenuKey(event) && isDownAction) {
            mFragment.getExoPlayerView().hideController();
            return true;
        }

        if (applySeekAction(event, uiVisible) || isNonOKAction(event, uiVisible)) {
            return true;
        }

        if (uiVisible) {
            mFragment.getExoPlayerView().showController(); // reset controller auto-hide timeout
        }

        //// Fix focus lost after Suggestions
        //if (mFragment.getExoPlayerView().findFocus() == null) {
        //    mFragment.getExoPlayerView().findViewById(R.id.exo_suggestions).requestFocus();
        //}

        // If the event was not handled then see if the player view can handle it as a media key event.
        return mFragment.getExoPlayerView().dispatchKeyEvent(event);
    }

    private boolean applyMediaKeys(KeyEvent event) {
        event = applyAdditionalMapping(event);

        if (mActions.containsKey(event.getKeyCode())) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mActions.get(event.getKeyCode()).run();
            }

            return true;
        }

        return false;
    }

    private KeyEvent applyAdditionalMapping(KeyEvent event) {
        if (mAdditionalMapping != null &&
            mAdditionalMapping.containsKey(event.getKeyCode())) {
            return new KeyEvent(event.getAction(), mAdditionalMapping.get(event.getKeyCode()));
        }

        return event;
    }

    private boolean isMenuKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_MENU;
    }

    private boolean isNonOKAction(KeyEvent event, boolean uiVisible) {
        boolean isOkKey = false;

        if (getEnableOKPause()) {
            isOkKey = isOkKey(event);
        }


        return !uiVisible && !isOkKey;
    }

    private boolean isOkKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_ENTER ||
               event.getKeyCode() == KeyEvent.KEYCODE_NUMPAD_ENTER ||
               event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER;
    }

    private boolean applySeekAction(KeyEvent event, boolean uiVisible) {
        // move selection to the timebar on left/right key events
        boolean isLeftRightKey = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT;

        if (!uiVisible && isLeftRightKey) {
            setFocusOnTimeBar();
            return true;
        }

        return false;
    }

    private boolean isAnyKeyAction(KeyEvent event, boolean uiVisible) {
        // fix focus on the play/pause button: don't move selection
        boolean isUpDownKey = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN;
        boolean isMenuKey = event.getKeyCode() == KeyEvent.KEYCODE_MENU;

        return !uiVisible && (isUpDownKey || isMenuKey);
    }

    private void setFocusOnTimeBar() {
        View timeBar = mFragment.getExoPlayerView().findViewById(R.id.time_bar);
        if (timeBar != null) {
            timeBar.requestFocus();
        }
    }

    private boolean isBackKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
    }

    private boolean isOutFakeKey(KeyEvent event) {
        View root = mFragment.getExoPlayerView();

        boolean isUp = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP;

        if (root != null && isUp && mFragment.isUiVisible()) {
            View upBtn = root.findViewById(R.id.up_catch_button);
            return upBtn.isFocused();
        }

        return false;
    }

    private boolean hideUI(KeyEvent event) {
        boolean isUpAction = event.getAction() == KeyEvent.ACTION_UP;
        boolean isVisible = mFragment.isUiVisible();

        if (isVisible) {
            if (isUpAction) {
                mFragment.getExoPlayerView().hideController();

                // fix control lost over the player ui
                mFragment.getExoPlayerView().requestFocus();
            }
            return true;
        }

        return false;
    }

    private boolean isVolumeEvent(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN;
    }

    private void setDispatchEvent(KeyEvent event) {
        if (mActivity instanceof FragmentManager) {
            ((FragmentManager) mActivity).setDispatchEvent(event);
        }
    }

    private boolean getEnableOKPause() {
        if (mEnableOKPause != null) {
            return mEnableOKPause;
        }

        mEnableOKPause = SmartPreferences.instance(mActivity).getEnableOKPause();

        return mEnableOKPause;
    }

    public void seekTo(long positionMs) {
        if (mFragment.getExoPlayerView() != null && mAutoShowPlayerUI) {
            mFragment.getExoPlayerView().showController();
        }

        if (mFragment.getPlayer() != null && mFragment.getPlayer().isCurrentWindowSeekable()) {
            mFragment.getPlayer().seekTo(positionMs);
        }
    }

    public void pause(boolean paused) {
        if (paused) {
            mOnPause.run();
        } else {
            mOnPlay.run();
        }
    }
}
