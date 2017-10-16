package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff.injectors;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.injectors.ResourceInjectorBase;
import com.squareup.otto.Subscribe;

public class GenericEventResourceInjector extends ResourceInjectorBase {
    public static class GenericBooleanResultEvent {
        private int mId;
        private boolean mResult;

        public GenericBooleanResultEvent(boolean result, int id) {
            mId = id;
            mResult = result;
        }

        public int getId() {
            return mId;
        }

        public boolean getResult() {
            return mResult;
        }
    }

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
