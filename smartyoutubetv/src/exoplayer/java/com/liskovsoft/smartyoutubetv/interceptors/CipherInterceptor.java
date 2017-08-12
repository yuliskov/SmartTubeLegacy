package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.CipherUtils2;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;

public class CipherInterceptor extends RequestInterceptor {
    private String mJSDecipherCode;

    public CipherInterceptor(Context context) {
        Browser.getBus().register(this);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (mJSDecipherCode != null) { // run once
            return null;
        }

        Response response = doOkHttpRequest(url);
        InputStream is = response.body().byteStream();
        mJSDecipherCode = CipherUtils2.extractDecipherCode(is);

        return null;
    }

    @Subscribe
    public void getDecipherCode(GetDecipherCodeEvent event) {
        Browser.getBus().post(new GetDecipherCodeDoneEvent(mJSDecipherCode));
    }
}
