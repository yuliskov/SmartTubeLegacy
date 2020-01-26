package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.HashMap;

public class HeaderManager {
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private HashMap<String, String> mHeaders;
    private String mRootUrl = "https://www.youtube.com/tv";

    // this values will be changed over time
    private static final String AD_SIGNALS =
            "dt=1580080155215&flash=0&frm&u_tz=120&u_his=7&u_java&u_h=540&u_w=960&u_ah=540&u_aw=960&u_cd=24&u_nplug&u_nmime&bc=31&bih=540&biw=960&brdim=0%2C0%2C0%2C0%2C960%2C0%2C960%2C540%2C960%2C540&vis=1&wgl=true&ca_type=image";
    private static final String CLIENT_VERSION = "6.20180913";
    private static final String PAGE_CL = "291117384";
    private static final String PAGE_LABEL = "youtube.ytfe.desktop_20200122_2_RC1";

    private static final String ACCEPT_COMPRESSION = "gzip, deflate";
    private static final String ACCEPT_PATTERN = "*/*";
    private static final String ORIGINAL_PACKAGE = "com.google.android.youtube.tv";

    public HeaderManager(Context context) {
        mContext = context;
        mPrefs = SmartPreferences.instance(mContext);
        mRootUrl = CommonParams.instance(mContext).getMainPageUrl();

        initHeaders();
    }

    public HashMap<String, String> getHeaders() {
        updateHeaders();
        return mHeaders;
    }

    private void initHeaders() {
        mHeaders = new HashMap<>();

        mHeaders.put("Referer", mRootUrl);
        mHeaders.put("User-Agent", new UserAgentManager().getUA());
        mHeaders.put("Accept-Language", new LangUpdater(mContext).getPreferredBrowserLocale());
        mHeaders.put("Accept-Encoding", ACCEPT_COMPRESSION);
        mHeaders.put("Accept", ACCEPT_PATTERN);

        mHeaders.put("X-Requested-With", ORIGINAL_PACKAGE);
        mHeaders.put("X-YouTube-Client-Name", "TVHTML5");
        mHeaders.put("X-YouTube-Utc-Offset", "120");
        mHeaders.put("X-Youtube-Time-Zone", "Europe/Athens");

        // this headers will be changed over time
        mHeaders.put("X-YouTube-Ad-Signals", AD_SIGNALS);
        mHeaders.put("X-YouTube-Client-Version", CLIENT_VERSION);
        mHeaders.put("X-YouTube-Page-CL", PAGE_CL);
        mHeaders.put("X-YouTube-Page-Label", PAGE_LABEL);
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
