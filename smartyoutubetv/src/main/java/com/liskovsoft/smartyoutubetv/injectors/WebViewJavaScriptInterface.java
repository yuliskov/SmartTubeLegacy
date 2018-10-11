package com.liskovsoft.smartyoutubetv.injectors;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.events.AssetFileInjectEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.PostDecipheredSignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericBooleanResultEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEventWithId;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events.SwitchResolutionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/*
 * JavaScript Interface. Web code can access methods in here
 * (as long as they have the @JavascriptInterface annotation)
 */
public class WebViewJavaScriptInterface {
    private Context mContext;
    private final Set<Tab> mTabs = new HashSet<>();
    private static final Logger sLogger = LoggerFactory.getLogger(WebViewJavaScriptInterface.class);
    private static final String TAG = WebViewJavaScriptInterface.class.getSimpleName();

    public WebViewJavaScriptInterface(Context context) {
        this(context, null);
    }

    /*
     * Need a reference to the context in order to sent a post message
     */
    public WebViewJavaScriptInterface(Context context, Tab tab) {
        mContext = context;
        if (tab != null)
            mTabs.add(tab);
    }

    public void add(Tab tab) {
        if (tab != null)
            mTabs.add(tab);
    }

    // TODO: not called in Android 8.0 (api 26)
    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void closeApp() {
        if (mContext instanceof Activity) {
            Helpers.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((Activity) mContext).finish();
                }
            });
        }
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceName() {
        return Helpers.getDeviceName();
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceHardware() {
        return Build.HARDWARE;
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceResolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return String.format("%sx%s", width, height);
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
                for (Tab tab : mTabs) {
                    tab.reload();
                }
            }
        });
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onAssetFileInject(String fileName, String listenerHash) {
        if (fileName != null) {
            Log.d(TAG, "Posting event: " + fileName);
            Browser.getBus().post(new AssetFileInjectEvent(fileName, listenerHash));
        }
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void postDecipheredSignatures(String[] signatures, int id) {
        sLogger.info("Just now received deciphered signatures from webview.");
        Browser.getBus().post(new PostDecipheredSignaturesEvent(signatures, id));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onGenericBooleanResult(boolean result, int id) {
        sLogger.info("Received generic boolean result from webview.");
        Browser.getBus().post(new GenericBooleanResultEvent(result, id));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onGenericStringResult(String result) {
        sLogger.info("Received generic string result from webview.");
        Browser.getBus().post(new GenericStringResultEvent(result));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onGenericStringResultWithId(String result, int id) {
        sLogger.info("Received generic string result from webview.");
        Browser.getBus().post(new GenericStringResultEventWithId(result, id));
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void showExitMsg() {
        if (mContext instanceof Activity) {
            MessageHelpers.showMessageThrottled(mContext, mContext.getResources().getString(R.string.exit_msg));
        }
    }

    /*
     * This method can be called from Android. @JavascriptInterface
     * required after SDK version 17.
     */
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getEngineType() {
        return Browser.getEngineType().name();
    }
}