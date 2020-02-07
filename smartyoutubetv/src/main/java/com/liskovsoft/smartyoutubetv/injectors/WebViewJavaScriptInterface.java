package com.liskovsoft.smartyoutubetv.injectors;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.webkit.JavascriptInterface;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.events.AssetFileInjectEvent;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.PostDecipheredSignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericBooleanResultEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors.GenericEventResourceInjector.GenericStringResultEventWithId;
import com.liskovsoft.smartyoutubetv.misc.CodecSelectorAddon;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.events.SwitchResolutionEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * JavaScript Interface. Web code can access methods in here <br/>
 * (as long as they have the {@link JavascriptInterface} annotation)<br/>
 * <br/>
 *
 * {@link JavascriptInterface} required after SDK version 17.
 */
public class WebViewJavaScriptInterface {
    private static final String TAG = WebViewJavaScriptInterface.class.getSimpleName();
    private Context mContext;
    private final Set<Tab> mTabs = new HashSet<>();
    private final JavaScriptMessageHandler mMessageHandler;

    public WebViewJavaScriptInterface(Context context) {
        this(context, null);
    }

    public WebViewJavaScriptInterface(Context context, Tab tab) {
        mContext = context;
        mMessageHandler = new JavaScriptMessageHandler(context);

        if (tab != null) {
            mTabs.add(tab);
        }
    }

    public void add(Tab tab) {
        if (tab != null)
            mTabs.add(tab);
    }
    
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void closeApp() {
        if (mContext instanceof Activity) {
            Helpers.postOnUiThread(() -> ((Activity) mContext).finish());
        }
    }
    
    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceName() {
        return Helpers.getDeviceName();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getAppVersion() {
        String mode = CommonApplication.getPreferences().getDefaultDisplayMode();
        String modeString = mode == null ? "" : " " + mode;
        return AppInfoHelpers.getAppVersion(mContext) + modeString;
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceHardware() {
        return Build.HARDWARE;
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getDeviceResolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return String.format("%sx%s", width, height);
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void switchResolution(String formatName) {
        Browser.getBus().post(new SwitchResolutionEvent(formatName));

        reloadTab();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void reloadTab() {
        Handler handler = new Handler(mContext.getMainLooper());
        handler.post(() -> {
            for (Tab tab : mTabs) {
                tab.reload();
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
        Log.i(TAG, "Just now received deciphered signatures from webview.");
        Browser.getBus().post(new PostDecipheredSignaturesEvent(signatures, id));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onGenericBooleanResult(boolean result, int id) {
        Log.i(TAG, "Received generic boolean result from webview.");
        Browser.getBus().post(new GenericBooleanResultEvent(result, id));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onGenericStringResult(String result) {
        Log.i(TAG, "Received generic string result from webview.");
        Browser.getBus().post(new GenericStringResultEvent(result));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void onGenericStringResultWithId(String result, int id) {
        Log.i(TAG, "Received generic string result from webview.");
        Browser.getBus().post(new GenericStringResultEventWithId(result, id));
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void showExitMsg() {
        if (mContext instanceof Activity) {
            MessageHelpers.showMessageThrottled(mContext, mContext.getResources().getString(R.string.exit_msg));
        }
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public int getApiLevel() {
        return VERSION.SDK_INT;
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getEngineType() {
        return Browser.getEngineType().name();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public boolean isExo() {
        return mContext instanceof TwoFragmentsManagerActivity;
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void openCodecSelector() {
        new CodecSelectorAddon(mContext, this).run();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public String getPreferredCodec() {
        return new CodecSelectorAddon(mContext, this).getPreferredCodec();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public void sendMessage(String message, String content) {
        mMessageHandler.handleMessage(message, content);
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public boolean isMicAvailable() {
        return Helpers.isMicAvailable(mContext);
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public boolean isGlobalAfrFixEnabled() {
        SmartPreferences prefs = SmartPreferences.instance(mContext);
        return SmartPreferences.GLOBAL_AFR_FIX_STATE_ENABLED.equals(prefs.getGlobalAfrFixState());
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public int getPlaybackWorking() {
        SmartPreferences prefs = SmartPreferences.instance(mContext);
        return prefs.getPlaybackWorking();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public boolean logToFileEnabled() {
        return Log.getLogType().equals(Log.LOG_TYPE_FILE);
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public boolean isMirrorEnabled() {
        return CommonApplication.getPreferences().isMirrorEnabled();
    }

    @JavascriptInterface
    @org.xwalk.core.JavascriptInterface
    public boolean isBrowserInBackground() {
        return CommonApplication.getPreferences().isBrowserInBackground();
    }
}