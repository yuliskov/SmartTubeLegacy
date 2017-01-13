package com.liskovsoft.browser.xwalk;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class XWalkResourceClientAdapter extends XWalkResourceClient {
    private final WebViewClient mWebViewClient;
    private final WebView mWebView;

    public XWalkResourceClientAdapter(WebViewClient client, WebView webView, XWalkView view) {
        super(view);
        mWebViewClient = client;
        mWebView = webView;
    }

    public WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    @Override
    public void onLoadStarted(XWalkView view, String url) {
        mWebViewClient.onPageStarted(mWebView, url, null);
    }

    @Override
    public void onLoadFinished(XWalkView view, String url) {
        mWebViewClient.onPageFinished(mWebView, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
        return mWebViewClient.shouldOverrideUrlLoading(mWebView, url);
    }
}
