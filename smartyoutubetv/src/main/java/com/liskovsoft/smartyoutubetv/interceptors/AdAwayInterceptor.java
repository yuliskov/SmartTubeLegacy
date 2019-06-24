package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.misc.AdAwayClient;

public class AdAwayInterceptor extends RequestInterceptor {
    private Context mContext;
    private final AdAwayClient mClient;

    public AdAwayInterceptor(Context context) {
        super(context);

        mContext = context;
        mClient = new AdAwayClient(context);
    }

    @Override
    public boolean test(String url) {
        return mClient.isAd(url);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        // block url
        return new WebResourceResponse(null, null, null);
    }
}
