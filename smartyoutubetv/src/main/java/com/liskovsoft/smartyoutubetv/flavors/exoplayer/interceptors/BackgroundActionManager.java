package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import com.liskovsoft.sharedutils.helpers.KeyHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.misc.keyhandler.GlobalKeyHandler;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class BackgroundActionManager {
    private static final String TAG = BackgroundActionManager.class.getSimpleName();
    private static final long SAME_VIDEO_NO_INTERACTION_TIMEOUT_MS = 1_000;
    private static final long VIDEO_CANCEL_TIMEOUT_MS = 1_000; // Don't increase value!
    private static final long PLAYLIST_CANCEL_TIMEOUT_MS = 3_000; // Don't increase value!
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final String PARAM_PLAYLIST_ID = "list";
    private static final String PARAM_MIRROR = "ytr";
    private static final String PARAM_MIRROR2 = "ytrcc";
    private final GlobalKeyHandler mKeyHandler;
    /**
     * fix playlist advance bug<br/>
     * create time window (1sec) where get_video_info isn't allowed<br/>
     * see {@link ExoInterceptor#intercept(String)} method
     */
    private long mContinueTime;
    private long mCancelTime;
    private boolean mIsOpened;
    private String mCurrentUrl;
    private boolean mSameVideo;
    private final SmartPreferences mPrefs;
    private String mReason;

    public BackgroundActionManager(GlobalKeyHandler keyHandler) {
        mPrefs = CommonApplication.getPreferences();
        mKeyHandler = keyHandler;
    }

    public boolean cancelPlayback() {
        if (mCurrentUrl == null || !mCurrentUrl.contains(ExoInterceptor.URL_VIDEO_DATA)) {
            mReason = "Cancel playback: No video data.";
            Log.d(TAG, mReason);
            return true;
        }

        if (getVideoId(mCurrentUrl) == null) {
            mReason = "Cancel playback: Supplied url doesn't contain video info.";
            Log.d(TAG, mReason);
            return true;
        }

        if (mSameVideo && mIsOpened) {
            mReason = "Cancel playback: Same video while doing playback.";
            Log.d(TAG, mReason);
            return true;
        }

        long curTimeMS = System.currentTimeMillis();

        boolean continueRecently = (curTimeMS - mContinueTime) < SAME_VIDEO_NO_INTERACTION_TIMEOUT_MS;

        if (mSameVideo && continueRecently && !mIsOpened) {
            mReason = "Cancel playback: Same video right after close previous.";
            Log.d(TAG, mReason);
            return true;
        }

        boolean cancelRecently = (curTimeMS - mCancelTime) < VIDEO_CANCEL_TIMEOUT_MS;

        if (cancelRecently) {
            mReason = "Cancel playback: User closed video recently.";
            Log.d(TAG, mReason);
            return true;
        }

        //boolean somethingPressedRecently = (System.currentTimeMillis() - mKeyHandler.getLastEventTimeMs()) < 3_000;
        //boolean isSpecialKey = KeyHelpers.isConfirmKey(mKeyHandler.getLastEventKeyCode()) || KeyHelpers.isMediaKey(mKeyHandler.getLastEventKeyCode());
        //
        //if (!isSpecialKey && somethingPressedRecently) { // fix music videos autoplay
        //    mReason = "User didn't pressed ok recently. Exiting... Code is " + mKeyHandler.getLastEventKeyCode();
        //    Log.d(TAG, mReason);
        //    return true;
        //}

        boolean isUpcoming = SmartPreferences.VIDEO_TYPE_UPCOMING.equals(mPrefs.getCurrentVideoType());

        if (isUpcoming) {
            mReason = "Cancel playback: Video announced but not available yet.";
            Log.d(TAG, mReason);
            return true;
        }

        return false;
    }

    public void onContinue() {
        Log.d(TAG, "Video: on continue");
        mContinueTime = System.currentTimeMillis();
        //mPrevVideoId = null;
        mIsOpened = false;
    }

    public void onCancel() {
        Log.d(TAG, "Video: canceled");
        mCancelTime = System.currentTimeMillis();
        //mPrevVideoId = null;
        mIsOpened = false;
    }

    public void init(String url) {
        //onInitMeasure();
        recordUrl(url);

        mCurrentUrl = url;
    }

    private void recordUrl(String url) {
        String prevVideoId = getVideoId(mCurrentUrl);
        String currentVideoId = getVideoId(url);

        if (prevVideoId != null) {
            mSameVideo = prevVideoId.equals(currentVideoId);
        } else {
            mSameVideo = false;
        }

        if (mSameVideo) {
            Log.d(TAG, "The same video encountered");
        }
    }

    public void onOpen() {
        Log.d(TAG, "Video has been opened");
        mIsOpened = true;
    }

    public boolean isOpened() {
        return mIsOpened;
    }

    private boolean isMirroring(String url) {
        String mirrorDeviceName = MyUrlEncodedQueryString.parse(url).get(PARAM_MIRROR);

        if (mirrorDeviceName == null) {
            mirrorDeviceName = MyUrlEncodedQueryString.parse(url).get(PARAM_MIRROR2);
        }

        if (mirrorDeviceName != null && !mirrorDeviceName.isEmpty()) { // any response is good
            Log.d(TAG, "The video is mirroring from the phone or tablet");
            return true;
        }

        return false;
    }

    public boolean isMirroring() {
        if (mCurrentUrl == null) {
            return false;
        }

        return isMirroring(mCurrentUrl);
    }

    public String getVideoId(String url) {
        if (url == null) {
            return null;
        }

        return MyUrlEncodedQueryString.parse(url).get(PARAM_VIDEO_ID);
    }

    public String getPlaylistId(String url) {
        if (url == null) {
            return null;
        }

        return MyUrlEncodedQueryString.parse(url).get(PARAM_PLAYLIST_ID);
    }

    public String getReason() {
        return mReason;
    }
}
