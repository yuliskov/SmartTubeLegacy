package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.webscripts.MainScriptManager;
import com.liskovsoft.smartyoutubetv.webscripts.ScriptManager;

public class ScriptManagerInterceptor extends RequestInterceptor {
    private final Context mContext;

    // script app from the corresponding app versions
    private static final String[] FIRST_SCRIPT_NAME = {"live.js"};
    private static final String[] LAST_SCRIPT_NAME = {"tv-player.js", "tv-player-ias.js"};
    private static final String[] LAST_STYLE_NAME = {"airstream-prod-css.css"};
    
    private final ScriptManager mManager;

    public ScriptManagerInterceptor(Context context) {
        mContext = context;
        mManager = new MainScriptManager(context);
    }

    @Override
    public boolean test(String url) {
        if (Helpers.endsWith(url, FIRST_SCRIPT_NAME)) {
            return true;
        }

        if (Helpers.endsWith(url, LAST_SCRIPT_NAME)) {
            return true;
        }

        if (Helpers.endsWith(url, LAST_STYLE_NAME)) {
            return true;
        }

        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (Helpers.endsWith(url, FIRST_SCRIPT_NAME)) {
            return prependResponse(url, mManager.getOnInitScripts());
        }

        if (Helpers.endsWith(url, LAST_SCRIPT_NAME)) {
            return appendResponse(url, mManager.getOnLoadScripts());
        }

        if (Helpers.endsWith(url, LAST_STYLE_NAME)) {
            return appendResponse(url, mManager.getStyles());
        }

        return null;
    }

}
