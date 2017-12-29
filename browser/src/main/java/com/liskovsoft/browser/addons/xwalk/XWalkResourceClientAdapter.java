package com.liskovsoft.browser.addons.xwalk;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

public class XWalkResourceClientAdapter extends XWalkResourceClient {
    private final WebViewClient mWebViewClient;
    private final WebView mWebView;

    public XWalkResourceClientAdapter(WebViewClient client, WebView webView, XWalkView view) {
        super(view);
        mWebViewClient = client;
        mWebView = webView;
    }

    @Override
    public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
        mWebViewClient.onReceivedError(mWebView, errorCode, description, failingUrl);
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
    public WebResourceResponse shouldInterceptLoadRequest(XWalkView view, String url) {
        return mWebViewClient.shouldInterceptRequest(mWebView, url);
    }

    @Override
    public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view, XWalkWebResourceRequest request) {
        return super.shouldInterceptLoadRequest(view, request);
    }

    // don't rely on it: shouldOverrideUrlLoading called one time fore session, use shouldInterceptLoadRequest instead
    @Override
    public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
        return mWebViewClient.shouldOverrideUrlLoading(mWebView, url);
    }
}
