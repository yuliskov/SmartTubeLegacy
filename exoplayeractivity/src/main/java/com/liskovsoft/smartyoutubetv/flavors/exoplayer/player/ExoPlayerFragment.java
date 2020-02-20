package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManagerAlt;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.keyhandler.KeyHandler;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.keyhandler.KeyHandlerFactory;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class ExoPlayerFragment extends ExoPlayerBaseFragment {
    private static final String TAG = ExoPlayerFragment.class.getSimpleName();
    private final AutoFrameRateManager mListener;
    private int mState;
    private View mWrapper;
    private boolean mIsAttached;
    private Intent mPendingIntent;

    public ExoPlayerFragment() {
        mListener = new AutoFrameRateManagerAlt(this);
        addEventListener(mListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // NOTE: entry point to handle keys
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mKeyHandler.handle(event);
    }

    @Override
    public void onBackPressed() {
        onPlayerAction(ExoPlayerBaseFragment.BUTTON_BACK);
    }

    @Override
    public void showSoftKeyboard() {
        // NOP, since no kbd here
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

        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
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
        } else {
            // loop video while user page or suggestions displayed
            mButtonsManager.syncRepeatButton();
        }
    }

    @Override
    public void close() {
         onPlayerAction(ExoPlayerFragment.BUTTON_BACK);
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void onPauseFragment() {
        mState = GenericFragment.STATE_PAUSED;

        // fragment switched: don't confuse it with onStop/onPause standard events
        releasePlayer();
    }

    @Override
    public void onResumeFragment() {
        mState = GenericFragment.STATE_RESUMED;

        if (!mIsAttached) {
            return;
        }

        // prevent double init: player will be initialized later in openVideo routine
        // initializePlayer();
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
    private void performInitialization() {
        if (!mIsAttached) {
            return;
        }

        if (mPendingIntent != null) {
            Log.d(TAG, "Open video from pending intent");
            openVideoFromIntent(mPendingIntent);
            mPendingIntent = null;
        } else if (getIntent() != null) {
            syncButtonStates(); // onCheckedChanged depends on this
            initializePlayer();
            //initializeUiScale();
        }
    }

    @Override
    public void onMemoryCritical() {
        // NOP
    }

    public AutoFrameRateManager getAutoFrameRateManager() {
        return mListener;
    }

    @Override
    public void onLoad() {
        // NOP
    }

    public void showMessage(String message, long hideDelay) {
        View root = getView();

        if (root == null) {
            return;
        }

        TextView afrView = root.findViewById(R.id.afr_loading_view);
        FragmentActivity activity = getActivity();

        if (afrView != null && activity != null) {
            afrView.setVisibility(View.VISIBLE);
            afrView.setText(message);

            new Handler(activity.getMainLooper()).postDelayed(() -> {
                afrView.setVisibility(View.GONE);
                afrView.setText("");
            }, hideDelay);
        }
    }
}
