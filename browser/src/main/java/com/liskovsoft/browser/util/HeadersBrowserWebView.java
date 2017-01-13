package com.liskovsoft.browser.util;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import com.liskovsoft.browser.BrowserWebView;
import com.liskovsoft.browser.BrowserWebViewFactory;
import com.liskovsoft.browser.WebViewFactory;

import java.util.Map;

public class HeadersBrowserWebView extends BrowserWebView {
    protected Map<String, String> mHeaders;
    private WebSettings mSettings;

    public HeadersBrowserWebView(Context context) {
        super(context);
        mHeaders = null;
    }

    public HeadersBrowserWebView(Map<String, String> headers, Context context) {
        super(context);
        mHeaders = headers;
    }

    public HeadersBrowserWebView(Map<String, String> headers, Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeaders = headers;
    }

    public HeadersBrowserWebView(Map<String, String> headers, Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaders = headers;
    }

    public HeadersBrowserWebView(Map<String, String> headers, Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
        mHeaders = headers;
    }

    @Override
    public WebSettings getSettings() {
        if (mSettings != null)
            return mSettings;

        mSettings = new HeadersWebSettingsDecorator(mHeaders, super.getSettings());
        return mSettings;
    }
}
