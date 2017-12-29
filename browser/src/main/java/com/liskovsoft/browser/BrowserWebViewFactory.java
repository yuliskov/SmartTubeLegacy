package com.liskovsoft.browser;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.browser.addons.HeadersBrowserWebView;
import com.liskovsoft.browser.addons.xwalk.XWalkWebViewAdapter;

import java.util.Map;

public class BrowserWebViewFactory implements WebViewFactory {
    protected final Context mContext;
    protected Map<String, String> mNextHeaders;

    public BrowserWebViewFactory(Context browser) {
        mContext = browser;
    }

    protected WebView instantiateWebView(AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        WebView w;
        boolean isXWalk = Browser.getEngineType() == EngineType.XWalk;
        if (isXWalk) {
            w = new XWalkWebViewAdapter(mNextHeaders, mContext, attrs, defStyle, privateBrowsing);
            // real display size (virtual pixel == real pixel)
            // 100 - normal resolution, 50 - 2160p resolution
            w.setInitialScale(100);
        } else {
            // BUGFIX: rare memory leak in WebView
            w = new HeadersBrowserWebView(mNextHeaders, mContext.getApplicationContext(), attrs, defStyle, privateBrowsing);
        }

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
