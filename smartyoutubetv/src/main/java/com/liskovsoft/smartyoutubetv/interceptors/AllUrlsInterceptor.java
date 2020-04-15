package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import okhttp3.Response;

import java.io.InputStream;

/**
 * Intercepts everything that remains from previous interceptors<br/>
 */
public class AllUrlsInterceptor extends RequestInterceptor {
    private static final String TAG = AllUrlsInterceptor.class.getSimpleName();

    public AllUrlsInterceptor(Context context) {
        super(context);
    }

    @Override
    public boolean test(String url) {
        return true;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        String postData = CommonApplication.getPreferences().getPostData();

        if (postData == null) {
            Log.e(TAG, "Post body is empty! Skipping url: " + url);
            return null;
        }

        Response response = null;

        //response = OkHttpHelpers.doPostOkHttpRequest(url, mManager.getHeaders(), body, "application/x-www-form-urlencoded");

        return createResponse(response);
    }
}
