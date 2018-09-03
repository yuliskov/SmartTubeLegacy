package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.injectors;

import android.content.Context;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.injectors.ResourceInjectorBase;
import com.squareup.otto.Subscribe;

public class GenericEventResourceInjector extends ResourceInjectorBase {
    // TODO: fixme: remove delay (button uninitialized)
    private final String mStringResultCommand = "setTimeout(function(){app.onGenericStringResultWithId(%s, %s);}, 2000);";

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

    public static class GenericStringResultEvent {
        private String mResult;

        public GenericStringResultEvent(String result) {
            mResult = result;
        }

        public String getResult() {
            return mResult;
        }
    }

    public static class GenericStringResultEventWithId {
        private String mResult;
        private final int mId;

        public GenericStringResultEventWithId(String result, int id) {
            mResult = result;
            mId = id;
        }

        public String getResult() {
            return mResult;
        }

        public int getId() {
            return mId;
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

    public static class JSStringResultEvent {
        private final String mCode;
        private final int mId;

        public JSStringResultEvent(String code, int id) {
            mCode = code;
            mId = id;
        }

        public String getCode() {
            return mCode;
        }

        public int getId() {
            return mId;
        }
    }

    public GenericEventResourceInjector(Context context) {
        this(context, null);
    }

    public GenericEventResourceInjector(Context context, WebView webView) {
        super(context, webView);
        Browser.getBus().register(this);
    }

    @Subscribe
    public void processResource(JSStringResultEvent event) {
        String jscode = event.getCode();
        int id = event.getId();
        injectJSContentUnicode(String.format(mStringResultCommand, jscode, id));
    }

    @Subscribe
    public void processResource(JSResourceEvent event) {
        String jscode = event.getCode();
        injectJSContentUnicode(jscode);
    }
}
