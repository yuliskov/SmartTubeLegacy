package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.util.Log;
import com.liskovsoft.smartyoutubetv.misc.MyUrlEncodedQueryString;

public class BackgroundActionManager {
    private static final String TAG = BackgroundActionManager.class.getSimpleName();
    private static final long NO_INTERACTION_TIMEOUT_MS = 500;
    private static final String VIDEO_ID_PARAM = "video_id";
    /**
     * fix playlist advance bug<br/>
     * create time window (1sec) where get_video_info isn't allowed<br/>
     * see {@link ExoInterceptor#intercept(String)} method
     */
    private long mExitTime;
    private long mPrevCallTime;
    private String mPrevVideoId;

    public boolean cancelAction(String url) {
        // Search screen and XWalk fix: same video intercepted twice (Why??)
        boolean videoClosedRecently = System.currentTimeMillis() - mExitTime < NO_INTERACTION_TIMEOUT_MS;
        if (videoClosedRecently) {
            Log.d(TAG, "Video is closed recently");
            mPrevCallTime = System.currentTimeMillis();
            return true;
        }

        // throttle calls
        boolean highCallRate = System.currentTimeMillis() - mPrevCallTime < NO_INTERACTION_TIMEOUT_MS;
        if (highCallRate) {
            Log.d(TAG, "To high call rate");
            mPrevCallTime = System.currentTimeMillis();
            return true;
        }

        // the same video could opened multiple times
        String videoId = MyUrlEncodedQueryString.parse(url).get(VIDEO_ID_PARAM);
        if (videoId == null) {
            Log.d(TAG, "Supplied url doesn't contain video info");
            mPrevCallTime = System.currentTimeMillis();
            return true;
        }

        boolean sameVideo = videoId.equals(mPrevVideoId);
        if (sameVideo) {
            Log.d(TAG, "The same video encountered");
            mPrevCallTime = System.currentTimeMillis();
            return true;
        }

        mPrevVideoId = videoId;
        mPrevCallTime = System.currentTimeMillis();

        return false;
    }

    public void onClose() {
        Log.d(TAG, "Video is closed");
        mExitTime = System.currentTimeMillis();
        mPrevVideoId = null;
    }

    public void onCancel() {
        Log.d(TAG, "Video is canceled");
        mPrevVideoId = null;
    }
}
