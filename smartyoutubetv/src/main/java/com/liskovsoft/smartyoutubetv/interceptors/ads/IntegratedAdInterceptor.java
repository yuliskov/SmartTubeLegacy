package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import okhttp3.MediaType;

import java.io.InputStream;

public class IntegratedAdInterceptor extends RequestInterceptor {
    private static final String TAG = IntegratedAdInterceptor.class.getSimpleName();

    public IntegratedAdInterceptor(Context context) {
        super(context);
    }

    @Override
    public boolean test(String url) {
        return url.contains("/youtubei/v1/browse");
    }

    @Override
    public WebResourceResponse intercept(String url) {
        String postData = CommonApplication.getPreferences().getPostData();
        InputStream urlData = postUrlData(url, postData);

        String content = "Empty response";
        WebResourceResponse response = null;

        if (Log.getLogType().equals(Log.LOG_TYPE_FILE) && urlData != null) {
            content = Helpers.toString(urlData);
        }

        Log.d(TAG, "Url: " + url + ", Post Data: " + postData + ", Response: " + content);

        if (urlData != null) {
            Log.d(TAG, "Searching and removing tv masthead section...");
            response = createResponse(MediaType.parse("application/json"), JsonBrowseParser.parse(urlData).removeMustHead().build());
        }

        return response;
    }
}
