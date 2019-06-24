package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class MainExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final ExoInterceptor mExoInterceptor;
    private final DecipherInterceptor mCipherInterceptor;
    private final DelayedCommandCallInterceptor mDoOnPlayEndInterceptor;
    private final ExoNextInterceptor mExoNextInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public MainExoInterceptor(Context context) {
        super(context);
        
        mContext = context;
        mDoOnPlayEndInterceptor = new DelayedCommandCallInterceptor(context);
        mExoNextInterceptor = new ExoNextInterceptor(context);
        mExoInterceptor = new ExoInterceptor(context, mDoOnPlayEndInterceptor, mExoNextInterceptor);
        mCipherInterceptor = new DecipherInterceptor(context);
    }

    @Override
    public boolean test(String url) {
        if (url.contains(ExoInterceptor.URL_VIDEO_DATA)) {
            mCurrentInterceptor = mExoInterceptor;
            return true;
        }

        if (url.contains("tv-player")) {
            mCurrentInterceptor = mCipherInterceptor;
            return true;
        }

        // useful places: ptracking, log_event, log_interaction
        // at this moment video should be added to history
        // attention: not working when WebView restored
        if (url.contains("ptracking")) {
            mCurrentInterceptor = mDoOnPlayEndInterceptor;
            return true;
        }

        if (url.contains(ExoNextInterceptor.URL_NEXT_DATA)) {
            mCurrentInterceptor = mExoNextInterceptor;
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        return mCurrentInterceptor.intercept(url);
    }
}
