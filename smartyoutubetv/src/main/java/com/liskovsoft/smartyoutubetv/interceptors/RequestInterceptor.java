package com.liskovsoft.smartyoutubetv.interceptors;

import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.InputStream;

public abstract class RequestInterceptor {
    public abstract boolean test(String url);
    public abstract WebResourceResponse intercept(String url);

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
        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(toPrepend, responseStream));
    }

    protected WebResourceResponse appendResponse(String url, InputStream toAppend) {
        Response response = OkHttpHelpers.doOkHttpRequest(url);
        InputStream responseStream = response.body().byteStream();
        return createResponse(response.body().contentType(), Helpers.appendStream(responseStream, toAppend));
    }
}
