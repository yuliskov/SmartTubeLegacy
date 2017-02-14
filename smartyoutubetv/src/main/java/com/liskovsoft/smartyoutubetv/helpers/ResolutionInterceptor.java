package com.liskovsoft.smartyoutubetv.helpers;

import android.content.Context;
import android.webkit.WebResourceResponse;
import okhttp3.Response;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResolutionInterceptor extends RequestInterceptor {
    private final Context mContext;

    public ResolutionInterceptor(Context context) {
         mContext = context;
    }

    @Override
    public boolean test(String url) {
        if (url.contains("tv-player.js")) {
            return true;
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Response response = doOkHttpRequest(url);
        InputStream inputStream = response.body().byteStream();
        InputStream beginInputStream = Helpers.getAsset(mContext, "resolution-interceptor.js");
        List<InputStream> streams = Arrays.asList(beginInputStream, inputStream);
        InputStream resultStream = new SequenceInputStream(Collections.enumeration(streams));

        return createResponse(response.body().contentType(), resultStream);
    }
}
