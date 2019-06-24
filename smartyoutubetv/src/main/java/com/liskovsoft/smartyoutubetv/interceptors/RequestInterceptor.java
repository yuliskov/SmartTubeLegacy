package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestInterceptor {
    private static final String TAG = RequestInterceptor.class.getSimpleName();
    private final Context mContext;
    private SmartPreferences mPrefs;
    /**
     * Common request headers
     */
    private Map<String, String> mCachedHeaders;

    public abstract boolean test(String url);
    public abstract WebResourceResponse intercept(String url);

    public RequestInterceptor(Context context) {
        mContext = context;

        if (mContext != null) {
            mPrefs = SmartPreferences.instance(context);
        }
    }

    private String getMimeType(MediaType contentType) {
        String type = contentType.type();
        String subtype = contentType.subtype();
        return String.format("%s/%s", type, subtype);
    }

    private String getCharset(MediaType contentType) {
        if (contentType.charset() == null) {
            return null;
        }
        return contentType.charset().name();
    }

    protected WebResourceResponse createResponse(MediaType mediaType, InputStream is) {
        WebResourceResponse resourceResponse = new WebResourceResponse(
                getMimeType(mediaType),
                getCharset(mediaType),
                is
        );
        return resourceResponse;
    }

    protected WebResourceResponse prependResponse(String url, InputStream toPrepend) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);

        if (response == null) {
            Log.e(TAG, "Oops... unknown error inside OkHttpHelpers: " + url);
            return null;
        }

        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(toPrepend, responseStream));
    }

    protected WebResourceResponse appendResponse(String url, InputStream toAppend) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);
        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(responseStream, toAppend));
    }

    protected InputStream getUrlData(String url) {
        Response response;

        if (mPrefs != null) {
            response = OkHttpHelpers.doGetOkHttpRequest(url, prepareHeaders());
        } else {
            response = OkHttpHelpers.doGetOkHttpRequest(url);
        }

        return response == null ? null : response.body().byteStream();
    }

    private Map<String, String> prepareHeaders() {
        if (mCachedHeaders != null) {
            return mCachedHeaders;
        }

        Map<String, String> headers = new HashMap<>();
        String rawCookie = mPrefs.getCookieHeader();
        String authorization = mPrefs.getAuthorizationHeader();

        headers.put("Referer", "https://www.youtube.com/tv");
        headers.put("x-client-data", "CJW2yQEIo7bJAQjBtskBCKmdygEIqKPKAQi/p8oBCOKoygE=");
        headers.put("x-youtube-client-name", "TVHTML5");
        headers.put("x-youtube-client-version", "6.20180913");
        headers.put("x-youtube-page-cl", "251772599");
        headers.put("x-youtube-page-label", "youtube.ytfe.desktop_20190605_0_RC0");
        headers.put("x-youtube-utc-offset", "180");
        headers.put("User-Agent", new UserAgentManager().getUA());

        if (rawCookie != null) {
            headers.put("Cookie", rawCookie);
        }

        if (authorization != null) {
            headers.put("Authorization", authorization);
        }

        // don't cache not fully initialized headers
        if (rawCookie == null || authorization == null) {
            return headers;
        }

        mCachedHeaders = headers;

        return headers;
    }
}
