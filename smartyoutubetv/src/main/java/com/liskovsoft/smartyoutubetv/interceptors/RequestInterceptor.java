package com.liskovsoft.smartyoutubetv.interceptors;

import android.webkit.WebResourceResponse;
import okhttp3.MediaType;

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
}
