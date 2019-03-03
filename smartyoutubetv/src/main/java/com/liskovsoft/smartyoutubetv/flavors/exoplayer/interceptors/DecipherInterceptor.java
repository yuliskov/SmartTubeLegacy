package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.CipherUtils;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.GetDecipherCodeDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.GetDecipherCodeEvent;
import com.squareup.otto.Subscribe;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import okhttp3.Response;

import java.io.InputStream;

/**
 * intercepts "tv-player.js"
 */
public class DecipherInterceptor extends RequestInterceptor {
    private String mJSDecipherCode;

    public DecipherInterceptor(Context context) {
        Browser.getBus().register(this);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (mJSDecipherCode != null) { // remove once
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
        Response response = OkHttpHelpers.doOkHttpRequest(url);
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
