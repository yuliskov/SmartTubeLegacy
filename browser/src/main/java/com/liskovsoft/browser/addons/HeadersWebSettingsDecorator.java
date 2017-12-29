package com.liskovsoft.browser.addons;

import android.webkit.WebSettings;

import java.util.Map;
import java.util.TreeMap;

public class HeadersWebSettingsDecorator extends WebSettingsDecorator {
    private final Map<String, String> mHeaders;

    private final static String USER_AGENT_HEADER = "User-Agent";

    public HeadersWebSettingsDecorator(Map<String, String> headers, WebSettings settings) {
        super(settings);
        mHeaders = convertToCaseInsensitiveMap(headers);
    }

    private Map<String, String> convertToCaseInsensitiveMap(Map<String, String> headers) {
        if (headers == null) return null;
        Map<String, String> nodeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        nodeMap.putAll(headers);
        return nodeMap;
    }

    private boolean setUserAgentFromHeaders() {
        if (mHeaders == null) return false;
        if (!mHeaders.containsKey(USER_AGENT_HEADER)) return false;

        mWebSettings.setUserAgentString(mHeaders.get(USER_AGENT_HEADER));
        return true;
    }

    @Override
    public void setUserAgentString(String ua) {
        if (setUserAgentFromHeaders()) {
            return;
        }

        super.setUserAgentString(ua);
    }

    @Override
    public void setDefaultTextEncodingName(String encoding) {
        super.setDefaultTextEncodingName(encoding);
    }
}
