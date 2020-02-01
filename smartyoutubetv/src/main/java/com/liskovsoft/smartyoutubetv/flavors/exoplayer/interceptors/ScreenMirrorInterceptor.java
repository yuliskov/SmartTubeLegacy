package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ScreenMirrorInterceptor extends RequestInterceptor {
    private static final String TAG = ScreenMirrorInterceptor.class.getSimpleName();
    private final SmartPreferences mPrefs;

    public ScreenMirrorInterceptor(Context context) {
        super(context);
        mPrefs = CommonApplication.getPreferences();
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        String postData = mPrefs.getPostData();

        Log.d(TAG, url);
        Log.d(TAG, postData);

        WebResourceResponse res = null;

        //if (postData != null && postData.contains("req0_state=2")) {
        //    res = emptyResponse();
        //}

        return res;
    }

}
