package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.util.Base64;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

public class ResourceInjector {
    private final Context mContext;
    private final WebView mWebView;
    private static final String cssInjectTemplate = "javascript:(function() {" +
            "var parent = document.getElementsByTagName('head').item(0);" +
            "var element = document.createElement('style');" +
            "element.type = 'text/css';" +
            // Tell the browser to BASE64-decode the string into your script !!!
            "element.innerHTML = window.atob('%s');" +
            "parent.appendChild(element)" +
            "})()";
    private static final String jsInjectTemplate = "javascript:(function() {" +
            "var parent = document.getElementsByTagName(\'head\').item(0);" +
            "var element = document.createElement(\'script\');" +
            "element.type = \'text/javascript\';" +
            // Tell the browser to BASE64-decode the string into your script !!!
            "element.innerHTML = window.atob('%s');" +
            "parent.appendChild(element)" +
            "})()";

    public ResourceInjector(Context context, WebView webView) {
        this.mContext = context;
        this.mWebView = webView;
    }

    public void inject() {
        injectCSS(mWebView, "main.css");
        injectJS(mWebView, "main.js");
    }

    private void injectJS(WebView webView, String fileName) {
        injectResource(webView, fileName, jsInjectTemplate);
    }

    private void injectCSS(WebView webView, String fileName) {
        injectResource(webView, fileName, cssInjectTemplate);
    }

    private void injectResource(WebView webView, String fileName, String template) {
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl(String.format(template, encoded));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
