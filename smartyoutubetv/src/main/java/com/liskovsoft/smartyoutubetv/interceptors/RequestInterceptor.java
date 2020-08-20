package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.HeaderManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyUrlEncodedQueryString;
import okhttp3.MediaType;
import okhttp3.Response;

public abstract class RequestInterceptor {

    private static final String TAG = RequestInterceptor.class.getSimpleName();

    private static final WebResourceResponse EMPTY_RESPONSE = new WebResourceResponse(null, null, null);

    private final Context mContext;
    private final HeaderManager mManager;

    public abstract boolean test(String url);

    public abstract WebResourceResponse intercept(String url);

    public RequestInterceptor(Context context) {
        mContext = context;
        mManager = new HeaderManager(context);
    }

    protected String getMimeType(MediaType contentType) {
        String type = contentType.type();
        String subtype = contentType.subtype();
        return String.format("%s/%s", type, subtype);
    }

    protected String getCharset(MediaType contentType) {
        if (contentType.charset() == null) {
            return null;
        }
        return contentType.charset().name();
    }

    protected WebResourceResponse createResponse(String mediaType, String charset, InputStream is) {
        return new WebResourceResponse(mediaType, charset, is);
    }

    protected WebResourceResponse createResponse(MediaType mediaType, InputStream is) {
        WebResourceResponse resourceResponse = new WebResourceResponse(
                getMimeType(mediaType),
                getCharset(mediaType),
                is
        );
        return resourceResponse;
    }

    protected WebResourceResponse wrapResponse(String url, InputStream before, InputStream after) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);

        if (response == null) {
            MessageHelpers.showLongMessageEndPause(mContext, R.string.fix_clock_msg);
        }

        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(Helpers.appendStream(before, responseStream), after));
    }

    protected WebResourceResponse prependResponse(String url, InputStream toPrepend) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);

        if (response == null) {
            MessageHelpers.showLongMessageEndPause(mContext, R.string.fix_clock_msg);
        }

        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(toPrepend, responseStream));
    }

    protected WebResourceResponse appendResponse(String url, InputStream toAppend) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);

        if (response == null) {
            MessageHelpers.showLongMessageEndPause(mContext, R.string.fix_clock_msg);
        }

        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(responseStream, toAppend));
    }

    protected InputStream getUrlData(String url) {
        return getUrlData(url, mManager.getHeaders());
    }

    protected InputStream getUrlData(String url, Map<String, String> headers) {
        InputStream result = null;

        Response response = OkHttpHelpers.doGetOkHttpRequest(url, headers);

        if (response != null && response.body() != null) {
            result = response.body().byteStream();
        }

        return result;
    }

    protected Response getResponse(String url) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);

        if (response == null) {
            MessageHelpers.showLongMessageEndPause(mContext, R.string.fix_clock_msg);
        }

        return response;
    }

    protected WebResourceResponse emptyResponse() {
        return EMPTY_RESPONSE;
    }

    protected InputStream postJsonData(String url, String body) {
        return postJsonData(url, body, null);
    }

    protected InputStream postJsonData(String url, String body, Map<String, String> headers) {
        InputStream result = null;

        HashMap<String, String> resultHeaders = mManager.getHeaders();

        if (headers != null) {
            resultHeaders.putAll(headers);
        }

        Response response = OkHttpHelpers.doPostOkHttpRequest(url, resultHeaders, body, "application/json");

        if (response != null && response.body() != null) {
            result = response.body().byteStream();
        }

        return result;
    }

    protected WebResourceResponse postFormData(String url, String body) {
        Response response = OkHttpHelpers.doPostOkHttpRequest(url, mManager.getHeaders(), body, "application/x-www-form-urlencoded");

        return createResponse(response);
    }

    protected WebResourceResponse createResponse(Response response) {
        WebResourceResponse result = null;

        if (response != null) {
            if (response.body() != null) {
                result = createResponse(response.body().contentType(), response.body().byteStream());
            } else {
                result = emptyResponse();
            }
        }

        return result;
    }

    protected WebResourceResponse filterVideoInfoResponse(String url) {
        WebResourceResponse result = null;

        if (url != null) {
            InputStream urlData = getUrlData(url);
            String response = Helpers.toString(urlData);

            if (response != null) {
                String filteredResponse = response.replace("%2C%22adPlacements%22%3A%5B%7B%22", "%2C%22removedAdPlacements%22%3A%5B%7B%22");
                result = createResponse(MediaType.parse("application/x-www-form-urlencoded"), Helpers.toStream(filteredResponse));
            }
        }

        return result;
    }
}
