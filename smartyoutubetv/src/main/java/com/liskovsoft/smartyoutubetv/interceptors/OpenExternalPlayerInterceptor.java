package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.MainExoInterceptor;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

public class OpenExternalPlayerInterceptor extends RequestInterceptor {
    private RequestInterceptor mInterceptor;

    public OpenExternalPlayerInterceptor(Context context) {
        super(context);

        if (SmartUtils.isExo(context)) {
            mInterceptor = new MainExoInterceptor(context);
        }
    }

    @Override
    public boolean test(String url) {
        if (mInterceptor == null) {
            return false;
        }

        return mInterceptor.test(url);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return mInterceptor.intercept(url);
    }
}
