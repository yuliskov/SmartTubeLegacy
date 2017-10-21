package com.liskovsoft.browser.custom;

import android.os.Bundle;
import com.liskovsoft.browser.custom.events.ControllerEventHandler;
import com.liskovsoft.browser.custom.events.PageLoadHandler;

import java.util.Map;

public class PageDefaults {
    private final String mUrl;
    private final Map<String, String> mHeaders;
    private final PageLoadHandler mHandler;
    private final ControllerEventHandler mPostProcessor;

    public PageDefaults() {
        this(null, null, null, null);
    }

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
        if (mPostProcessor == null) {
            return getEmptyHandler();
        }
        return mPostProcessor;
    }

    private ControllerEventHandler getEmptyHandler() {
        return new ControllerEventHandler() {
            @Override
            public void onControllerStart() {
                /* NOP */
            }

            @Override
            public void beforeSaveInstanceState(Bundle state) {
                /* NOP */
            }

            @Override
            public void beforeRestoreInstanceState(Bundle state) {
                /* NOP */
            }
        };
    }
}
