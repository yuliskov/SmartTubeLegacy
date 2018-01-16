package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.CipherUtils;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events.GetDecipherCodeDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events.GetDecipherCodeEvent;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;

// intercepts "tv-player.js"
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
        Response response = Helpers.doOkHttpRequest(url);
        if (response == null) {
            return;
        }

        InputStream is = response.body().byteStream();
        mJSDecipherCode = CipherUtils.extractDecipherCode(is);
    }

    @Subscribe
    public void getDecipherCode(GetDecipherCodeEvent event) {
        Browser.getBus().post(new GetDecipherCodeDoneEvent(mJSDecipherCode));
    }
}
