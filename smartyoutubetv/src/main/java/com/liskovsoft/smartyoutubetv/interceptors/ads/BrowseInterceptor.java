package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;

import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Try to get rich response from browse request.<br/>
 * Such response contains video long click menu items.<br/>
 * Main trick is to change user agent to Cobalt.<br/>
 * NOTE: set user agent via js produce next error: Refused to set unsafe header "User-Agent"
 */
public class BrowseInterceptor extends RequestInterceptor {
    private static final String TAG = BrowseInterceptor.class.getSimpleName();
    private static final String BROWSE_URL = "/youtubei/v1/browse";
    private static final String HOME_ID = "\"browseId\":\"default\"";
    private static final String TOPICS_ID = "\"browseId\":\"FEtopics\"";
    private static final String CONTINUATION_ID = "\"continuation\":";
    private static final String BROWSE_ID = "\"browseId\":";
    private static final String BROWSE_NEXT_URL = "/youtubei/v1/next";
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private Map<String, String> mHeaders;
    private boolean mIsXWalk;
    private boolean mIsAdBlockEnabled;
    private boolean mIsEnableVideoMenu;

    public BrowseInterceptor(Context context) {
        super(context);
        mContext = context;
        mPrefs = CommonApplication.getPreferences();
        mIsXWalk = SmartUtils.isXWalk(mContext);
        mIsAdBlockEnabled = mPrefs.isAdBlockEnabled();
        mIsEnableVideoMenu = mPrefs.getEnableVideoMenu();
        initHeaders();
    }

    private void initHeaders() {
        // add video menu in browse response
        if (mIsEnableVideoMenu) {
            mHeaders = new HashMap<>();
            mHeaders.put("User-Agent", UserAgentManager.COBALT_CLIENT[0]);
        }
    }

    @Override
    public boolean test(String url) {
        return url != null && url.contains(BROWSE_URL);
    }

    @Override
    public WebResourceResponse intercept(String url) {
        WebResourceResponse response = filterBannerData(url);

        if (response == null) { // menu already filtered unless ads is disabled
            response = filterLongPressVideoMenu(url);
        }

        return response;
    }

    private WebResourceResponse filterBannerData(String url) {
        if (mIsXWalk) { // performance impact
            return null;
        }

        if (!mIsAdBlockEnabled) {
            return null;
        }

        String postData = mPrefs.getPostData();

        if (postData == null) {
            Log.e(TAG, "Post body is empty! Skipping url: " + url);
            return null;
        }

        if (!postData.contains(HOME_ID) && !postData.contains(TOPICS_ID)) {
            Log.e(TAG, "Not a Home page. Skip filtering! Url: " + url + " Post data: " + postData);
            return null;
        }

        // TEST ONLY!!!
        //InputStream urlData = getTestResponse();

        InputStream urlData = postJsonData(url, postData, mHeaders);

        WebResourceResponse response = null;

        if (Log.getLogType().equals(Log.LOG_TYPE_FILE) && urlData != null) {
            String content = Helpers.toString(urlData);
            Log.d(TAG, "Url: " + url + ". Post Data: " + postData + ". Response: " + content);
            urlData = Helpers.toStream(content);
        }

        if (urlData != null) {
            Log.d(TAG, "Searching and removing tv masthead section...");
            JsonBrowseParser browseParser = JsonBrowseParser.parse(urlData);

            if (browseParser.removeMastHead()) {
                Log.d(TAG, "Success. TV masthead has been removed.");
            } else {
                if (Log.getLogType().equals(Log.LOG_TYPE_FILE)) {
                    Log.d(TAG, "Oops. Response doesn't contain MustHead section. Url: " + url + ". Post Data: " + postData + ". Response: " + Helpers.toString(browseParser.toStream()));
                } else {
                    Log.d(TAG, "Oops. Response doesn't contain MustHead section. Url: " + url + ". Post Data: " + postData);
                }
            }

            response = createResponse("application/json", null, browseParser.toStream());
        } else {
            Log.e(TAG, "Error. Response in empty. Url: " + url + ". Post Data: " + postData);
        }

        return response;
    }

    private WebResourceResponse filterLongPressVideoMenu(String url) {
        if (!mIsEnableVideoMenu) { // rich menu not enabled
            return null;
        }

        String postData = mPrefs.getPostData();

        if (postData == null) {
            Log.e(TAG, "Post body is empty! Skipping url: " + url);
            return null;
        }

        WebResourceResponse result = null;

        InputStream dataStream = postJsonData(url, postData, mHeaders);

        if (dataStream != null) {
            result = createResponse("application/json", null, dataStream);
        }

        return result;
    }

    private InputStream getTestResponse() {
        return AssetHelper.getAsset(mContext, "tests/tv_masthead2_origin.json");
    }
}
