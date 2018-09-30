package com.liskovsoft.browser.addons;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.webkit.WebResourceRequest;

import java.util.Map;

public class MyWebResourceRequest {
    private Uri mUri;
    private boolean mIsForMainFrame;
    private boolean mIsRedirect;
    private boolean mHasGesture;
    private String mMethod;
    private Map<String, String> mRequestHeaders;

    public static MyWebResourceRequest fromUrl(String url) {
        MyWebResourceRequest result = new MyWebResourceRequest();
        result.mUri = Uri.parse(url);

        return result;
    }

    @TargetApi(21)
    public static MyWebResourceRequest fromWebResourceRequest(WebResourceRequest request) {
        MyWebResourceRequest result = new MyWebResourceRequest();
        result.mUri = request.getUrl();
        result.mIsForMainFrame = request.isForMainFrame();
        if (VERSION.SDK_INT >= 24) {
            result.mIsRedirect = request.isRedirect();
        }
        result.mHasGesture = request.hasGesture();
        result.mMethod = request.getMethod();
        result.mRequestHeaders = request.getRequestHeaders();

        return result;
    }

    private MyWebResourceRequest() {
        
    }

    public Uri getUrl() {
        return mUri;
    }

    public boolean isForMainFrame() {
        return mIsForMainFrame;
    }

    public boolean isRedirect() {
        return mIsRedirect;
    }

    public boolean hasGesture() {
        return mHasGesture;
    }

    public String getMethod() {
        return mMethod;
    }

    public Map<String, String> getRequestHeaders() {
        return mRequestHeaders;
    }
}
