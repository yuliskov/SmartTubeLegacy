package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import okhttp3.MediaType;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Try to get rich response from browse request.<br/>
 * Such response contains video long click menu items.<br/>
 * Main trick is to change user agent to Cobalt.<br/>
 * NOTE: set user agent via js produce next error: Refused to set unsafe header "User-Agent"
 */
public class VideoMenuInterceptor extends RequestInterceptor {
    private static final String TAG = VideoMenuInterceptor.class.getSimpleName();
    private static final String HOME_PAGE_ID = "\"browseId\":\"default\"";
    private static final String CONTINUATION_ID = "\"continuation\":";
    private static final String BROWSE_ID = "\"browseId\":";
    private static final String BROWSE_URL = "/youtubei/v1/browse";
    private static final String BROWSE_NEXT_URL = "/youtubei/v1/next";
    private final Context mContext;
    private boolean mIsHome;
    private boolean mEnabled;

    public VideoMenuInterceptor(Context context) {
        super(context);
        mContext = context;
        mEnabled = CommonApplication.getPreferences().getEnableVideoMenu();
    }

    @Override
    public boolean test(String url) {
        if (url.endsWith(BROWSE_URL)) {
            return mEnabled;
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

        WebResourceResponse result = null;

        HashMap<String, String> customHeaders = new HashMap<>();
        customHeaders.put("User-Agent", UserAgentManager.COBALT_CLIENT[0]);

        InputStream dataStream = postJsonData(url, postData, customHeaders);

        if (dataStream != null) {
            result = createResponse(MediaType.parse("application/json"), dataStream);
        }

        return result;
    }
}
