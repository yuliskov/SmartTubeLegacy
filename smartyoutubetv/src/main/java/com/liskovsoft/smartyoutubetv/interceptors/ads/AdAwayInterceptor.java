package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.AdAwayClient;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

public class AdAwayInterceptor extends RequestInterceptor {
    private static final String TAG = AdAwayInterceptor.class.getSimpleName();
    private final AdAwayClient mClient;
    private final boolean mAdBlockEnabled;

    public AdAwayInterceptor(Context context) {
        super(context);

        mClient = new AdAwayClient(context);

        if (SmartUtils.isExo(context)) {
            mAdBlockEnabled = true; // adview doesn't work on Pro
        } else {
            mAdBlockEnabled = CommonApplication.getPreferences().isAdBlockEnabled();
        }

        Log.d(TAG, "AdBlock enabled " + mAdBlockEnabled);
    }

    @Override
    public boolean test(String url) {
        if (mAdBlockEnabled) {
            return mClient.isAd(url);
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        // block url
        return emptyResponse();
    }
}
