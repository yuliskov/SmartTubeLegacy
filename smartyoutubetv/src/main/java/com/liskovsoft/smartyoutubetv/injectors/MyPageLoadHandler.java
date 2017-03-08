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
    private VideoFormatNotification mNotification;

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
        // js must be added before page fully loaded
        addJSInterface(tab);
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
            mInjector = new ResourceInjector(mContext, w);
        if (mNotification == null)
            mNotification = new VideoFormatNotification(mContext, w);
        mInjector.inject();
    }
}
