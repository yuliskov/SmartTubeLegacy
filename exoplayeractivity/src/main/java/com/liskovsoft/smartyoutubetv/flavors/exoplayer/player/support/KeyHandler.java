package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.keytranslator.KeyTranslator;
import com.liskovsoft.smartyoutubetv.keytranslator.PlayerKeyTranslator;

public class KeyHandler {
    private final Activity mActivity;
    private final PlayerInterface mFragment;
    private KeyTranslator mTranslator;
    private Boolean mEnableOKPause;

    public KeyHandler(Activity activity, PlayerInterface playerFragment) {
        mActivity = activity;
        mFragment = playerFragment;
    }

    public boolean handle(KeyEvent event) {
        event = getKeyTranslator().doTranslateKeys(event);
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

        if (applySpecialKeyAction(event)) {
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

        // If the event was not handled then see if the player view can handle it as a media key event.
        return mFragment.getExoPlayerView().dispatchMediaKeyEvent(event);
    }

    @TargetApi(15)
    private boolean applySpecialKeyAction(KeyEvent event) {
        ExoPlayer player = mFragment.getPlayer();

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                player.setPlayWhenReady(true);
                break;
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_STOP:
                player.setPlayWhenReady(false);
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                player.setPlayWhenReady(!player.getPlayWhenReady());
                break;
            case KeyEvent.KEYCODE_MEDIA_STEP_FORWARD:
                player.seekTo(player.getCurrentPosition() + 10_000);
                break;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                player.seekTo(player.getCurrentPosition() - 10_000);
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                mFragment.getExoPlayerView().findViewById(R.id.exo_next2).callOnClick();
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                mFragment.getExoPlayerView().findViewById(R.id.exo_prev).callOnClick();
                break;
        }

        return false;
    }

    private KeyTranslator getKeyTranslator() {
        if (mTranslator == null) {
            mTranslator = new PlayerKeyTranslator();
        }

        return mTranslator;
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
}
