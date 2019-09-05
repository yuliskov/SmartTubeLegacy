package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

/**
 * Used with conjunction with js DeviceUtils.disableCodec
 */
public class PlaybackStatsInterceptor extends RequestInterceptor {
    private static final String DURATION_URL = "youtube.com/gen_204?a=tv_transport&duration=";
    private static final String ERROR_DURATION_URL = "youtube.com/gen_204?a=tv_transport&duration=0&";
    private static final String PLAYBACK_URL = "youtube.com/api/stats/watchtime";
    private static final String PLAYBACK_URL2 = "youtube.com/api/stats/playback";
    private static final String START_VIDEO_URL = "youtube.com/get_midroll_info";
    private static final String PRESTART_VIDEO_URL = "youtube.com/get_video_info";
    private static final String TAG = PlaybackStatsInterceptor.class.getSimpleName();
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private boolean mPlaybackUrlFound = false;
    private int mWorkCount = 0;
    private int mState = 0;
    private int mRunCount = 0;

    public PlaybackStatsInterceptor(Context context) {
        super(context);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean test(String url) {
        if (mPrefs.isAppJustInstalled() || (BuildConfig.DEBUG && mRunCount++ <= 1)) {
            mPrefs.setPlaybackWorking(SmartPreferences.PLAYBACK_UNKNOWN);
        }

        if (mWorkCount > 3 || mPrefs.getPlaybackWorking() == SmartPreferences.PLAYBACK_NOT_WORKING) { // set only once
            return false;
        }

        if (url.contains(PRESTART_VIDEO_URL)) {
            if (mState == SmartPreferences.PLAYBACK_NOT_WORKING) {
                mPrefs.setPlaybackWorking(SmartPreferences.PLAYBACK_NOT_WORKING);
                Log.d(TAG, "Playback is NOT working!!!");
            }
        } else if (url.contains(START_VIDEO_URL)) {
            mState = SmartPreferences.PLAYBACK_NOT_WORKING;
            Log.d(TAG, "Playback is NOT working.. yet!!!");
        } else if (url.contains(PLAYBACK_URL)) {
            mState = SmartPreferences.PLAYBACK_IS_WORKING;
            mWorkCount++;
            Log.d(TAG, "Playback is working!!!");
        }

        return false; // give a chance to handle url from other interceptors
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return null;
    }
}
