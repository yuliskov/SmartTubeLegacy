package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class PlaybackStatsInterceptor extends RequestInterceptor {
    private static final String DURATION_URL = "youtube.com/gen_204?a=tv_transport&duration=";
    private static final String DURATION_PARAM = "&duration=0&";
    private static final String TAG = PlaybackStatsInterceptor.class.getSimpleName();
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public PlaybackStatsInterceptor(Context context) {
        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean test(String url) {
        if (!AppInfoHelpers.isAppJustInstalled() &&
            mPrefs.getPlaybackWorking() == SmartPreferences.PLAYBACK_NOT_WORKING) { // set only once
            return false;
        }

        if (url.contains(DURATION_URL)) {
            if (url.contains(DURATION_PARAM)) {
                mPrefs.setPlaybackWorking(SmartPreferences.PLAYBACK_NOT_WORKING);
                Log.d(TAG, "Playback is NOT working!!!");
            } else {
                mPrefs.setPlaybackWorking(SmartPreferences.PLAYBACK_IS_WORKING);
                Log.d(TAG, "Playback is working!!!");
            }
        }

        return false; // give a chance to handle url from other interceptors
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return null;
    }
}
