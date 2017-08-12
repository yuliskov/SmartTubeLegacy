package com.liskovsoft.smartyoutubetv.webviewstuff;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.browser.custom.PageLoadHandler;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.DecipherRoutineInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPageLoadHandler implements PageLoadHandler {
    private final Context mContext;
    private WebViewJavaScriptInterface mJS;
    private MyJsCssTweaksInjector mInjector;
    private VideoFormatInjector mNotification;
    private DecipherRoutineInjector mDecipherRoutineInjector;
    private static final Logger logger = LoggerFactory.getLogger(MyPageLoadHandler.class);

    public MyPageLoadHandler(Context context) {
        mContext = context;
    }

    @Override
    public void onPageFinished(Tab tab) {
        logger.info("onPageFinished called");
        WebView w = tab.getWebView();
        injectWebFiles(w);
    }

    @Override
    public void onPageStarted(Tab tab) {
        logger.info("onPageStarted called");
        // js must be added before page fully loaded
        addJSInterface(tab);

        //WebView w = tab.getWebView();
        //injectWebFiles(w);
    }

    @Override
    public WebViewClient overrideWebViewClient(WebViewClient client) {
        return new MyWebViewClientDecorator(client, mContext);
    }

    @Override
    public WebChromeClient overrideWebChromeClient(WebChromeClient client) {
        return client;
    }

    private void addJSInterface(Tab tab) {
        if (mJS != null) {
            return;
        }

        mJS = new WebViewJavaScriptInterface(mContext, tab);
        WebView webView = tab.getWebView();
        webView.addJavascriptInterface(mJS, "app");
    }

    private void injectWebFiles(WebView w) {
        if (mInjector == null)
            mInjector = new MyJsCssTweaksInjector(mContext, w);
        if (mNotification == null)
            mNotification = new VideoFormatInjector(mContext, w);
        if (mDecipherRoutineInjector == null)
            mDecipherRoutineInjector = new DecipherRoutineInjector(mContext, w);

        mInjector.inject();
    }
}
