package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;

public class BackgroundActionManager {
    private static final String TAG = BackgroundActionManager.class.getSimpleName();
    private static final long NO_INTERACTION_TIMEOUT_MS = 500;
    private static final long SAME_VIDEO_NO_INTERACTION_TIMEOUT_MS = 2_000;
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final String PARAM_MIRROR = "ytr";
    /**
     * fix playlist advance bug<br/>
     * create time window (1sec) where get_video_info isn't allowed<br/>
     * see {@link ExoInterceptor#intercept(String)} method
     */
    private long mExitTime;
    private long mPrevCallTime;
    private String mPrevVideoId;
    private boolean mIsOpened;

    public boolean cancelPlayback(String url) {
        if (!url.contains(ExoInterceptor.URL_VIDEO_DATA))
            return true;

        long elapsedTimeAfterClose = System.currentTimeMillis() - mExitTime;
        Log.d(TAG, "Video closed ms ago: " + elapsedTimeAfterClose);

        // Search screen and XWalk fix: same video intercepted twice (Why??)
        boolean videoClosedRecently = elapsedTimeAfterClose < NO_INTERACTION_TIMEOUT_MS;
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
        String videoId = MyUrlEncodedQueryString.parse(url).get(PARAM_VIDEO_ID);
        if (videoId == null) {
            Log.d(TAG, "Supplied url doesn't contain video info");
            mPrevCallTime = System.currentTimeMillis();
            return true;
        }

        boolean sameVideo = videoId.equals(mPrevVideoId);
        boolean sameVideoClosedRecently = elapsedTimeAfterClose < SAME_VIDEO_NO_INTERACTION_TIMEOUT_MS;
        if (sameVideo && (sameVideoClosedRecently || mIsOpened)) {
            Log.d(TAG, "The same video encountered");
            mPrevCallTime = System.currentTimeMillis();
            return true;
        }

        mPrevVideoId = videoId;
        mPrevCallTime = System.currentTimeMillis();
        mIsOpened = false;

        return false;
    }

    public void onClose() {
        Log.d(TAG, "Video is closed");
        mExitTime = System.currentTimeMillis();
        //mPrevVideoId = null;
        mIsOpened = false;
    }

    public void onCancel() {
        Log.d(TAG, "Video is canceled");
        //mPrevVideoId = null;
        mIsOpened = false;
    }

    public void onOpen() {
        Log.d(TAG, "Video has been opened");
        mIsOpened = true;
    }

    public boolean isOpened() {
        return mIsOpened;
    }

    public boolean isMirroring(String url) {
        String mirrorDeviceName = MyUrlEncodedQueryString.parse(url).get(PARAM_MIRROR);

        if (mirrorDeviceName != null && !mirrorDeviceName.isEmpty()) { // any response is good
            Log.d(TAG, "The video is mirroring from the phone or tablet");
            return true;
        }

        return false;
    }
}
