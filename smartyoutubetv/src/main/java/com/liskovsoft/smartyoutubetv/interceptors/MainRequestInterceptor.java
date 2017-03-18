package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;

import java.util.ArrayList;

public class MainRequestInterceptor extends RequestInterceptor {
    private Context mContext;
    private final ArrayList<RequestInterceptor> mInterceptors;

    public MainRequestInterceptor(Context context) {
        mContext = context;
        mInterceptors = new ArrayList<>();
        mInterceptors.add(new VideoInfoInterceptor(context));
    }

    @Override
    public boolean test(String url) {
        for (RequestInterceptor interceptor : mInterceptors) {
            if (interceptor.test(url)){
                return true;
            }
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        for (RequestInterceptor interceptor : mInterceptors) {
            if (interceptor.test(url)){
                return interceptor.intercept(url);
            }
        }
        return null;
    }
}
