package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.AdAwayClient;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

public class AdAwayInterceptor extends RequestInterceptor {
    private final AdAwayClient mClient;
    private final boolean mAdBlockEnabled;

    public AdAwayInterceptor(Context context) {
        super(context);

        mClient = new AdAwayClient(context);

        if (SmartUtils.isExo(context)) {
            mAdBlockEnabled = true; // adview doesn't work on Pro
        } else {
            mAdBlockEnabled = SmartUtils.isAdBlockEnabled();
        }
    }

    @Override
    public boolean test(String url) {
        if (mAdBlockEnabled) {
            return mClient.isAd(url);
        } else {
            return false;
        }
    }

    @Override
    public WebResourceResponse intercept(String url) {
        // block url
        return emptyResponse();
    }
}
