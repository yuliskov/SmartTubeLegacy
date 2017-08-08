package com.liskovsoft.smartyoutubetv.webviewstuff;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.events.SupportedVideoFormatsEvent;
import com.squareup.otto.Subscribe;

public class MyJsCssTweaksInjector extends ResourceInjectorBase {
    private final Context mContext;
    private final WebView mWebView;

    public MyJsCssTweaksInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
        mWebView = webView;
    }

    public void inject() {
        injectCSSOnce("main.css");
        injectJSOnce("common.js");
        injectJSOnce("quality-controls.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJSOnce("xwalk.js");
        } else {
            injectJSOnce("webview.js");
        }
    }

    @Subscribe
    public void notifyAboutSupportedVideoFormats(SupportedVideoFormatsEvent event) {
        injectJSAsIs("notifyAboutSupportedVideoFormats(['hd', 'sd', 'lq'])");
    }
}
