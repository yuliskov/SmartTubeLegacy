package com.liskovsoft.smartyoutubetv.misc;

import java.util.HashMap;
import java.util.Map;

public class UserAgentManager {
    public enum UAVersion {V1, V2}
    private static final String USER_AGENT = "user-agent";
    public final static String[] LG_SMART_TV = {
            // 2013
            "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)",
            // Voice button enabled!!!
            "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 43LK5760PTA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 43LK5760PTA, wired)"
    };
    public final static String[] SAMSUNG_SMART_TV = {
            // Another old ua
            "Mozilla/5.0 (SMART-TV; Linux; Tizen 2.4.0) AppleWebkit/538.1 (KHTML, like Gecko) SamsungBrowser/1.1 TV Safari/538.1",
            // Old UI (no exit dialog)
            "Mozilla/5.0 (Linux; Tizen 2.3) AppleWebKit/538.1 (KHTML, like Gecko)Version/2.3 TV Safari/538.1",
            "Mozilla/5.0 (Linux; Tizen 2.3; SmartHub; SMART-TV; SmartTV; U; Maple2012) AppleWebKit/538.1+ (KHTML, like Gecko) TV Safari/538.1+",
            "Mozilla/5.0 (SMART-TV; Linux; Tizen 5.0) AppleWebKit/537.36 (KHTML; like Gecko) SamsungBrowser/2.2 Chrome/63.0.3239.84 TV Safari/537.36",
            // 2019
            "Mozilla/5.0 (SMART-TV; LINUX; Tizen 5.0) AppleWebKit/537.36 (KHTML, like Gecko) Version/5.0 TV Safari/537.36"
    };
    public final static String[] PHILIPS_SMART_TV = {
            // Old UI (with exit dialog)
            "Opera/9.80 (Linux armv7l; HbbTV/1.2.1 (; Philips; 40HFL5010T12; ; PHILIPSTV; CE-HTML/1.0 NETTV/4.4.1 SmartTvA/3.0.0 Firmware/004.002.036.135 (PhilipsTV, 3.1.1,)en) ) Presto/2.12.407 Version/12.50"
    };
    /**
     * User agent from the official YouTube for TV app
     */
    public final static String[] COBALT_CLIENT = {
            "Mozilla/5.0 (DirectFB; Linux x86_64) Cobalt/40.13031-qa (unlike Gecko) Starboard/1"
    };
    private final HashMap<String, String> mHeaders;

    public UserAgentManager() {
        mHeaders = new HashMap<>();
        mHeaders.put(USER_AGENT, getUA());
    }

    public String getUA() {
        return SAMSUNG_SMART_TV[0];
    }

    // used inside JSDirs.java
    public UAVersion getUAVersion() {
        return UAVersion.V1;
    }

    public Map<String, String> getUAHeaders() {
        return mHeaders;
    }
}
