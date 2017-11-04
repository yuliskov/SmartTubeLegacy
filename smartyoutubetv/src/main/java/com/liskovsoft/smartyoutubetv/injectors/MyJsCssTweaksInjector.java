package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events.SupportedVideoFormatsEvent;
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
        injectCSSAssetOnce("main.css");
        injectJSAssetOnce("common.js");
        injectJSAssetOnce("quality-controls.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJSAssetOnce("xwalk.js");
        } else {
            injectJSAssetOnce("webview.js");
        }
    }

    @Subscribe
    public void notifyAboutSupportedVideoFormats(SupportedVideoFormatsEvent event) {
        injectJSContent("notifyAboutSupportedVideoFormats(['hd', 'sd', 'lq'])");
    }
}
