package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import okhttp3.MediaType;

import java.io.InputStream;

/**
 * Intercepts Ad banner situations.
 */
public class IntegratedAdInterceptor extends RequestInterceptor {
    private static final String TAG = IntegratedAdInterceptor.class.getSimpleName();
    private static final String HOME_PAGE_ID = "\"browseId\":\"default\"";
    private static final String BROWSE_URL = "/youtubei/v1/browse";
    private final boolean mAdBlockEnabled;

    public IntegratedAdInterceptor(Context context) {
        super(context);
        mAdBlockEnabled = SmartUtils.isAdBlockEnabled();
    }

    @Override
    public boolean test(String url) {
        if (mAdBlockEnabled) {
            return url.contains(BROWSE_URL);
        } else {
            return false;
        }
    }

    @Override
    public WebResourceResponse intercept(String url) {
        String postData = CommonApplication.getPreferences().getPostData();

        if (postData == null) {
            Log.e(TAG, "Post body is empty! Skipping url: " + url);
            return null;
        }

        if (!postData.contains(HOME_PAGE_ID)) {
            Log.e(TAG, "Not Home page. Skip filtering! Url: " + url);
            return null;
        }

        InputStream urlData = postJsonData(url, postData);

        WebResourceResponse response = null;

        if (Log.getLogType().equals(Log.LOG_TYPE_FILE) && urlData != null) {
            String content = Helpers.toString(urlData);
            Log.d(TAG, "Url: " + url + ". Post Data: " + postData + ". Response: " + content);
            urlData = Helpers.toStream(content);
        }

        if (urlData != null) {
            Log.d(TAG, "Searching and removing tv masthead section...");
            JsonBrowseParser browseParser = JsonBrowseParser.parse(urlData);
            if (browseParser.canRemoveMustHead()) {
                response = createResponse(MediaType.parse("application/json"), browseParser.removeMustHead().build());
            } else {
                if (Log.getLogType().equals(Log.LOG_TYPE_FILE)) {
                    Log.d(TAG, "Oops. Response doesn't contain MustHead section. Url: " + url + ". Post Data: " + postData + ". Response: " + Helpers.toString(browseParser.build()));
                } else {
                    Log.d(TAG, "Oops. Response doesn't contain MustHead section. Url: " + url + ". Post Data: " + postData);
                }
            }
        } else {
            Log.e(TAG, "Error. Response in empty. Url: " + url + ". Post Data: " + postData);
        }

        return response;
    }
}
