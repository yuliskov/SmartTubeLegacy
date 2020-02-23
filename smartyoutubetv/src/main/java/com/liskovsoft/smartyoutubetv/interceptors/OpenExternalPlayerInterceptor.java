package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.MainExoInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTV1080Activity;
import com.liskovsoft.smartyoutubetv.flavors.webview.interceptors.OpenPlayerIntentInterceptor;
import com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTV1080AltActivity;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private RequestInterceptor mInterceptor;
    private Context mContext;

    public OpenExternalPlayerInterceptor(Context context) {
        super(context);

        mContext = context;
        if (SmartUtils.isWebView(mContext) || SmartUtils.isXWalk(mContext)) {
            mInterceptor = new OpenPlayerIntentInterceptor(mContext);
        } else if (SmartUtils.isExo(mContext)) {
            mInterceptor = new MainExoInterceptor(mContext);
        }
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
