package com.liskovsoft.smartyoutubetv.injectors;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.smartyoutubetv.events.CSSFileInjectEvent;
import com.liskovsoft.smartyoutubetv.events.JSFileInjectEvent;
import com.liskovsoft.smartyoutubetv.events.SwitchResolutionEvent;

/*
 * JavaScript Interface. Web code can access methods in here
 * (as long as they have the @JavascriptInterface annotation)
 */
public class WebViewJavaScriptInterface {
    private final Tab mTab;
    private Context mContext;

    /*
     * Need a reference to the context in order to sent a post message
     */
    public WebViewJavaScriptInterface(Context context, Tab tab) {
        mContext = context;
        mTab = tab;
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void closeApp() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).finish();
        }
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceName() {
        return Build.MODEL;
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void switchResolution(String formatName) {
        Browser.getBus().post(new SwitchResolutionEvent(formatName));

        Handler handler = new Handler(mContext.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                mTab.reload();
            }
        });
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onJSFileInject(String fileName) {
        Browser.getBus().post(new JSFileInjectEvent(fileName));

    }
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onCSSFileInject(String fileName) {
        Browser.getBus().post(new CSSFileInjectEvent(fileName));
    }
}