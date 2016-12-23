package com.liskovsoft.browser;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserWebView extends WebView {
    private WebViewClient mWebViewClient;

    public BrowserWebView(Context context) {
        super(context);
    }

    public BrowserWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrowserWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BrowserWebView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        mWebViewClient = client;
        super.setWebViewClient(client);
    }

    public WebViewClient getWebViewClient() {
        return mWebViewClient;
    }
}
