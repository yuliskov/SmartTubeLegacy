package com.liskovsoft.browser;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.browser.addons.HeadersBrowserWebView;
import com.liskovsoft.browser.xwalk.XWalkWebViewAdapter;
import com.liskovsoft.sharedutils.mylogger.Log;
import org.xwalk.core.XWalkPreferences;

import java.util.Map;

public class BrowserWebViewFactory implements WebViewFactory {
    private static final String TAG = BrowserWebViewFactory.class.getSimpleName();
    protected final Context mContext;
    protected Map<String, String> mNextHeaders;
    private final boolean mIsXWalk;

    public BrowserWebViewFactory(Context browser) {
        mContext = browser;
        mIsXWalk = Browser.getEngineType() == EngineType.XWalk;

        initRemoteDebugging();
    }

    @TargetApi(19)
    private void initRemoteDebugging() {
        if (!BuildConfig.DEBUG) {
            return;
        }

        if (mIsXWalk) {
            XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        } else {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    /**
     * Creates {@link WebView} instance. Scale setting moved to {@link BrowserSettings#getInitialScale()}
     * @param attrs view attrs
     * @param defStyle view style
     * @param privateBrowsing don't remember anything
     * @return new WebView instance
     */
    protected WebView instantiateWebView(AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        WebView w;

        if (mIsXWalk) {
            Log.d(TAG, "Instantiating XWalkView...");
            w = new XWalkWebViewAdapter(mNextHeaders, mContext, attrs, defStyle, privateBrowsing);
        } else {
            // BUGFIX: rare memory leak in WebView
            Log.d(TAG, "Instantiating WebView...");
            w = new HeadersBrowserWebView(mNextHeaders, mContext.getApplicationContext(), attrs, defStyle, privateBrowsing);
        }

        w.clearCache(true);

        // NOTE: scale moved to: com.liskovsoft.browser.BrowserSettings.getInitialScale()

        return w;
    }

    @Override
    public WebView createWebView(boolean privateBrowsing) {
        WebView w = instantiateWebView(null, android.R.attr.webViewStyle, privateBrowsing);
        initWebViewSettings(w);
        return w;
    }

    @Override
    public WebView createSubWebView(boolean privateBrowsing) {
        return null;
    }

    public WebView createWebView() {
        return createWebView(false);
    }

    /**
     * We must decorate setting as early as possible
     * @param headers
     */
    @Override
    public void setNextHeaders(Map<String, String> headers) {
        mNextHeaders = headers;
    }

    @Override
    public Map<String, String> getNextHeaders() {
        return mNextHeaders;
    }

    protected void initWebViewSettings(WebView w) {
        w.setScrollbarFadingEnabled(true);
        w.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        w.setMapTrackballToArrowKeys(false); // use trackball directly
        // Enable the built-in zoom
        w.getSettings().setBuiltInZoomControls(true);
        final PackageManager pm = mContext.getPackageManager();
        boolean supportsMultiTouch =
                pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)
                        || pm.hasSystemFeature(PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT);
        w.getSettings().setDisplayZoomControls(!supportsMultiTouch);

        // Add this WebView to the settings observer list and update the
        // settings
        final BrowserSettings s = BrowserSettings.getInstance();
        s.startManagingSettings(w.getSettings());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // Remote Web Debugging is always enabled, where available.
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
}
