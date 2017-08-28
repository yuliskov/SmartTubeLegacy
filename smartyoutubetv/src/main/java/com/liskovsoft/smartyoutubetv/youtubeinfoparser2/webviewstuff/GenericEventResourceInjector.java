package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.injectors.ResourceInjectorBase;
import com.squareup.otto.Subscribe;

public class GenericEventResourceInjector extends ResourceInjectorBase {
    public static class JSResourceEvent {
        private String mCode;

        public JSResourceEvent(String code) {
            mCode = code;
        }

        public String getCode() {
            return mCode;
        }
    }

    public GenericEventResourceInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
    }

    @Subscribe
    public void processResource(JSResourceEvent evnt) {
        String jscode = evnt.getCode();
        injectJSContentUnicode(jscode);
    }
}
