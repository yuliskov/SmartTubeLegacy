package com.liskovsoft.browser.custom;

import java.util.Map;

public class PageDefaults {
    private final String mUrl;
    private final Map<String, String> mHeaders;
    private final PageLoadHandler mHandler;
    private final ControllerEventHandler mPostProcessor;

    public PageDefaults(String url, Map<String, String> headers, PageLoadHandler handler) {
        this(url, headers, handler, null);
    }

    public PageDefaults(String url, Map<String, String> headers, PageLoadHandler handler, ControllerEventHandler postProcessor) {
        mUrl = url;
        mHeaders = headers;
        mHandler = handler;
        mPostProcessor = postProcessor;
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

    public ControllerEventHandler getPostProcessor() {
        return mPostProcessor;
    }
}
