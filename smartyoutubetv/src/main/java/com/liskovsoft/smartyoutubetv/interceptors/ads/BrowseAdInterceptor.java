package com.liskovsoft.smartyoutubetv.interceptors.ads;

import android.content.Context;
import android.webkit.WebResourceResponse;

import com.liskovsoft.m3uparser.core.utils.Strings;
import com.liskovsoft.sharedutils.helpers.AssetHelper;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.interceptors.RequestInterceptor;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import okhttp3.MediaType;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Try to get rich response from browse request.<br/>
 * Such response contains video long click menu items.<br/>
 * Main trick is to change user agent to Cobalt.<br/>
 * NOTE: set user agent via js produce next error: Refused to set unsafe header "User-Agent"
 */
public class BrowseAdInterceptor extends RequestInterceptor {
    private static final String TAG = BrowseAdInterceptor.class.getSimpleName();
    private static final String BROWSE_URL = "/youtubei/v1/browse";
    private static final String HOME_ID = "\"browseId\":\"default\"";
    private static final String TOPICS_ID = "\"browseId\":\"FEtopics\"";
    private static final String SUBS_ID = "\"browseId\":\"FEsubscriptions\"";
    private static final String LIBRARY_ID = "\"browseId\":\"FEmy_youtube\"";
    private static final String CONTINUATION_ID = "\"continuation\":";
    private static final String BROWSE_ID = "\"browseId\":";
    private static final String BROWSE_NEXT_URL = "/youtubei/v1/next";
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private Map<String, String> mHeaders;
    private boolean mIsXWalk;
    private boolean mIsAdBlockEnabled;
    private boolean mIsEnableVideoMenu;
    private boolean mIsCompatibleSettings;

    public BrowseAdInterceptor(Context context) {
        super(context);
        mContext = context;
        mPrefs = CommonApplication.getPreferences();
        mIsXWalk = SmartUtils.isXWalk(mContext);
        mIsAdBlockEnabled = mPrefs.isAdBlockEnabled();
        mIsEnableVideoMenu = mPrefs.getEnableVideoMenu();
        mIsCompatibleSettings = mIsEnableVideoMenu || (mIsAdBlockEnabled && !mIsXWalk);
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
        return mIsCompatibleSettings && (url != null && url.contains(BROWSE_URL));
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

        if (Strings.isNullOrEmpty(postData)) {
            Log.e(TAG, "Post body is empty! Skipping url: " + url);
            return null;
        }

        boolean isHome = postData.contains(HOME_ID) || postData.contains(TOPICS_ID);

        if (!isHome) {
            Log.e(TAG, "Not a Home page. Skip filtering! Url: " + url + " Post data: " + postData);
            return null;
        }

        InputStream urlData = postJsonData(url, postData, mHeaders);

        if (urlData == null) {
            Log.e(TAG, "Error. Response is empty. Url: " + url + ". Post Data: " + postData);
            return null;
        }

        JsonBrowseAdParser parser = new JsonBrowseAdParser(urlData);

        if (Log.getLogType().equals(Log.LOG_TYPE_FILE)) {
            Log.d(TAG, "Searching and removing tv masthead section..." + Helpers.toString(parser.toStream()));
        }


        if (parser.removeMastHead()) {
            Log.d(TAG, "Success. Masthead has been removed!");
            return createResponse(MediaType.parse("application/json"), parser.toStream());
        }

        return null;
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
