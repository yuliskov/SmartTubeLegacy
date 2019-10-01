package com.liskovsoft.smartyoutubetv.misc;

import com.liskovsoft.smartyoutubetv.CommonApplication;

import java.util.HashMap;
import java.util.Map;

public class UserAgentManager {
    public static final String USER_AGENT = "user-agent";
    /**
     * New UI
     */
    private final static String LG_SMART_TV = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)";
    /**
     * New UI (Voice button enabled)
     */
    private final static String LG_SMART_TV_VOICE_BUTTON_ENABLED = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 43LK5760PTA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 43LK5760PTA, wired)";
    /**
     * Old UI (no exit dialog)
     */
    private final static String SAMSUNG_SMART_TV = "Mozilla/5.0 (Linux; Tizen 2.3) AppleWebKit/538.1 (KHTML, like Gecko)Version/2.3 TV Safari/538.1";
    private final static String SAMSUNG_SMART_TV_2 = "Mozilla/5.0 (Linux; Tizen 2.3; SmartHub; SMART-TV; SmartTV; U; Maple2012) AppleWebKit/538.1+ (KHTML, like Gecko) TV Safari/538.1+";
    /**
     * Old UI (with exit dialog)
     */
    private final static String PHILIPS_SMART_TV = "Opera/9.80 (Linux armv7l; HbbTV/1.2.1 (; Philips; 40HFL5010T12; ; PHILIPSTV; CE-HTML/1.0 NETTV/4.4.1 SmartTvA/3.0.0 Firmware/004.002.036.135 (PhilipsTV, 3.1.1,)en) ) Presto/2.12.407 Version/12.50";
    /**
     * User agent from the official YouTube for TV app
     */
    private final static String COBALT_CLIENT = "Mozilla/5.0 (DirectFB; Linux x86_64) Cobalt/40.13031-qa (unlike Gecko) Starboard/1";
    private final HashMap<String, String> mHeaders;
    private final boolean mEnableAnimations;

    public UserAgentManager() {
        mEnableAnimations = CommonApplication.getPreferences().getEnableAnimatedPreviews();
        mHeaders = new HashMap<>();
        mHeaders.put(USER_AGENT, getUA());
    }

    public String getUA() {
        //if (mEnableAnimations) {
        //    return COBALT_CLIENT;
        //}

        return LG_SMART_TV_VOICE_BUTTON_ENABLED;
    }

    public Map<String, String> getUAHeaders() {
        return mHeaders;
    }
}
