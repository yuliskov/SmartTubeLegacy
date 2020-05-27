package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ForceNewUIHandler extends StateHandler {
    private static final String TAG = ForceNewUIHandler.class.getSimpleName();
    private static final String NEW_UI_COOKIE = "VISITOR_INFO1_LIVE=sw1lN1SY3ts; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String OLD_UI_COOKIE = "VISITOR_INFO1_LIVE=gZAMS9rWkqc; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String RESET_UI_COOKIE = "VISITOR_INFO1_LIVE=; path=/; domain=.youtube.com; expires=Sat, 25-Apr-1971 15:42:26 GMT; httponly";
    private static final String COOKIE_URL = "https://www.youtube.com";
    private final Activity mContext;

    public ForceNewUIHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onUpdate() {
        SmartUtils.setSecureCookie(RESET_UI_COOKIE, COOKIE_URL, mContext);
    }
}
