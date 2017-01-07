package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class XWalkWebView extends WebView {
    public XWalkWebView(Context context) {
        super(context);
    }

    public XWalkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XWalkWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
