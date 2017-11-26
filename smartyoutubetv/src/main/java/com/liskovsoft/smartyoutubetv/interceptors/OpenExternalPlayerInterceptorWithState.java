package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.MainRequestInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity;
import com.liskovsoft.smartyoutubetv.flavors.webview.interceptors.OpenExternalPlayerInterceptor;

public class OpenExternalPlayerInterceptorWithState extends RequestInterceptor {
    private RequestInterceptor mInterceptor;
    private Context mContext;

    public OpenExternalPlayerInterceptorWithState(Context context) {
        mContext = context;
        boolean isWebView = mContext instanceof SmartYouTubeTVActivity;
        boolean isXWalk = mContext instanceof com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTVActivity;
        boolean isExo = mContext instanceof SmartYouTubeTVExoBase;
        if (isWebView || isXWalk) {
            mInterceptor = new OpenExternalPlayerInterceptor(mContext);
        } else if (isExo) {
            mInterceptor = new MainRequestInterceptor(mContext);
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
