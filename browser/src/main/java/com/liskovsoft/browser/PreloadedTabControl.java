package com.liskovsoft.browser;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class to manage the controlling of preloaded tab.
 */
public class PreloadedTabControl {
    private static final boolean LOGD_ENABLED = com.liskovsoft.browser.Browser.LOGD_ENABLED;
    private static final String LOGTAG = "PreloadedTabControl";

    final Tab mTab;
    private String mLastQuery;
    private boolean mDestroyed;

    public PreloadedTabControl(Tab t) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "PreloadedTabControl.<init>");
        mTab = t;
    }

    public void setQuery(String query) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "Cannot set query: no searchbox interface");
    }

    public boolean searchBoxSubmit(final String query,
            final String fallbackUrl, final Map<String, String> fallbackHeaders) {
        return false;
    }

    public void searchBoxCancel() {
    }

    public void loadUrlIfChanged(String url, Map<String, String> headers) {
        String currentUrl = mTab.getUrl();
        if (!TextUtils.isEmpty(currentUrl)) {
            try {
                // remove fragment:
                currentUrl = Uri.parse(currentUrl).buildUpon().fragment(null).build().toString();
            } catch (UnsupportedOperationException e) {
                // carry on
            }
        }
        if (LOGD_ENABLED) Log.d(LOGTAG, "loadUrlIfChanged\nnew: " + url + "\nold: " +currentUrl);
        if (!TextUtils.equals(url, currentUrl)) {
            loadUrl(url, headers);
        }
    }

    public void loadUrl(String url, Map<String, String> headers) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "Preloading " + url);
        mTab.loadUrl(url, headers);
    }

    public void destroy() {
        if (LOGD_ENABLED) Log.d(LOGTAG, "PreloadedTabControl.destroy");
        mDestroyed = true;
        mTab.destroy();
    }

    public Tab getTab() {
        return mTab;
    }

}
