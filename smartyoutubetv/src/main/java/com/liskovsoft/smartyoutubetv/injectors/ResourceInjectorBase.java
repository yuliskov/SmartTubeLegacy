package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.CSSFileInjectEvent;
import com.liskovsoft.smartyoutubetv.events.JSFileInjectEvent;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.squareup.otto.Subscribe;

import java.io.*;

public abstract class ResourceInjectorBase {
    private final Context mContext;
    private final WebView mWebView;
    private static final String cssInjectTemplate = "javascript:(function() {" +
            "var parent = document.getElementsByTagName('head').item(0);" +
            "var element = document.createElement('style');" +
            "element.type = 'text/css';" +
            // Tell the browser to BASE64-decode the string into your script !!!
            "element.innerHTML = decodeURIComponent(window.atob('%s'));" +
            "parent.appendChild(element)" +
            "})()";
    private static final String jsInjectTemplate = "javascript:(function() {" +
            "var parent = document.getElementsByTagName(\'head\').item(0);" +
            "var element = document.createElement(\'script\');" +
            "element.type = \'text/javascript\';" +
            // Tell the browser to BASE64-decode the string into your script !!!
            "element.innerHTML = decodeURIComponent(window.atob('%s'));" +
            "parent.appendChild(element)" +
            "})()";

    private static final String testJSFnTemplate = "function testInject(fileName){if (window[fileName]) return; window[fileName] = true; app.onJSFileInject(fileName)}; testInject('%s');";
    private static final String testCSSFnTemplate = "function testInject(fileName){if (window[fileName]) return; window[fileName] = true; app.onCSSFileInject(fileName)}; testInject('%s');";

    private final EventHandler mHandler;

    private class EventHandler {
        private final ResourceInjectorBase mInjector;

        public EventHandler(ResourceInjectorBase injector) {
            Browser.getBus().register(this);
            mInjector = injector;
        }

        @Subscribe
        public void onJSFileInjectEvent(JSFileInjectEvent event) {
            final String fileName = event.getFileName();
            mInjector.injectJSAsset(fileName);
        }

        @Subscribe
        public void onCSSFileInjectEvent(CSSFileInjectEvent event) {
            final String fileName = event.getFileName();
            Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mInjector.injectCSSAsset(fileName);
                }
            });
        }
    }

    public ResourceInjectorBase(Context context, WebView webView) {
        mContext = context;
        mWebView = webView;
        mHandler = new EventHandler(this);
    }

    /**
     * Safe mean injected only one time per page.
     * @param fileName
     */
    protected void injectJSAssetOnce(String fileName) {
        injectContent(jsInjectTemplate, String.format(testJSFnTemplate, fileName).getBytes());
    }

    /**
     * Safe mean injected only one time per page.
     * @param fileName
     */
    protected void injectCSSAssetOnce(String fileName) {
        injectContent(jsInjectTemplate, String.format(testCSSFnTemplate, fileName).getBytes());
    }

    protected void injectJSAsset(String fileName) {
        injectResource(fileName, jsInjectTemplate);
    }

    protected void injectCSSAsset(String fileName) {
        injectResource(fileName, cssInjectTemplate);
    }

    /**
     * Injects JS as is
     * @param content
     */
    protected void injectJSContent(final String content) {
        loadUrlSafe(String.format(jsInjectTemplate, content));
    }

    /**
     * Injects JS and encodes non-english letters
     * @param content
     */
    protected void injectJSContentUnicode(final String content) {
        injectContent(jsInjectTemplate, content.getBytes());
    }

    private void injectResource(String fileName, String template) {
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            injectContent(template, buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void injectContent(String template, byte[] data) {
        String uriEncoded = Helpers.encodeURI(data);// preserve non-english letters
        String encoded = Base64.encodeToString(uriEncoded.getBytes(), Base64.NO_WRAP);
        String content = String.format(template, encoded);
        loadUrlSafe(content);
    }

    private void loadUrlSafe(final String content) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            // Current Thread is Main Thread.
            mWebView.loadUrl(content);
            return;
        }

        Handler mainHandler = new Handler(mContext.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(content);
            }
        });
    }
}
