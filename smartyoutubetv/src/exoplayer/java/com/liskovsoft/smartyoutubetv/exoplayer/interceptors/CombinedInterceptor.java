package com.liskovsoft.smartyoutubetv.exoplayer.interceptors;

import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class CombinedInterceptor extends RequestInterceptor {
    private final RequestInterceptor[] mInterceptors;

    public CombinedInterceptor(RequestInterceptor ...interceptors) {
        mInterceptors = interceptors;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        for (RequestInterceptor interceptor : mInterceptors) {
            interceptor.intercept(url);
        }
        return null;
    }
}
