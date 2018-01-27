package com.liskovsoft.smartyoutubetv.events;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.Tab;

public class WebViewHangWatcher implements Controller.EventListener {
    private static final String TAG = WebViewHangWatcher.class.getSimpleName();
    private final Context mContext;
    private final int mRetryTimes;

    public WebViewHangWatcher(Context context, int retryTimes) {
        mContext = context;
        mRetryTimes = retryTimes;
    }

    @Override
    public void onReceiveError(Tab tab) {
        Log.i(TAG, "page error: " + tab.getWebView().getUrl());
    }

    @Override
    public void onLoadSuccess(Tab tab) {
        Log.i(TAG, "load success: " + tab.getWebView().getUrl());
    }

    @Override
    public WebViewClient onSetWebViewClient(Tab tab, WebViewClient client) {
        return null;
    }

    @Override
    public WebChromeClient onSetWebChromeClient(Tab tab, WebChromeClient client) {
        return null;
    }

    @Override
    public void onPageFinished(Tab tab) {
        Log.i(TAG, "page finished: " + tab.getWebView().getUrl());
    }

    @Override
    public void onPageStarted(Tab tab) {
        Log.i(TAG, "page started: " + tab.getWebView().getUrl());
    }

    @Override
    public void onControllerStart() {

    }

    @Override
    public void onSaveControllerState(Bundle state) {

    }

    @Override
    public void onRestoreControllerState(Bundle state) {

    }

    @Override
    public void onTabCreated(Tab tab) {

    }
}
