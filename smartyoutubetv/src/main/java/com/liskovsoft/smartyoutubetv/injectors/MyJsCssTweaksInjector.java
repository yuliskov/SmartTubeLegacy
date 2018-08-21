package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.misc.webaddonsmanager.WebAddonsManager;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events.SupportedVideoFormatsEvent;
import com.squareup.otto.Subscribe;

import java.util.List;

public class MyJsCssTweaksInjector extends ResourceInjectorBase {
    private final Context mContext;
    private final WebAddonsManager mManager;

    public MyJsCssTweaksInjector(Context context) {
        this(context, null);
    }

    public MyJsCssTweaksInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
        mManager = new WebAddonsManager(context);
    }

    public void inject() {
        injectCSSAssetOnce("common/common.css");
        injectJSAssetOnce("common/common.js");
        injectJSAssetOnce("common/quality-controls.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJSAssetOnce("xwalk/xwalk.js");
        } else {
            injectJSAssetOnce("webview/webview.js");
        }
        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase) {
            injectJSAssetOnce("exoplayer/exoplayer.js");
            injectCSSAssetOnce("exoplayer/exoplayer.css");
        }
        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity) {
            injectCSSAssetOnce("common/loading-placeholder.css");
            injectJSAssetOnce("common/webview_720.js");
        }

        injectAddons();
    }

    private void injectAddons() {
        List<String> cssAddons = mManager.getCSSAddons();
        List<String> jsAddons = mManager.getJSAddons();
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
