package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.util.Base64;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.browser.custom.WebViewBrowserActivity;
import com.liskovsoft.browser.xwalk.XWalkBrowserActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        injectCSS("main.css");
        injectJS("common.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJS("xwalk.js");
        } else {
            injectJS("webview.js");
        }
    }

    private void injectJS(String fileName) {
        injectResource(fileName, jsInjectTemplate);
    }

    private void injectCSS(String fileName) {
        injectResource(fileName, cssInjectTemplate);
    }

    private void injectResource(String fileName, String template) {
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            mWebView.loadUrl(String.format(template, encoded));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////

    private void loadJS(String fileName) {
        loadResource(fileName, "text/javascript");
    }

    private void loadCSS(String fileName) {
        loadResource(fileName, "text/css");
    }

    private void loadResource(String fileName, String mimeType) {
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            String content = getStringFromInputStream(inputStream);

            mWebView.loadData(content, mimeType, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
