package com.liskovsoft.browser.addons;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebSettings;
import com.liskovsoft.browser.BrowserWebView;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.Map;

public class HeadersBrowserWebView extends BrowserWebView {
    private static final String TAG = HeadersBrowserWebView.class.getSimpleName();
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

        // don't wrap WebSettings on old api (below 16)
        // because WebSettings doesn't have default constructor
        int sdkInt = VERSION.SDK_INT;
        if (sdkInt < 16) {
            mSettings = super.getSettings();
            return mSettings;
        }

        mSettings = new HeadersWebSettingsDecorator(mHeaders, super.getSettings());
        return mSettings;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.d(TAG, "WebView: Soft keyboard has appeared on the screen");
        return super.onCreateInputConnection(outAttrs);
    }
}
