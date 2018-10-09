package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private RequestInterceptor mInterceptor;
    private Context mContext;

    public OpenExternalPlayerInterceptor(Context context) {
        mContext = context;
        // TODO: fragments, fix later
        //boolean isWebView = mContext instanceof SmartYouTubeTVFragment;
        //boolean isXWalk = mContext instanceof com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTVFragment;
        //boolean isExo = mContext instanceof SmartYouTubeTVExoBase;
        //if (isWebView || isXWalk) {
        //    mInterceptor = new OpenPlayerIntentInterceptor(mContext);
        //} else if (isExo) {
        //    mInterceptor = new MainExoInterceptor(mContext);
        //}
    }

    @Override
    public boolean test(String url) {
        if (mInterceptor == null)
            return false;
        return mInterceptor.test(url);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return mInterceptor.intercept(url);
    }
}
