package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;

import java.io.InputStream;

public class IntegratedAdInterceptor extends RequestInterceptor {
    private static final String TAG = IntegratedAdInterceptor.class.getSimpleName();

    public IntegratedAdInterceptor(Context context) {
        super(context);
    }

    @Override
    public boolean test(String url) {
        return Log.getLogType().equals(Log.LOG_TYPE_FILE) && url.contains("/youtubei/v1/browse");
    }

    @Override
    public WebResourceResponse intercept(String url) {
        InputStream urlData = postUrlData(url, CommonApplication.getPreferences().getPostData());

        String content = "Empty response";

        if (urlData != null) {
            content = Helpers.toString(urlData);
        }

        Log.d(TAG, "Url: " + url + ", Response: " + content);

        return null;
    }
}
