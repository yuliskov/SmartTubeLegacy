package com.liskovsoft.browser.xwalk;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import com.liskovsoft.browser.BrowserWebViewFactory;

public class XWalkWebViewFactory extends BrowserWebViewFactory {
    public XWalkWebViewFactory(Context browser) {
        super(browser);
    }

    @Override
    protected WebView instantiateWebView(AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        WebView w = new XWalkWebViewAdapter(mNextHeaders, mContext, attrs, defStyle, privateBrowsing);
        return w;
    }
}
