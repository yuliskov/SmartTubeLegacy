package com.liskovsoft.browser.custom;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BrowserActivityController {
    private static final Logger logger = LoggerFactory.getLogger(BrowserActivityController.class);
    private final Activity mActivity;
    private Controller mController;
    private final String mDefaultHomeUrl = "https://google.com";
    private final String mDefaultUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    private Map<String, String> mHeaders;

    public static final String ACTION_SHOW_BOOKMARKS = "show_bookmarks";
    public static final String ACTION_SHOW_BROWSER = "show_browser";
    public static final String ACTION_RESTART = "--restart--";
    private static final String EXTRA_STATE = "state";
    public static final String EXTRA_DISABLE_URL_OVERRIDE = "disable_url_override";
    private KeyguardManager mKeyguardManager;
    private PowerManager mPowerManager;

    public BrowserActivityController(Activity activity) {
        mActivity = activity;
    }

    /**
     *  onSaveInstanceState(Bundle map)
     *  onSaveInstanceState is called right before onStop(). The map contains
     *  the saved state.
     */
    protected void onSaveInstanceState(Bundle outState) {
        logger.info("BrowserActivity.onSaveInstanceState: this=", this);
        mController.onSaveInstanceState(outState);
    }

    protected Controller createAndInitController(Bundle icicle) {
        mHeaders = new HashMap<>();
        mHeaders.put("user-agent", mDefaultUserAgent);

        mController = new SimpleUIController(mActivity);
        Intent intent = (icicle == null) ? mActivity.getIntent() : null;
        mController.start(intent);
        mController.loadUrl(mDefaultHomeUrl, mHeaders);
        return mController;
    }

    protected void onPause() {
        mController.onPause();
    }

    protected void onResume() {
        if (mController == null) {
            return;
        }
        logger.info("BrowserActivity.onResume: this=" + this);
        mController.onResume();
    }

    public static boolean isTablet(Activity activity) {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mController == null)
            return false;
        return mController.onKeyDown(keyCode, event);
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mController.onKeyLongPress(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mController == null)
            return false;
        return mController.onKeyUp(keyCode, event);
    }

    protected void onNewIntent(Intent intent) {
        if (shouldIgnoreIntents()) return;
        if (ACTION_RESTART.equals(intent.getAction())) {
            Bundle outState = new Bundle();
            mController.onSaveInstanceState(outState);
            mActivity.finish();
            mActivity.getApplicationContext().startActivity(
                    new Intent(mActivity.getApplicationContext(), BrowserActivityController.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(EXTRA_STATE, outState));
            return;
        }
        mController.handleNewIntent(intent);
    }

    private boolean shouldIgnoreIntents() {
        // Only process intents if the screen is on and the device is unlocked
        // aka, if we will be user-visible
        if (mKeyguardManager == null) {
            mKeyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        }
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) mActivity.getSystemService(Context.POWER_SERVICE);
        }
        boolean ignore = !mPowerManager.isScreenOn();
        ignore |= mKeyguardManager.inKeyguardRestrictedInputMode();
        logger.info("ignore intents: {}", ignore);
        return ignore;
    }
}