package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ForceNewUIHandler extends StateHandler {
    private static final String TAG = ForceNewUIHandler.class.getSimpleName();
    private static final String NEW_UI_COOKIE3 = "VISITOR_INFO1_LIVE=VsVpAXmklX4; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String NEW_UI_COOKIE2 = "VISITOR_INFO1_LIVE=xcc12hbEjFM; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String NEW_UI_COOKIE = "VISITOR_INFO1_LIVE=cp3UVuEA3l4; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String OLD_UI_COOKIE = "VISITOR_INFO1_LIVE=ErVksiAQ6pg; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String COOKIE_URL = "https://www.youtube.com";
    private final Activity mContext;

    public ForceNewUIHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onInit() {
        if (CommonApplication.getPreferences().isUserLogged()) {
            SmartUtils.setSecureCookie(NEW_UI_COOKIE3, COOKIE_URL, mContext);
        }
    }
}
