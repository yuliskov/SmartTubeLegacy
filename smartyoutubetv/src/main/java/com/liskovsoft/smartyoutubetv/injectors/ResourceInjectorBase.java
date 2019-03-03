package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.webkit.WebView;
import com.liskovsoft.sharedutils.helpers.Helpers;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public abstract class ResourceInjectorBase implements ResourceInjectorWatcher.Listener {
    private static final String TAG = ResourceInjectorBase.class.getSimpleName();
    private final Context mContext;
    private final Set<WebView> mWebViews = new HashSet<>();
    private static final String genericElementInjectTemplate = "javascript:(function() {" +
            "console.log(\'ResourceInjectorBase.java::genericElementInjectTemplate\');" +
            "var parent = document.getElementsByTagName(\'head\').item(0);" +
            "var element = document.createElement(\'%TAG_NAME%\');" +
            "element.type = \'%MIME_TYPE%\';" +
            // Tell the browser to BASE64-decode the string into your script !!!
            "element.innerHTML = decodeURIComponent(window.atob(\'%s\'));" +
            "parent.appendChild(element);" +
            "})();";

    private static final String testAssetTemplate = "javascript:(function (fileName) {" +
            "if (window[fileName]) return;" +
            "window[fileName] = true;" +
            "app.onAssetFileInject(fileName, \'%LISTENER_HASH%\');" +
            "})(\'%s\');";

    private String testAssetTemplateCache;
    private String jsInjectTemplateCache;
    private String cssInjectTemplateCache;
    private ResourceInjectorWatcher mWatcher;

    public ResourceInjectorBase(Context context) {
        this(context, null);
    }

    public ResourceInjectorBase(Context context, WebView webView) {
        mContext = context;
        if (webView != null)
            mWebViews.add(webView);

        mWatcher = ResourceInjectorWatcher.instance();
        mWatcher.addListener(this);
    }

    public void add(WebView webView) {
        if (webView != null)
            mWebViews.add(webView);
    }

    @Override
    public void onAssetFileInjectEvent(String fileName) {
        if (fileName.endsWith(".js")) {
            injectJSAsset(fileName);
        } else if (fileName.endsWith(".css")) {
            injectCSSAsset(fileName);
        }
    }

    /**
     * Safe mean injected only one time per page.
     * @param fileName asset name
     */
    protected void injectJSAssetOnce(String fileName) {
        injectContent(obtainJSInjectTemplate(), String.format(obtainTestAssetTemplate(), fileName).getBytes());
    }

    /**
     * Safe mean injected only one time per page.
     * @param fileName asset name
     */
    protected void injectCSSAssetOnce(String fileName) {
        // yes, injecting js instead of css, because there is a test js function
        injectContent(obtainJSInjectTemplate(), String.format(obtainTestAssetTemplate(), fileName).getBytes());
    }

    protected void injectJSAsset(String fileName) {
        String resTemplate = obtainJSInjectTemplate();
        injectResource(fileName, resTemplate);
    }

    protected void injectCSSAsset(String fileName) {
        String resTemplate = obtainCSSInjectTemplate();
        injectResource(fileName, resTemplate);
    }

    private String obtainTestAssetTemplate() {
        if (testAssetTemplateCache != null) {
            return testAssetTemplateCache;
        }

        testAssetTemplateCache = testAssetTemplate.replace("%LISTENER_HASH%", String.valueOf(hashCode()));
        return testAssetTemplateCache;
    }

    private String obtainJSInjectTemplate() {
        if (jsInjectTemplateCache != null) {
            return jsInjectTemplateCache;
        }

        jsInjectTemplateCache = genericElementInjectTemplate
                .replace("%TAG_NAME%", "script")
                .replace("%MIME_TYPE%", "text/javascript");
        return jsInjectTemplateCache;
    }

    private String obtainCSSInjectTemplate() {
        if (cssInjectTemplateCache != null) {
            return cssInjectTemplateCache;
        }

        cssInjectTemplateCache = genericElementInjectTemplate
                .replace("%TAG_NAME%", "style")
                .replace("%MIME_TYPE%", "text/css");
        return cssInjectTemplateCache;
    }

    /**
     * Injects JS as is
     * @param content
     */
    protected void injectJSContent(final String content) {
        loadUrlSafe(String.format(obtainJSInjectTemplate(), content));
    }

    /**
     * Injects JS and encodes non-english letters
     * @param content
     */
    protected void injectJSContentUnicode(final String content) {
        injectContent(obtainJSInjectTemplate(), content.getBytes());
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
            for (WebView webView : mWebViews) {
                webView.loadUrl(content);
            }
            return;
        }

        Handler mainHandler = new Handler(mContext.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (WebView webView : mWebViews) {
                    webView.loadUrl(content);
                }
            }
        });
    }
}
