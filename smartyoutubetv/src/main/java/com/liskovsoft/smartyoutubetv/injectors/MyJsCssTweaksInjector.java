package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase;
import com.liskovsoft.smartyoutubetv.oldyoutubeinfoparser.events.SupportedVideoFormatsEvent;
import com.squareup.otto.Subscribe;

public class MyJsCssTweaksInjector extends ResourceInjectorBase {
    private final Context mContext;

    public MyJsCssTweaksInjector(Context context) {
        this(context, null);
    }

    public MyJsCssTweaksInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
        mContext = context;
    }

    public void inject() {
        injectCSSAssetOnce("main.css");
        injectJSAssetOnce("common.js");
        injectJSAssetOnce("quality-controls.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJSAssetOnce("xwalk.js");
        } else {
            injectJSAssetOnce("webview.js");
        }
        if (mContext instanceof SmartYouTubeTVExoBase) {
            injectJSAssetOnce("exoplayer.js");
            injectCSSAssetOnce("exoplayer.css");
        }
    }

    @Subscribe
    public void notifyAboutSupportedVideoFormats(SupportedVideoFormatsEvent event) {
        injectJSContent("notifyAboutSupportedVideoFormats(['hd', 'sd', 'lq'])");
    }
}
