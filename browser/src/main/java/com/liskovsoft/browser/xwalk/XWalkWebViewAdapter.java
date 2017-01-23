package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.other.HeadersBrowserWebView;
import com.liskovsoft.browser.other.HeadersWebSettingsDecorator;
import org.xwalk.core.XWalkView;

import java.util.HashMap;
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

        // we don't need this WebView because it serves as simple wrapper
        super.onPause();
        super.pauseTimers();

        // maybe this fixes crashes on mitv2?
        //XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, true);

        mXWalkView = new XWalkView(context, attrs);

        mXWalkView.setZOrderOnTop(true); // fix blank screen (no video)
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
    public void loadData(String data, String mimeType, String encoding) {
        // Content-Type: text/html; charset=utf-8
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", String.format("%s; %s", mimeType, encoding));
        mXWalkView.load(null, data, headers);
    }

    @Override
    public void pauseTimers() {
        mXWalkView.pauseTimers();
    }

    @Override
    public void resumeTimers() {
        mXWalkView.resumeTimers();
    }

    @Override
    public void onPause() {
        mXWalkView.onHide();
    }

    @Override
    public void onResume() {
        mXWalkView.onShow();
    }

    @Override
    public void setLayerType(int layerType, Paint paint) {
        mXWalkView.setLayerType(layerType, paint);
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
