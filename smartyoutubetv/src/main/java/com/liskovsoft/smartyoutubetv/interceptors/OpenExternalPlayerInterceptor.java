package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.MainExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity;
import com.liskovsoft.smartyoutubetv.flavors.webview.interceptors.OpenPlayerIntentInterceptor;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private RequestInterceptor mInterceptor;
    private Context mContext;

    public OpenExternalPlayerInterceptor(Context context) {
        mContext = context;
        boolean isWebView = mContext instanceof SmartYouTubeTVActivity;
        boolean isXWalk = mContext instanceof com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTVActivity;
        boolean isExo = mContext instanceof SmartYouTubeTVExoBase;
        if (isWebView || isXWalk) {
            mInterceptor = new OpenPlayerIntentInterceptor(mContext);
        } else if (isExo) {
            mInterceptor = new MainExoInterceptor(mContext);
        }
    }

    @Override
    public boolean test(String url) {
        return mInterceptor.test(url);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return mInterceptor.intercept(url);
    }
}
