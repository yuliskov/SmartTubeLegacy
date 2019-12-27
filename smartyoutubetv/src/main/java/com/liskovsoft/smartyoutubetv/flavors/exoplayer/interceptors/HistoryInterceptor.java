package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.youtubeutils.YouTubeHistoryUpdater;
import com.liskovsoft.smartyoutubetv.misc.youtubeutils.YouTubeWatchTime;

public class HistoryInterceptor extends RequestInterceptor {
    private static final String TAG = HistoryInterceptor.class.getSimpleName();
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
        Log.d(TAG, "On url: " + url);

        mUrl = url;

        //notifyPositionChange();

        // block url
        return new WebResourceResponse(null, null, null);
    }

    private void notifyPositionChange() {
        if (mUrl != null) {
            YouTubeWatchTime watchTime = YouTubeWatchTime.parse(mUrl);
            int pos = watchTime.getCurrentPosition();
            CommonApplication.getPreferences().setCurrentVideoPosition(pos);
        }
    }

    public void updatePosition(float position) {
        mPosition = position;

        if (mUrl != null) {
            mTracker.sync(mUrl, mPosition, 0);
        }
    }

    public void onStart() {
        mUrl = null;
    }
}
