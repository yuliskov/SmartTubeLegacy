package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.browser.fragments.FragmentManager;
import com.liskovsoft.browser.fragments.GenericFragment;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.PlayerFragment;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class ExoPlayerFragment extends ExoPlayerBaseFragment implements PlayerFragment {
    private int mState;
    private View mWrapper;
    private boolean mIsAttached;
    private Intent mPendingIntent;

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
    }

    @Override
    public void finish() {
        // NOP, since we are using fragments
    }

    @Override
    public void onNewIntent(Intent intent) {
        // NOP, since we're not handling activity's intents
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

    @Override
    public void onStart() {
        super.onStart();

        mIsAttached = true;

        // manager wants this fragment to be paused
        if (getState() == GenericFragment.STATE_PAUSED) {
            return;
        }

        if (Util.SDK_INT > 23) {
            performInitialization();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // manager already've paused this fragment
        if (getState() == GenericFragment.STATE_PAUSED) {
            return;
        }

        if ((Util.SDK_INT <= 23 || player == null)) {
            performInitialization();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getState() == GenericFragment.STATE_PAUSED) {
            return;
        }

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        mIsAttached = false;

        if (getState() == GenericFragment.STATE_PAUSED) {
            return;
        }

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    /**
     * Entry point for video playback
     * @param intent video info
     */
    @Override
    public void openVideo(Intent intent) {
        if (!mIsAttached) {
            mPendingIntent = intent;
            return;
        }

        if (intent != null) {
            openVideoFromIntent(intent);
        }
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void onPauseFragment() {
        mState = GenericFragment.STATE_PAUSED;
        releasePlayer();
    }

    @Override
    public void onResumeFragment() {
        mState = GenericFragment.STATE_RESUMED;

        if (!mIsAttached) {
            return;
        }

        initializePlayer();
    }

    @Override
    public View getWrapper() {
        return mWrapper;
    }

    @Override
    public void setWrapper(View wrapper) {
        mWrapper = wrapper;
    }

    /**
     * Bugfix: {@link #getActivity} is null inside {@link #openVideo} and {@link #onResumeFragment()} routines
     */
    public void performInitialization() {
        if (!mIsAttached)
            return;

        if (mPendingIntent != null) {
            openVideoFromIntent(mPendingIntent);
            mPendingIntent = null;
            return;
        }

        initializePlayer();
    }
}
