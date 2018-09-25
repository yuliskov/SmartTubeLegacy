package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events.SupportedVideoFormatsEvent;
import com.liskovsoft.smartyoutubetv.webaddons.AddonManager;
import com.liskovsoft.smartyoutubetv.webaddons.WebAddon;
import com.squareup.otto.Subscribe;

import java.util.List;

public class MyJsCssTweaksInjector extends ResourceInjectorBase {
    private static final String TAG = MyJsCssTweaksInjector.class.getSimpleName();
    private final Context mContext;
    private final WebAddon mRootAddon;

    public MyJsCssTweaksInjector(Context context) {
        this(context, null);
    }

    public MyJsCssTweaksInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
        mRootAddon = new AddonManager(context);
    }

    public void inject() {
        Log.d(TAG, "Injecting js/css tweaks...");

        injectCSSAssetOnce("common/common.css");
        injectJSAssetOnce("common/common.js");
        //if (Browser.getEngineType() == EngineType.XWalk) {
        //    injectJSAssetOnce("xwalk/xwalk.js");
        //} else {
        //    injectJSAssetOnce("webview/webview.js");
        //}
        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase) {
            injectJSAssetOnce("common/exoplayer.js");
            injectCSSAssetOnce("common/exoplayer.css");
        }
        //if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity) {
        //    injectCSSAssetOnce("common/loading-placeholder.css");
        //    injectJSAssetOnce("common/webview_720.js");
        //}

        injectAddons();
    }

    private void injectAddons() {
        List<String> cssAddons = mRootAddon.getCSSList();
        List<String> jsAddons = mRootAddon.getJSList();
        for (String addon : cssAddons) {
            injectCSSAssetOnce(addon);
        }
        for (String addon : jsAddons) {
            injectJSAssetOnce(addon);
        }
    }

    @Subscribe
    public void notifyAboutSupportedVideoFormats(SupportedVideoFormatsEvent event) {
        injectJSContent("notifyAboutSupportedVideoFormats(['hd', 'sd', 'lq'])");
    }
}
