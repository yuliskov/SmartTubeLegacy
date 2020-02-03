package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.flavors.common.TwoFragmentsManagerActivity;
import com.liskovsoft.smartyoutubetv.fragments.TwoFragmentManager;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;

public class MainExoInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final ExoInterceptor mExoInterceptor;
    private final DecipherInterceptor mCipherInterceptor;
    private final DelayedCommandCallInterceptor mDoOnPlayEndInterceptor;
    private final ExoNextInterceptor mExoNextInterceptor;
    private final HistoryInterceptor mHistoryInterceptor;
    private final ScreenMirrorInterceptor mScreenMirrorInterceptor;
    private RequestInterceptor mCurrentInterceptor;

    public MainExoInterceptor(Context context) {
        super(context);
        
        mContext = context;
        mDoOnPlayEndInterceptor = new DelayedCommandCallInterceptor(context);
        mExoNextInterceptor = new ExoNextInterceptor(context);
        mCipherInterceptor = new DecipherInterceptor(context);
        mHistoryInterceptor = new HistoryInterceptor(context);
        mExoInterceptor = new ExoInterceptor(context, mDoOnPlayEndInterceptor, mExoNextInterceptor, mHistoryInterceptor);
        mScreenMirrorInterceptor = new ScreenMirrorInterceptor(context, this);
    }

    @Override
    public boolean test(String url) {
        if (url.contains("tv-player")) {
            mCurrentInterceptor = mCipherInterceptor;
            return true;
        }

        if (url.contains("/get_video_info")) {
            mCurrentInterceptor = mExoInterceptor;
            return true;
        }

        if (url.contains("/youtubei/v1/next") ||
            url.contains("/youtubei/v1/browse") ||
            url.contains("/youtubei/v1/guide")) {
            mCurrentInterceptor = mExoNextInterceptor;
            return true;
        }

        // useful places: ptracking, log_event, log_interaction
        // at this moment video should be added to history
        // attention: not working when WebView restored
        if (url.contains("/ptracking")) {
            mCurrentInterceptor = mDoOnPlayEndInterceptor;
            return true;
        }

        // history is tracked via YouTubeTracker
        if (url.contains("/api/stats/watchtime")) {
            mCurrentInterceptor = mHistoryInterceptor;
            return true;
        }

        if (url.contains("/api/lounge/bc/bind?device=LOUNGE_SCREEN")) {
            mCurrentInterceptor = mScreenMirrorInterceptor;
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (!test(url)) {
            return null;
        }

        if (mCurrentInterceptor == null) {
            // block url
            return emptyResponse();
        }

        return mCurrentInterceptor.intercept(url);
    }

    public ExoInterceptor getExoInterceptor() {
        return mExoInterceptor;
    }

    public TwoFragmentManager getTwoFragmentManager() {
        return (TwoFragmentManager) mContext;
    }
}
