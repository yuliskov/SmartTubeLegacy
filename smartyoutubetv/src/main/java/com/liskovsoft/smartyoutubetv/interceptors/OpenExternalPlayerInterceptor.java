package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.MainExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVFragment;
import com.liskovsoft.smartyoutubetv.flavors.webview.interceptors.OpenPlayerIntentInterceptor;

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
