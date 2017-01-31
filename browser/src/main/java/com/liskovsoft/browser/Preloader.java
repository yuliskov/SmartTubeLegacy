package com.liskovsoft.browser;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import java.util.Map;

/**
 * Singleton class for handling preload requests.
 */
public class Preloader {

    private final static String LOGTAG = "browser.preloader";
    private final static boolean LOGD_ENABLED = com.liskovsoft.browser.Browser.LOGD_ENABLED;

    private static final int PRERENDER_TIMEOUT_MILLIS = 30 * 1000; // 30s

    private static Preloader sInstance;

    private final Context mContext;
    private final Handler mHandler;
    private final WebViewFactory mFactory;
    private volatile PreloaderSession mSession;

    public static void initialize(Context context) {
        sInstance = new Preloader(context);
    }

    public static Preloader getInstance() {
        return sInstance;
    }

    private Preloader(Context context) {
        mContext = context.getApplicationContext();
        mHandler = new Handler(Looper.getMainLooper());
        mSession = null;
        mFactory = new BrowserWebViewFactory(context);

    }

    private PreloaderSession getSession(String id) {
        if (mSession == null) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Create new preload session " + id);
            mSession = new PreloaderSession(id);
            WebViewTimersControl.getInstance().onPrerenderStart(
                    mSession.getWebView());
            return mSession;
        } else if (mSession.mId.equals(id)) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Returning existing preload session " + id);
            return mSession;
        }

        if (LOGD_ENABLED) Log.d(LOGTAG, "Existing session in progress : " + mSession.mId +
                " returning null.");
        return null;
    }

    private PreloaderSession takeSession(String id) {
        PreloaderSession s = null;
        if (mSession != null && mSession.mId.equals(id)) {
            s = mSession;
            mSession = null;
        }

        if (s != null) {
            s.cancelTimeout();
        }

        return s;
    }

    public void handlePreloadRequest(String id, String url, Map<String, String> headers,
            String searchBoxQuery) {
        PreloaderSession s = getSession(id);
        if (s == null) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Discarding preload request, existing"
                    + " session in progress");
            return;
        }

        s.touch(); // reset timer
        PreloadedTabControl tab = s.getTabControl();
        if (searchBoxQuery != null) {
            tab.loadUrlIfChanged(url, headers);
            tab.setQuery(searchBoxQuery);
        } else {
            tab.loadUrl(url, headers);
        }
    }

    public void cancelSearchBoxPreload(String id) {
        PreloaderSession s = getSession(id);
        if (s != null) {
            s.touch(); // reset timer
            PreloadedTabControl tab = s.getTabControl();
            tab.searchBoxCancel();
        }
    }

    public void discardPreload(String id) {
        PreloaderSession s = takeSession(id);
        if (s != null) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Discard preload session " + id);
            WebViewTimersControl.getInstance().onPrerenderDone(s == null ? null : s.getWebView());
            PreloadedTabControl t = s.getTabControl();
            t.destroy();
        } else {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Ignored discard request " + id);
        }
    }

    /**
     * Return a preloaded tab, and remove it from the preloader. This is used when the
     * view is about to be displayed.
     */
    public PreloadedTabControl getPreloadedTab(String id) {
        PreloaderSession s = takeSession(id);
        if (LOGD_ENABLED) Log.d(LOGTAG, "Showing preload session " + id + "=" + s);
        return s == null ? null : s.getTabControl();
    }

    private class PreloaderSession {
        private final String mId;
        private final PreloadedTabControl mTabControl;

        private final Runnable mTimeoutTask = new Runnable(){
            @Override
            public void run() {
                if (LOGD_ENABLED) Log.d(LOGTAG, "Preload session timeout " + mId);
                discardPreload(mId);
            }};

        public PreloaderSession(String id) {
            mId = id;
            mTabControl = new PreloadedTabControl(
                    new Tab(new PreloadController(mContext), mFactory.createWebView(false)));
            touch();
        }

        public void cancelTimeout() {
            mHandler.removeCallbacks(mTimeoutTask);
        }

        public void touch() {
            cancelTimeout();
            mHandler.postDelayed(mTimeoutTask, PRERENDER_TIMEOUT_MILLIS);
        }

        public PreloadedTabControl getTabControl() {
            return mTabControl;
        }

        public WebView getWebView() {
            Tab t = mTabControl.getTab();
            return t == null? null : t.getWebView();
        }

    }

}
