package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;

/**
 * Intercepts everything that remains from previous interceptors<br/>
 */
public class AllUrlsInterceptor extends RequestInterceptor {
    public AllUrlsInterceptor(Context context) {
        super(context);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return null;
    }
}
