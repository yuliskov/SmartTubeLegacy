package com.liskovsoft.browser;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.liskovsoft.browser.addons.SimpleUIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BrowserActivity extends AppCompatActivity {
    private static final Logger logger = LoggerFactory.getLogger(BrowserActivity.class);
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
    private Bundle mIcicle;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // this routine is a simple demonstration what you can do with controller
        if (this.getClass() == BrowserActivity.class) {
            createController(icicle);
        }
    }

    //protected void beginInitWebView(Bundle icicle) {
    //    createController(icicle);
    //}

    private void createController(Bundle icicle) {
        mHeaders = new HashMap<>();
        mHeaders.put("user-agent", mDefaultUserAgent);

        mController = new SimpleUIController(this);
        Intent intent = (icicle == null) ? getIntent() : null;
        mController.start(intent);
        mController.loadUrl(mDefaultHomeUrl, mHeaders);
    }


    protected void setController(Controller controller) {
        mController = controller;
    }

    private void saveBrowserState(Bundle outState) {
        if (mController == null)
            return;
        logger.info("BrowserActivity.onSaveInstanceState: this=", this);
        mController.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        // NOTE: fix state saving when finishing activity
        saveBrowserState(null);
        super.finish();
        System.exit(0);
    }

    /**
     *  onSaveInstanceState(Bundle map)
     *  onSaveInstanceState is called right before onStop(). The map contains
     *  the saved state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveBrowserState(outState);
    }

    @Override
    public void onLowMemory() {
        // msg 4 future me
        // don't place here save state calls - it won't help
        super.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mController == null) {
            return;
        }
        logger.info("BrowserActivity.onPause: this=" + this);
        mController.onPause();
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

    @Override
    protected void onDestroy() {
        super.onResume();
        if (mController == null) {
            return;
        }
        logger.info("BrowserActivity.onDestroy: this=" + this);
        // BUGFIX: fixing bug with Resuming webview timers (friezed youtube logo)
        mController.onResume();
    }

    public static boolean isTablet(Activity activity) {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mController == null) {
            return false;
        }
        return mController.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (mController == null) {
            return false;
        }
        return mController.onKeyLongPress(keyCode, event) ||
                super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mController == null) {
            return false;
        }
        return mController.onKeyUp(keyCode, event) ||
                super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (mController == null || intent == null) {
            return;
        }
        if (shouldIgnoreIntents()) return;
        if (ACTION_RESTART.equals(intent.getAction())) {
            Bundle outState = new Bundle();
            mController.onSaveInstanceState(outState);
            finish();
            getApplicationContext().startActivity(
                    new Intent(getApplicationContext(), BrowserActivity.class)
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