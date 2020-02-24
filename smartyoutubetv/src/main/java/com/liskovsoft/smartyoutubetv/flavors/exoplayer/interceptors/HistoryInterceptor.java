package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.os.Handler;
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

        // MIBox Mini delayed history fix:
        // History url received after video has been closed.
        // Also, this can help to active history for some other devices.
        updatePosition(mPosition);

        //notifyPositionChange();

        // block url
        return emptyResponse();
    }

    private void notifyPositionChange() {
        if (mUrl != null) {
            YouTubeWatchTime watchTime = YouTubeWatchTime.parse(mUrl);
            int pos = watchTime.getCurrentPosition();
            CommonApplication.getPreferences().setCurrentVideoPosition(pos);
        }
    }

    public void updatePosition(float position) {
        Log.d(TAG, "Updating position. Position: " + position + ". Url: " + mUrl);

        mPosition = position;

        if (mUrl != null) {
            mTracker.sync(mUrl, mPosition, 0);
        }
    }

    public void onStart() {
        mUrl = null;
        mPosition = 0;
    }
}
