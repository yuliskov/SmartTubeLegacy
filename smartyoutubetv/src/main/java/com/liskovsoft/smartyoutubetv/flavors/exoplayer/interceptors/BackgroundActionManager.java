package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.view.KeyEvent;
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

    public BackgroundActionManager(GlobalKeyHandler keyHandler) {
        mPrefs = CommonApplication.getPreferences();
        mKeyHandler = keyHandler;
    }

    public boolean cancelPlayback() {
        if (mCurrentUrl == null || !mCurrentUrl.contains(ExoInterceptor.URL_VIDEO_DATA)) {
            Log.d(TAG, "Cancel playback: No video data.");
            return true;
        }

        if (getVideoId(mCurrentUrl) == null) {
            Log.d(TAG, "Cancel playback: Supplied url doesn't contain video info.");
            return true;
        }

        if (mSameVideo && mIsOpened) {
            Log.d(TAG, "Cancel playback: Same video while doing playback.");
            return true;
        }

        long curTimeMS = System.currentTimeMillis();

        boolean continueRecently = (curTimeMS - mContinueTime) < SAME_VIDEO_NO_INTERACTION_TIMEOUT_MS;

        if (mSameVideo && continueRecently && !mIsOpened) {
            Log.d(TAG, "Cancel playback: Same video right after close previous.");
            return true;
        }

        boolean cancelRecently = (curTimeMS - mCancelTime) < VIDEO_CANCEL_TIMEOUT_MS;

        if (cancelRecently) {
            Log.d(TAG, "Cancel playback: User closed video recently.");
            return true;
        }

        boolean somethingPressedRecently = (System.currentTimeMillis() - mKeyHandler.getLastEventTimeMs()) < 3_000;
        boolean isOkKey = KeyHelpers.isConfirmKey(mKeyHandler.getLastEventKeyCode());

        if (!isOkKey && somethingPressedRecently) { // fix music videos autoplay
            Log.d(TAG, "User didn't pressed ok recently. Exiting... Code is " + mKeyHandler.getLastEventKeyCode());
            return true;
        }

        //boolean isPlaylist = mCurrentUrl.contains("&" + PARAM_PLAYLIST_ID + "=");
        //
        //// Fix playlist bug when new video opens without user interactions
        //boolean playlistCancel = isPlaylist && ((curTimeMS - mCancelTime) < PLAYLIST_CANCEL_TIMEOUT_MS);
        //
        //if (playlistCancel) {
        //    Log.d(TAG, "Cancel playback: User closed video recently in playlist.");
        //    return true;
        //}

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
}
