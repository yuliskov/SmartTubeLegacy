package com.liskovsoft.smartyoutubetv.injectors;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;

/*
 * JavaScript Interface. Web code can access methods in here
 * (as long as they have the @JavascriptInterface annotation)
 */
public class WebViewJavaScriptInterface {

    private Context mContext;

    /*
     * Need a reference to the context in order to sent a post message
     */
    public WebViewJavaScriptInterface(Context context) {
        mContext = context;
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
}