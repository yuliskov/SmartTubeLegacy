package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.browser.fragments.FragmentManager;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.PlayerFragment;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class ExoPlayerFragment extends ExoPlayerBaseFragment implements PlayerFragment {
    // NOTE: entry point to handle keys
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        event = translateEscapeToBack(event);
        setDispatchEvent(event);

        if (isVolumeEvent(event))
            return false;

        if (isBackKey(event) && !isUiVisible() && event.getAction() == KeyEvent.ACTION_UP) {
            onBackPressed();
        }

        if (isBackKey(event) || isUpKey(event)) {
            return hideUI(event);
        }

        // Show the controls on any key event.
        simpleExoPlayerView.showController();

        // If the event was not handled then see if the player view can handle it as a media key event.
        return simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }

    // Activity input

    private boolean isBackKey(KeyEvent event) {
        return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
    }

    private boolean isUpKey(KeyEvent event) {
        View root = getView();
        if (root == null)
            throw new IllegalStateException("Fragment's root view is null");

        boolean isUp = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP;
        if (isUp) {
            View upBtn = root.findViewById(R.id.up_catch_button);
            return upBtn.isFocused();
        }
        return false;
    }

    private boolean hideUI(KeyEvent event) {
        boolean isUp = event.getAction() == KeyEvent.ACTION_UP;
        boolean isVisible = isUiVisible();

        if (isVisible) {
            if (isUp) {
                simpleExoPlayerView.hideController();
            }
            return true;
        }

        return false;
    }

    private boolean isUiVisible() {
        return interfaceVisibilityState == View.VISIBLE;
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

    private boolean isVolumeEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP ||
                event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return true;
        }
        return false;
    }

    private void setDispatchEvent(KeyEvent event) {
        if (getActivity() instanceof FragmentManager) {
            ((FragmentManager) getActivity()).setDispatchEvent(event);
        }
    }

    @Override
    public void onBackPressed() {
        doGracefulExit(ExoPlayerBaseFragment.BUTTON_BACK);

        // moveTaskToBack(true); // don't exit at this point
    }

    @Override
    public void finish() {
        // TODO: not implemented??
        // super.finish();
    }

    @Override
    public void onNewIntent(Intent intent) {
        // NOP, since we're not handling activity's intents

        //releasePlayer();
        //shouldAutoPlay = true;
        //clearResumePosition();
        //setIntent(intent);
    }

    @Override
    public void openVideo(Intent intent) {
        releasePlayer();
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
        buttonsManager.syncButtonStates(); // onCheckedChanged depends on this
        initializePlayer();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return false;
    }
}
