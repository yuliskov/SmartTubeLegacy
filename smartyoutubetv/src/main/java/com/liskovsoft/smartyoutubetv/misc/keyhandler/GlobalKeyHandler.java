package com.liskovsoft.smartyoutubetv.misc.keyhandler;

import android.app.Activity;
import android.os.Handler;
import android.view.KeyEvent;
import androidx.annotation.Nullable;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;

public class GlobalKeyHandler {
    private static final String TAG = GlobalKeyHandler.class.getSimpleName();
    private final boolean mBackPressExitEnabled;
    private final Handler mHandler;
    private final Runnable mExitAppFn;
    private final Runnable mResetExitFn = () -> {mDoubleBackToExitPressedTimes = 0; mEnableDoubleBackExit = false;};
    private static final long BACK_PRESS_DURATION_MS = 1_000;
    private boolean mEnableDoubleBackExit;
    private int mDoubleBackToExitPressedTimes;
    private boolean mDownPressed;

    public GlobalKeyHandler(Activity ctx) {
        mHandler = new Handler(ctx.getMainLooper());
        mBackPressExitEnabled = CommonApplication.getPreferences() != null && CommonApplication.getPreferences().getEnableBackPressExit();
        mExitAppFn = () -> {
            MessageHelpers.showMessage(ctx, R.string.close_msg);
            ctx.finish();
        };
    }

    public void checkLongPressExit(KeyEvent event) {
        if (!mBackPressExitEnabled) {
            return;
        }

        boolean isBack =
                event.getKeyCode() == KeyEvent.KEYCODE_BACK ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_B;

        if (event.getAction() == KeyEvent.ACTION_DOWN && isBack) {
            if (event.getRepeatCount() == 0) { // same event fires multiple times
                mHandler.postDelayed(mExitAppFn, BACK_PRESS_DURATION_MS);
            }
        } else {
            mHandler.removeCallbacks(mExitAppFn);
        }
    }

    @Nullable
    public KeyEvent translateKey(KeyEvent event) {
        if (ignoreEvent(event)) {
            Log.d(TAG, "Oops. Seems phantom key received. Ignoring... " + event);
            return null;
        }

        if (isReservedKey(event)) {
            Log.d(TAG, "Found globally reserved key. Ignoring..." + event);
            return null;
        }

        checkBackPressed(event);

        return gamepadFix(event);
    }

    private KeyEvent unknownKeyFix(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_UNKNOWN &&
            event.getScanCode() == 68) {
            event = Helpers.newEvent(event, KeyEvent.KEYCODE_BACK);
        }

        return event;
    }

    private KeyEvent gamepadFix(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BUTTON_R1:
            case KeyEvent.KEYCODE_BUTTON_R2:
                event = Helpers.newEvent(event, KeyEvent.KEYCODE_DPAD_RIGHT);
                break;
            case KeyEvent.KEYCODE_BUTTON_L1:
            case KeyEvent.KEYCODE_BUTTON_L2:
                event = Helpers.newEvent(event, KeyEvent.KEYCODE_DPAD_LEFT);
                break;
            case KeyEvent.KEYCODE_BUTTON_A:
                event = Helpers.newEvent(event, KeyEvent.KEYCODE_DPAD_CENTER);
                break;
        }

        return event;
    }

    public void checkDoubleBackExit() {
        mEnableDoubleBackExit = true;
        mHandler.postDelayed(mResetExitFn, 1000);
    }

    private void checkBackPressed(KeyEvent event) {
        if (!mEnableDoubleBackExit) {
            return;
        }

        if (event.getAction() == KeyEvent.ACTION_UP) {
            return;
        }

        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK &&
            event.getKeyCode() != KeyEvent.KEYCODE_B &&
            event.getKeyCode() != KeyEvent.KEYCODE_ESCAPE) {
            mResetExitFn.run();
            mHandler.removeCallbacks(mResetExitFn);
            return;
        }

        if (mDoubleBackToExitPressedTimes >= 0) {
            // exit action
            mExitAppFn.run();
            return;
        }

        mDoubleBackToExitPressedTimes++;

        if (mDoubleBackToExitPressedTimes == 1) {
            mHandler.postDelayed(mResetExitFn, 1000);
        }
    }

    /**
     * Ignore unpaired ACTION_UP events<br/>
     * Ignore UNKNOWN key codes
     */
    private boolean ignoreEvent(KeyEvent event) {
        if (event == null || event.getKeyCode() == KeyEvent.KEYCODE_UNKNOWN) {
            return true;
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mDownPressed = true;
            return false;
        }

        if (event.getAction() == KeyEvent.ACTION_UP && mDownPressed) {
            mDownPressed = false;
            return false;
        }

        return true;
    }

    private boolean isReservedKey(KeyEvent event) {
        // Philips ambilight button
        int KEYCODE_SVC_EXIT = 319;

        if (event.getKeyCode() == KEYCODE_SVC_EXIT) {
            return true;
        }

        return false;
    }

    /**
     * Ignore non-paired key up events
     *
     * @param event event
     * @return is ignored
     */
    //private boolean isEventIgnoredOld(KeyEvent event) {
    //    mDownPressed = mDownPressed < 0 ? 0 : mDownPressed; // do reset sometimes
    //
    //    if (event.getAction() == KeyEvent.ACTION_DOWN) {
    //        mDownPressed++;
    //        return false;
    //    }
    //
    //    if (event.getAction() == KeyEvent.ACTION_UP && mDownPressed > 0) {
    //        mDownPressed--;
    //        return false;
    //    }
    //
    //    return true;
    //}
}
