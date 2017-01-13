package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.util.HeadersBrowserWebView;
import com.liskovsoft.browser.util.HeadersWebSettingsDecorator;
import org.xwalk.core.XWalkView;

import java.util.Map;

public class XWalkWebViewAdapter extends HeadersBrowserWebView {
    private final XWalkView mXWalkView;
    private XWalkResourceClientAdapter mResourceClient;

    public XWalkWebViewAdapter(Map<String, String> headers, Context context) {
        this(headers, context, null);
    }

    public XWalkWebViewAdapter(Map<String, String> headers, Context context, AttributeSet attrs) {
        this(headers, context, null, 0);
    }

    public XWalkWebViewAdapter(Map<String, String> headers, Context context, AttributeSet attrs, int defStyleAttr) {
        this(headers, context, attrs, defStyleAttr, false);
    }

    public XWalkWebViewAdapter(Map<String, String> headers, Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(headers, context, attrs, defStyle, privateBrowsing);

        mXWalkView = new XWalkView(context, attrs);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        mXWalkView.load(url, null, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        mXWalkView.load(url, null);
    }

    @Override
    public WebSettings getSettings() {
        return new HeadersWebSettingsDecorator(mHeaders, new XWalkWebSettingsAdapter(mXWalkView.getSettings()));
    }

    public XWalkView getXWalkView() {
        return mXWalkView;
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        mResourceClient = new XWalkResourceClientAdapter(client, this, mXWalkView);
        mXWalkView.setResourceClient(mResourceClient);
    }

    @Override
    public WebViewClient getWebViewClient() {
        return mResourceClient.getWebViewClient();
    }


    @Override
    public WebBackForwardList saveState(Bundle outState) {
        boolean success = mXWalkView.saveState(outState);
        return new WebBackForwardListAdapter(success);
    }

    @Override
    public WebBackForwardList restoreState(Bundle inState) {
        boolean success = mXWalkView.restoreState(inState);
        return new WebBackForwardListAdapter(success);
    }
}
