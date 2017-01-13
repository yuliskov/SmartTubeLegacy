package com.liskovsoft.browser.xwalk;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.util.SimpleUIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xwalk.core.XWalkActivity;

import java.util.HashMap;
import java.util.Map;

public class XWalkBrowserActivity_TEMP extends XWalkActivity {
    private static final Logger logger = LoggerFactory.getLogger(XWalkBrowserActivity_TEMP.class);
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

    @Override
    protected void onXWalkReady() {
        mController = createAndInitController(null);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        //mController = createAndInitController(icicle);
    }

    /**
     *  onSaveInstanceState(Bundle map)
     *  onSaveInstanceState is called right before onStop(). The map contains
     *  the saved state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        logger.info("BrowserActivity.onSaveInstanceState: this=", this);
        mController.onSaveInstanceState(outState);
    }

    protected Controller createAndInitController(Bundle icicle) {
        mHeaders = new HashMap<>();
        mHeaders.put("user-agent", mDefaultUserAgent);

        mController = new SimpleUIController(this);
        Intent intent = (icicle == null) ? getIntent() : null;
        mController.start(intent);
        mController.loadUrl(mDefaultHomeUrl, mHeaders);
        return mController;
    }

    @Override
    protected void onPause() {
        mController.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mController == null) {
            return;
        }
        logger.info("BrowserActivity.onResume: this=" + this);
        mController.onResume();
    }

    public static boolean isTablet(Activity activity) {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mController == null)
            return false;
        return mController.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mController.onKeyLongPress(keyCode, event) ||
                super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mController == null)
            return false;
        return mController.onKeyUp(keyCode, event) ||
                super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (shouldIgnoreIntents()) return;
        if (ACTION_RESTART.equals(intent.getAction())) {
            Bundle outState = new Bundle();
            mController.onSaveInstanceState(outState);
            finish();
            getApplicationContext().startActivity(
                    new Intent(getApplicationContext(), XWalkBrowserActivity_TEMP.class)
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
            mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        }
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        }
        boolean ignore = !mPowerManager.isScreenOn();
        ignore |= mKeyguardManager.inKeyguardRestrictedInputMode();
        logger.info("ignore intents: {}", ignore);
        return ignore;
    }
}