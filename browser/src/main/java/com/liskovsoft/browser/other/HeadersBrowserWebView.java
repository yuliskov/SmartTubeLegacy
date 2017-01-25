package com.liskovsoft.browser.other;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import com.liskovsoft.browser.BrowserWebView;

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

        // Fix for Android 4.0.3, 4.0.4: WebSettings doesn't have default constructor
        int sdkInt = VERSION.SDK_INT;
        if (sdkInt <= 15) {
            mSettings = super.getSettings();
            return mSettings;
        }

        mSettings = new HeadersWebSettingsDecorator(mHeaders, super.getSettings());
        return mSettings;
    }
}
