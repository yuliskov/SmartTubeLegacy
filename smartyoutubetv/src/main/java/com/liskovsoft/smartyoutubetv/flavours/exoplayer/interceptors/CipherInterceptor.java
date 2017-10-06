package com.liskovsoft.smartyoutubetv.flavours.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.CipherUtils2;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.events.GetDecipherCodeDoneEvent;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.events.GetDecipherCodeEvent;
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

        runInOtherThread(url);

        return null;
    }

    private void runInOtherThread(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cacheResponse(url);
            }
        }).start();
    }

    private void cacheResponse(String url) {
        Response response = doOkHttpRequest(url);
        InputStream is = response.body().byteStream();
        mJSDecipherCode = CipherUtils2.extractDecipherCode(is);
    }

    @Subscribe
    public void getDecipherCode(GetDecipherCodeEvent event) {
        Browser.getBus().post(new GetDecipherCodeDoneEvent(mJSDecipherCode));
    }
}
