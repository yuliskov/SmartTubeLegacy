package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.events.SupportedVideoFormatsEvent;
import com.squareup.otto.Subscribe;

public class ResourceInjector extends ResourceInjectorBase {
    private final Context mContext;
    private final WebView mWebView;

    public ResourceInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
        mWebView = webView;
    }

    public void inject() {
        injectCSSSafe("main.css");
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
        Handler mainHandler = new Handler(mContext.getMainLooper());
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runJS("notifyAboutSupportedVideoFormats(['hd', 'sd', 'lq'])");
            }
        }, 1000); // delay because page not fully loaded at this point
    }
}
