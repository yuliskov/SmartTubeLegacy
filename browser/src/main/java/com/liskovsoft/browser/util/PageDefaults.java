package com.liskovsoft.browser.util;

import java.util.Map;

public class PageDefaults {
    private final String mUrl;
    private final Map<String, String> mHeaders;
    private final PageLoadHandler mHandler;

    public PageDefaults(String url, Map<String, String> headers, PageLoadHandler handler) {
        mUrl = url;
        mHeaders = headers;
        mHandler = handler;
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public PageLoadHandler getHandler() {
        return mHandler;
    }
}
