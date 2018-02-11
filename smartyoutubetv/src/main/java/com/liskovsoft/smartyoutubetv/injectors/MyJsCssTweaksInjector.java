package com.liskovsoft.smartyoutubetv.injectors;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
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
        injectCSSAssetOnce("common.css");
        injectJSAssetOnce("common.js");
        injectJSAssetOnce("quality-controls.js");
        if (Browser.getEngineType() == EngineType.XWalk) {
            injectJSAssetOnce("xwalk.js");
        } else {
            injectJSAssetOnce("webview.js");
        }
        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase) {
            injectJSAssetOnce("exoplayer.js");
            injectCSSAssetOnce("exoplayer.css");
        }
        if (mContext instanceof com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity) {
            injectJSAssetOnce("webview_720.js");
        }
    }

    @Subscribe
    public void notifyAboutSupportedVideoFormats(SupportedVideoFormatsEvent event) {
        injectJSContent("notifyAboutSupportedVideoFormats(['hd', 'sd', 'lq'])");
    }
}
