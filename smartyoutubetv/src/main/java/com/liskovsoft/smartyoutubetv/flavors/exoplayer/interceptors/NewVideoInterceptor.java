package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class NewVideoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private boolean mIsNewVideo;

    public NewVideoInterceptor(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        mIsNewVideo = true;
        return null;
    }

    public boolean isNewVideo() {
        return mIsNewVideo;
    }

    public void reset() {
        mIsNewVideo = false;
    }
}
