package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.interceptors.ads.AdAwayInterceptor;
import com.liskovsoft.smartyoutubetv.interceptors.ads.IntegratedAdInterceptor;
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
        mInterceptors.add(new IntegratedAdInterceptor(context));
        //mInterceptors.add(new ContentSecurityPolicyInterceptor(context));
        mInterceptors.add(new OpenExternalPlayerInterceptor(context));
        mInterceptors.add(new PlaybackStatsInterceptor(context));
        mInterceptors.add(new LegacyMainScriptManagerInterceptor(context));
        //mInterceptors.add(new AllUrlsInterceptor(context));
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    /**
     * Calling interceptors until encountering non-null response.<br/>
     * So, interceptors order is important!<br/>
     * It's critical for Ad blocking, for example.
     */
    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Intercepting url: " + url);

        WebResourceResponse result = null;

        for (RequestInterceptor interceptor : mInterceptors) {
            if (interceptor.test(url)) {
                result = interceptor.intercept(url);

                // Stop calling interceptors when encountering non-null response.
                // Usually last interception in the list
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }
}
