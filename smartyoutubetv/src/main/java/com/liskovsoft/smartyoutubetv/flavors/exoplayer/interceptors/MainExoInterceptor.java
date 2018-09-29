package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class MainExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final RequestInterceptor mExoInterceptor;
    private final DecipherInterceptor mCipherInterceptor;
    private final DelayedCommandCallInterceptor mDoOnPlayEndInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public MainExoInterceptor(Context context) {
        mContext = context;
        mDoOnPlayEndInterceptor = new DelayedCommandCallInterceptor();
        mExoInterceptor = new ExoInterceptor(context, mDoOnPlayEndInterceptor);
        mCipherInterceptor = new DecipherInterceptor(context);
    }

    @Override
    public boolean test(String url) {
        if (url.contains("get_video_info")) {
            mCurrentInterceptor = mExoInterceptor;
            return true;
        }

        if (url.contains("tv-player.js")) {
            mCurrentInterceptor = mCipherInterceptor;
            return true;
        }

        // "videoplayback": MuteVideoInterceptor

        // useful places: ptracking, log_event, log_interaction
        // at this moment video should be added to history
        // attention: not working when WebView restored
        if (url.contains("ptracking")) {
            mCurrentInterceptor = mDoOnPlayEndInterceptor;
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
