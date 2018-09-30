package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events.SupportedVideoFormatsEvent;
import com.liskovsoft.smartyoutubetv.webscripts.old.AddonManager;
import com.liskovsoft.smartyoutubetv.webscripts.old.WebAddon;
import com.squareup.otto.Subscribe;

import java.util.List;

// TODO: delete
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
        
        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase) {
            injectJSAssetOnce("core/exoplayer.js");
            injectCSSAssetOnce("core/exoplayer.css");
        }

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
