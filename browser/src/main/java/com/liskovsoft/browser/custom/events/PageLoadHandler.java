package com.liskovsoft.browser.custom.events;

import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.liskovsoft.browser.Tab;

public interface PageLoadHandler {
    void onPageFinished(Tab tab);
    void onPageStarted(Tab tab);
    WebViewClient overrideWebViewClient(WebViewClient client);
    WebChromeClient overrideWebChromeClient(WebChromeClient client);
}
