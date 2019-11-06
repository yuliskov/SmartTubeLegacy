package com.liskovsoft.smartyoutubetv.interceptors.scripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;

/**
 *  Legacy devices
 */
public class LegacyMainScriptManagerInterceptor extends MainScriptManagerInterceptor {
    private static final String[] FIRST_SCRIPT_REGEX = {"youtube.com/s/_/kabuki_legacy/_/js/.*/m=base$"};
    private static final String[] MAIN_STYLE_REGEX = {"youtube.com/s/_/kabuki_legacy/_/ss/.*"};

    public LegacyMainScriptManagerInterceptor(Context context) {
        super(context);
    }

    @Override
    protected boolean isFirstScript(String url) {
        return super.isFirstScript(url) || Helpers.matchAll(url, FIRST_SCRIPT_REGEX);
    }

    @Override
    protected boolean isStyle(String url) {
        return super.isStyle(url) || Helpers.matchAll(url, MAIN_STYLE_REGEX);
    }
}
