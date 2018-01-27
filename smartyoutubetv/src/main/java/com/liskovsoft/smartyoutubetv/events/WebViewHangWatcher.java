package com.liskovsoft.smartyoutubetv.events;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.Tab;

public class WebViewHangWatcher implements Controller.EventListener {
    private final Context mContext;
    private final int mRetryTimes;

    public WebViewHangWatcher(Context context, int retryTimes) {
        mContext = context;
        mRetryTimes = retryTimes;
    }

    @Override
    public void onReceiveError(Tab tab) {
        
    }

    @Override
    public void onLoadSuccess(Tab tab) {

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

    }

    @Override
    public void onPageStarted(Tab tab) {

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
