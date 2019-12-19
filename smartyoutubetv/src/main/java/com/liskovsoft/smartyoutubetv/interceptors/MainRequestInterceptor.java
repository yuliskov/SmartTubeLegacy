package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.interceptors.scripts.LegacyMainScriptManagerInterceptor;

import java.util.ArrayList;

public class MainRequestInterceptor extends RequestInterceptor {
    private static final String TAG = MainRequestInterceptor.class.getSimpleName();
    private Context mContext;
    private final ArrayList<RequestInterceptor> mInterceptors;

    public MainRequestInterceptor(Context context) {
        super(context);

        mContext = context;
        mInterceptors = new ArrayList<>();
        mInterceptors.add(new AdAwayInterceptor(context));
        //mInterceptors.add(new ContentSecurityPolicyInterceptor(context));
        mInterceptors.add(new PlaybackStatsInterceptor(context));
        mInterceptors.add(new LegacyMainScriptManagerInterceptor(context));
        mInterceptors.add(new OpenExternalPlayerInterceptor(context));
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    /**
     * all interceptors are called<br/>
     * first non-null response is the result of all intercept operation
     */
    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Intercepting url: " + url);

        WebResourceResponse result = null;

        for (RequestInterceptor interceptor : mInterceptors) {
            // all interceptors are called
            // first non-null response is the result of all intercept operation
            if (interceptor.test(url)) {
                WebResourceResponse tmpResult = interceptor.intercept(url);
                if (result == null) {
                    result = tmpResult;
                }
            }
        }

        return result;
    }
}
