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
        mInterceptors.add(new AdAwayInterceptor(context));
        mInterceptors.add(new ScriptManagerInterceptor(context));
        mInterceptors.add(new OpenExternalPlayerInterceptor(context));
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

    /**
     * all interceptors are called<br/>
     * first non-null response is the result of all intercept operation
     */
    @Override
    public WebResourceResponse intercept(String url) {
        WebResourceResponse result = null;
        for (RequestInterceptor interceptor : mInterceptors) {
            // all interceptors are called
            // first non-null response is the result of all intercept operation
            if (interceptor.test(url)){
                WebResourceResponse tmpResult = interceptor.intercept(url);
                if (result == null)
                    result = tmpResult;
            }
        }
        return result;
    }
}
