package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.BackPressInterceptor;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.CipherInterceptor;
import com.liskovsoft.smartyoutubetv.exoplayer.interceptors.ExoInterceptor;

public class OpenExternalPlayerInterceptor2 extends RequestInterceptor {
    private final Context mContext;
    private final RequestInterceptor mExoInterceptor;
    private final RequestInterceptor mCipherInterceptor;
    private final BackPressInterceptor mBackPressInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public OpenExternalPlayerInterceptor2(Context context) {
        mContext = context;
        mExoInterceptor = new ExoInterceptor(context);
        mCipherInterceptor = new CipherInterceptor(context);
        mBackPressInterceptor = new BackPressInterceptor(context);
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

        if (url.contains("ptracking")) {
            mCurrentInterceptor = mBackPressInterceptor;
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
