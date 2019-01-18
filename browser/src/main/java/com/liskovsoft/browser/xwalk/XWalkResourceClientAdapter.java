package com.liskovsoft.browser.xwalk;

import android.net.http.SslError;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Tab.MainWindowClient;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

public class XWalkResourceClientAdapter extends XWalkResourceClient {
    private MainWindowClient mWebViewClient;
    private WebChromeClient mWebChromeClient;
    private final WebView mWebView;

    public XWalkResourceClientAdapter(WebView webView, XWalkView view) {
        super(view);
        mWebView = webView;
    }

    public void setWebViewClient(WebViewClient client) {
        mWebViewClient = (MainWindowClient) client;
    }

    public void setWebChromeClient(WebChromeClient client) {
        mWebChromeClient = client;
    }

    @Override
    public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
        mWebViewClient.onReceivedSslError(mWebView, callback, error);
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
    public void onProgressChanged(XWalkView view, int progressInPercent) {
        mWebChromeClient.onProgressChanged(mWebView, progressInPercent);
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
