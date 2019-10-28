package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.webkit.CookieManager;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class ForceWebUIHandler extends StateHandler {
    private static final String NEW_UI_COOKIE = "VISITOR_INFO1_LIVE=cp3UVuEA3l4; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2022 15:42:26 GMT; httponly";
    private static final String OLD_UI_COOKIE = "VISITOR_INFO1_LIVE=ErVksiAQ6pg; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2022 15:42:26 GMT; httponly";

    public ForceWebUIHandler(Activity context) {
    }

    @Override
    public void onInit() {
        CookieManager.getInstance().setCookie("https://www.youtube.com", OLD_UI_COOKIE);
    }
}
