package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceResponse;

/**
 * The {@link FileExtensionInterceptor} class represent request interceptor by file extensions.
 * <p>
 * Attention! This class cannot be used in {@link RequestInterceptorProcessor} list, because
 * return {@code null} as processing result.
 */
public class FileExtensionInterceptor extends RequestInterceptor {

    public FileExtensionInterceptor(Context context) {
        super(context);
    }

    @Override
    public boolean test(String url) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(url);
        if (ext.isEmpty()) {
            return false;
        }
        return ext.equals("jpg")
                || ext.equals("png")
                || ext.equals("ico")
                || ext.equals("ttf");
    }

    @Override
    public WebResourceResponse intercept(String url) {
        return null;
    }
}
