package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.YouTubeHistoryUpdater;

public class HistoryInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final YouTubeHistoryUpdater mTracker;
    private float mPosition;

    public HistoryInterceptor(Context context) {
        super(context);
        mContext = context;
        mTracker = new YouTubeHistoryUpdater(mContext);
    }

    @Override
    public boolean test(String url) {
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        mTracker.sync(url, mPosition, 0);

        // block url
        return new WebResourceResponse(null, null, null);
    }

    public void setPosition(float position) {
        mPosition = position;
    }
}
