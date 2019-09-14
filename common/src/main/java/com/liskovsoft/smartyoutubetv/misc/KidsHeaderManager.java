package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.HashMap;

public class KidsHeaderManager {
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private HashMap<String, String> mHeaders;
    // this values will be changed over time
    private static final String CLIENT_VERSION = "6.20180913";
    private static final String PAGE_CL = "260482851";
    private static final String PAGE_LABEL = "youtube.ytfe.desktop_20190729_3_RC0";
    private static final String AD_SIGNALS = "dt=1564772449513&flash=0&frm&u_tz=180&u_his=50&u_java=true&u_h=540&u_w=960&u_ah=540&u_aw=960&u_cd=32" +
            "&u_nplug&u_nmime&bc=1&bih=540&biw=960&brdim=0%2C0%2C0%2C0%2C960%2C0%2C960%2C540%2C960%2C540&vis=1&wgl=true&ca_type=image";

    public KidsHeaderManager(Context context) {
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

        mHeaders.put("Referer", "https://www.youtube.com/tv/kids");
        mHeaders.put("User-Agent", new UserAgentManager().getUA());
        mHeaders.put("Accept-Language", new LangUpdater(mContext).getPreferredBrowserLocale());

        mHeaders.put("X-YouTube-Client-Name", "TVHTML5");
        mHeaders.put("X-YouTube-Utc-Offset", "180");

        // this headers will be changed over time
        mHeaders.put("X-YouTube-Client-Version", CLIENT_VERSION);
        mHeaders.put("X-YouTube-Page-CL", PAGE_CL);
        mHeaders.put("X-YouTube-Page-Label", PAGE_LABEL);
        mHeaders.put("X-YouTube-Ad-Signals", AD_SIGNALS);
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
