package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.YouTubeHistoryUpdater;

public class HistoryInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final YouTubeHistoryUpdater mTracker;
    private float mPosition;
    private String mUrl;

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
        mUrl = url;

        // block url
        return new WebResourceResponse(null, null, null);
    }

    public void updatePosition(float position) {
        mPosition = position;

        if (mUrl != null) {
            mTracker.sync(mUrl, mPosition, 0);
        }
    }
}
