package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.os.Handler;
import android.util.Base64;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.browser.custom.WebViewBrowserActivity;
import com.liskovsoft.browser.xwalk.XWalkBrowserActivity;
import com.liskovsoft.smartyoutubetv.events.SupportedVideoFormatsEvent;
import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceInjector extends ResourceInjectorBase {
    private final Context mContext;
    private final WebView mWebView;
    private boolean mAlreadyInjected;

    public ResourceInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
        mWebView = webView;
    }

    public void inject() {
        if (isAlreadyInjected())
            return;

        injectCSS("main.css");
        injectJS("common.js");
        injectJS("quality-controls.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJS("xwalk.js");
        } else {
            injectJS("webview.js");
        }
    }

    private boolean isAlreadyInjected() {
        if (mAlreadyInjected)
            return true;

        mAlreadyInjected = true;
        return false;
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
