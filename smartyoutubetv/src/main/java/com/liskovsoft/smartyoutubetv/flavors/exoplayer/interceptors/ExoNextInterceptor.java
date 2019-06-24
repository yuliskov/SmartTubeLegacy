package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;

public class ExoNextInterceptor extends RequestInterceptor {
    private static final String TAG = ExoNextInterceptor.class.getSimpleName();
    public static final String URL_NEXT_DATA = "youtubei/v1/next";
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private InputStream mResponseStream;

    public ExoNextInterceptor(Context context) {
        super(context);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        Log.d(TAG, "Video metadata is intercepted successfully");

        mResponseStream = getUrlData(url);

        return null;
    }
}
