package com.liskovsoft.smartyoutubetv.misc;
import android.content.Context;

public class UAManager {
    /**
     * New UI
     */
    private final static String LG_SMART_TV = "Mozilla/5.0 (Unknown; Linux armv7l) AppleWebKit/537.1+ (KHTML, like Gecko) Safari/537.1+ LG Browser/6.00.00(+mouse+3D+SCREEN+TUNER; LGE; 42LA660S-ZA; 04.25.05; 0x00000001;); LG NetCast.TV-2013 /04.25.05 (LG, 42LA660S-ZA, wired)";
    /**
     * Old UI (no exit dialog)
     */
    private final static String SAMSUNG_SMART_TV = "Mozilla/5.0 (Linux; Tizen 2.3) AppleWebKit/538.1 (KHTML, like Gecko)Version/2.3 TV Safari/538.1";
    private final static String SAMSUNG_SMART_TV_2 = "Mozilla/5.0 (Linux; Tizen 2.3; SmartHub; SMART-TV; SmartTV; U; Maple2012) AppleWebKit/538.1+ (KHTML, like Gecko) TV Safari/538.1+";
    /**
     * Old UI (with exit dialog)
     */
    private final static String PHILIPS_SMART_TV = "Opera/9.80 (Linux armv7l; HbbTV/1.2.1 (; Philips; 40HFL5010T12; ; PHILIPSTV; CE-HTML/1.0 NETTV/4.4.1 SmartTvA/3.0.0 Firmware/004.002.036.135 (PhilipsTV, 3.1.1,)en) ) Presto/2.12.407 Version/12.50";
    // private final SmartPreferences mPrefs;

    public UAManager(Context ctx) {
        // mPrefs = SmartPreferences.instance(ctx);
    }

    public String getUA() {
        //boolean oldUI = mPrefs.getBootstrapOldUI();
        //
        //return oldUI ? sPhilipsSmartTV : sLGSmartTV;

        return LG_SMART_TV;
    }
}
