package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.browser.custom.PageLoadHandler;

public class MyPageLoadHandler implements PageLoadHandler {
    private final Context mContext;
    private WebViewJavaScriptInterface mJS;
    private ResourceInjector mInjector;

    public MyPageLoadHandler(Context context) {
        mContext = context;
    }

    @Override
    public void onPageFinished(Tab tab) {
        WebView w = tab.getWebView();
        injectWebFiles(w);
    }

    @Override
    public void onPageStarted(Tab tab) {
        WebView w = tab.getWebView();
        // js must be added before page fully loaded
        addJSInterface(w);
        //injectWebFiles(w);
    }

    @Override
    public WebViewClient overrideWebViewClient(WebViewClient client) {
        return new MyWebViewClientDecorator(client);
    }

    @Override
    public WebChromeClient overrideWebChromeClient(WebChromeClient client) {
        return client;
    }

    private void addJSInterface(WebView w) {
        mJS = new WebViewJavaScriptInterface(mContext);
        w.addJavascriptInterface(mJS, "app");
    }

    private void injectWebFiles(WebView w) {
        mInjector = new ResourceInjector(mContext, w);
        mInjector.inject();
    }
}
