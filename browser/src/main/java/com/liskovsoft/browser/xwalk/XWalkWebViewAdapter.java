package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.*;
import com.liskovsoft.browser.addons.HeadersBrowserWebView;
import com.liskovsoft.browser.addons.HeadersWebSettingsDecorator;
import com.liskovsoft.sharedutils.mylogger.Log;
import org.xwalk.core.XWalkView;

import java.util.HashMap;
import java.util.Map;

public class XWalkWebViewAdapter extends HeadersBrowserWebView {
    private static final String TAG = XWalkWebViewAdapter.class.getSimpleName();
    private final XWalkView mXWalkView;
    private final XWalkUIClientAdapter mXWalkUiClient;
    private XWalkResourceClientAdapter mResourceClient;

    public XWalkWebViewAdapter(Context context) {
        this(null, context);
    }

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
        super.pauseTimers();
        super.onPause();

        // maybe this fixes crashes on mitv2?
        //XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, true);

        mXWalkView = new XWalkView(context, attrs);
        mResourceClient = new XWalkResourceClientAdapter(this, mXWalkView);
        mXWalkView.setResourceClient(mResourceClient);
        mXWalkUiClient = new XWalkUIClientAdapter(mXWalkView);
        mXWalkView.setUIClient(mXWalkUiClient);
    }

    @Override
    public void reload() {
        mXWalkView.reload(XWalkView.RELOAD_NORMAL);
    }

    @Override
    public String getUrl() {
        return mXWalkView.getUrl();
    }

    @Override
    public String getOriginalUrl() {
        return mXWalkView.getOriginalUrl();
    }

    @Override
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        mXWalkView.evaluateJavascript(script, resultCallback);
    }

    @Override
    public void setInitialScale(int scaleInPercent) {
        mXWalkView.setInitialScale(scaleInPercent);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        mXWalkView.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadUrl(String url) {
        mXWalkView.loadUrl(url);
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
        if (mXWalkView == null) {
            return null;
        }

        return new HeadersWebSettingsDecorator(mHeaders, new XWalkWebSettingsAdapter(mXWalkView.getSettings()));
    }

    public XWalkView getXWalkView() {
        return mXWalkView;
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        mResourceClient.setWebViewClient(client);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        mResourceClient.setWebChromeClient(client);
        mXWalkUiClient.setWebChromeClient(client);
    }

    @Override
    public WebViewClient getWebViewClient() {
        return mResourceClient.getWebViewClient();
    }

    @Override
    public void addJavascriptInterface(Object object, String name) {
        mXWalkView.addJavascriptInterface(object, name);
    }


    @Override
    public WebBackForwardList saveState(Bundle outState) {
        boolean success = mXWalkView.saveState(outState);

        return getWebBackForwardListAdapter(success);
    }

    @Override
    public WebBackForwardList restoreState(Bundle inState) {
        boolean success = mXWalkView.restoreState(inState);

        return getWebBackForwardListAdapter(success);
    }

    private WebBackForwardList getWebBackForwardListAdapter(boolean success) {
        // WebBackForwardList doesn't have a constructor on api < 17
        if (VERSION.SDK_INT < 17) {
            return super.copyBackForwardList();
        }

        return new WebBackForwardListAdapter(success);
    }

    @Override
    public void destroy() {
        mXWalkView.onDestroy();
        super.destroy();
    }

    @Override
    public void clearCache(boolean includeDiskFiles) {
        mXWalkView.clearCache(includeDiskFiles);
        super.clearCache(includeDiskFiles);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.d(TAG, "XWalkView: Soft keyboard has appeared on the screen");
        return mXWalkView.onCreateInputConnection(outAttrs);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "XWalkView: Dispatching key " + event);
        return mXWalkView.dispatchKeyEvent(event);
    }
}
