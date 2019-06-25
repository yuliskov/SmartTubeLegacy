package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.HashMap;

public class HeaderManager {
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private HashMap<String, String> mHeaders;

    public HeaderManager(Context context) {
        mContext = context;
        mPrefs = SmartPreferences.instance(mContext);

        initHeaders();
    }

    public HashMap<String, String> getHeaders() {
        updateHeaders();
        return mHeaders;
    }

    private void initHeaders() {
        mHeaders = new HashMap<>();

        mHeaders.put("Referer", "https://www.youtube.com/tv");
        mHeaders.put("x-client-data", "CJW2yQEIo7bJAQjBtskBCKmdygEIqKPKAQi/p8oBCOKoygE=");
        mHeaders.put("x-youtube-client-name", "TVHTML5");
        mHeaders.put("x-youtube-client-version", "6.20180913");
        mHeaders.put("x-youtube-page-cl", "251772599");
        mHeaders.put("x-youtube-page-label", "youtube.ytfe.desktop_20190605_0_RC0");
        mHeaders.put("x-youtube-utc-offset", "180");
        mHeaders.put("User-Agent", new UserAgentManager().getUA());
        mHeaders.put("Accept-Language", new LangUpdater(mContext).getPreferredBrowserLocale());
    }

    private void updateHeaders() {
        String authorization = mPrefs.getAuthorizationHeader(); // DON'T CACHE: value changed over time

        if (authorization != null) {
            mHeaders.put("Authorization", authorization);
        }

        String cookies = mPrefs.getCookieHeader(); // DON'T CACHE: value changed over time

        if (cookies != null) {
            mHeaders.put("Cookie", cookies);
        }
    }
}
