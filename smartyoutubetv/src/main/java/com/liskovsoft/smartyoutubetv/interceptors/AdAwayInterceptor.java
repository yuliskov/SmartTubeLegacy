package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.misc.AdAwayClient;

public class AdAwayInterceptor extends RequestInterceptor {
    private Context mContext;

    public AdAwayInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        return AdAwayClient.isAd(url);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        // block url
        return new WebResourceResponse(null, null, null);
    }
}
