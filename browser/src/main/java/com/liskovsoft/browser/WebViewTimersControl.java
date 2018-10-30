package com.liskovsoft.browser;

import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

/**
 * Centralised point for controlling WebView timers pausing and resuming.
 *
 * All methods on this class should only be called from the UI thread.
 */
public class WebViewTimersControl {

    private static final boolean LOGD_ENABLED = com.liskovsoft.browser.Browser.LOGD_ENABLED;
    private static final String LOGTAG = "WebViewTimersControl";

    private static WebViewTimersControl sInstance;

    private boolean mBrowserActive;
    private boolean mPrerenderActive;

    /**
     * Get the static instance. Must be called from UI thread.
     */
    public static WebViewTimersControl getInstance() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("WebViewTimersControl.get() called on wrong thread");
        }
        if (sInstance == null) {
            sInstance = new WebViewTimersControl();
        }
        return sInstance;
    }

    private WebViewTimersControl() {
    }

    private void resumeTimers(WebView wv) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "Resuming webview timers, view=" + wv);
        if (wv != null) {
            wv.resumeTimers();
            wv.onResume();
        }
    }

    private void maybePauseTimers(WebView wv) {
        if (!mBrowserActive && !mPrerenderActive && wv != null) {
            if (LOGD_ENABLED)
                Log.d(LOGTAG, "Pausing webview timers, view=" + wv);
            wv.pauseTimers();
            wv.onPause();
        }
    }

    public void onBrowserActivityResume(WebView wv) {
        if (LOGD_ENABLED)
            Log.d(LOGTAG, "onBrowserActivityResume");
        mBrowserActive = true;
        resumeTimers(wv);
    }

    public void onBrowserActivityPause(WebView wv) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onBrowserActivityPause");
        mBrowserActive = false;
        maybePauseTimers(wv);
    }

    public void onPrerenderStart(WebView wv) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onPrerenderStart");
        mPrerenderActive = true;
        resumeTimers(wv);
    }

    public void onPrerenderDone(WebView wv) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "onPrerenderDone");
        mPrerenderActive = false;
        maybePauseTimers(wv);
    }
}

