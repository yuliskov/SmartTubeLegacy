package com.liskovsoft.smartyoutubetv.misc.keyhandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.KeyEvent;
import androidx.annotation.Nullable;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.KeyHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;

public class GlobalKeyHandler {
    private static final String TAG = GlobalKeyHandler.class.getSimpleName();
    private final boolean mBackPressExitEnabled;
    private final Handler mHandler;
    private final Activity mContext;
    private final Runnable mExitAppFn;
    private final Runnable mResetExit = () -> mEnableDoubleBackExit = false;
    private static final long BACK_PRESS_DURATION_MS = 1_000;
    private boolean mEnableDoubleBackExit;
    private boolean mDownPressed;
    private long mLastEventTimeMs;
    private int mLastEventCode;

    public GlobalKeyHandler(Activity ctx) {
        mHandler = new Handler(ctx.getMainLooper());
        mContext = ctx;
        mBackPressExitEnabled = CommonApplication.getPreferences() != null && CommonApplication.getPreferences().getEnableBackPressExit();
        mExitAppFn = () -> {
            Log.d(TAG, "Closing the app...");

            mContext.moveTaskToBack(true);
        };
    }

    public void checkShortcut(KeyEvent event) {
        checkLongPressExit(event);
        checkSearchKey(event);
    }

    /**
     * Handle searches on the bluetooth keyboard.
     */
    private void checkSearchKey(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_SLASH && event.getAction() == KeyEvent.ACTION_UP) {
            if (mContext instanceof FragmentManager) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://empty.url?search_query=shortcut_pressed"));
                ((FragmentManager) mContext).handleIntent(intent);
            }
        }
    }

    private void checkLongPressExit(KeyEvent event) {
        if (!mBackPressExitEnabled) {
            return;
        }

        boolean isBack =
                event.getKeyCode() == KeyEvent.KEYCODE_BACK ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_B;

        if (event.getAction() == KeyEvent.ACTION_DOWN && isBack) {
            if (event.getRepeatCount() == 3) { // same event fires multiple times
                mExitAppFn.run();
            }
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

        mLastEventTimeMs = System.currentTimeMillis();
        mLastEventCode = event.getKeyCode();

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
    }

    private void checkBackPressed(KeyEvent event) {
        if (!mEnableDoubleBackExit) {
            return;
        }

        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK &&
            event.getKeyCode() != KeyEvent.KEYCODE_B &&
            event.getKeyCode() != KeyEvent.KEYCODE_ESCAPE) {
            mResetExit.run();
            return;
        }

        if (event.getAction() == KeyEvent.ACTION_UP) { // should be UP
            return;
        }

        Log.d(TAG, "Checking double back exit...");

        // exit action
        mExitAppFn.run();
        mResetExit.run();
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
        return KeyHelpers.isAmbilightKey(event.getKeyCode());
    }

    public int getLastEventKeyCode() {
        return mLastEventCode;
    }

    public long getLastEventTimeMs() {
        return mLastEventTimeMs;
    }
}
