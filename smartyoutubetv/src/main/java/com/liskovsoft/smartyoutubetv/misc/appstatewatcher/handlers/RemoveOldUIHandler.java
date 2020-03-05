package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class RemoveOldUIHandler extends StateHandler {
    private static final String TAG = RemoveOldUIHandler.class.getSimpleName();
    private static final String NEW_UI_COOKIE = "VISITOR_INFO1_LIVE=cp3UVuEA3l4; path=/; domain=.youtube.com; expires=Sat, 25-Apr-1971 15:42:26 GMT; httponly";
    private static final String COOKIE_URL = "https://www.youtube.com";
    private final Activity mContext;

    public RemoveOldUIHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onInit() {
        SmartUtils.setSecureCookie(NEW_UI_COOKIE, COOKIE_URL, mContext);
    }
}
