package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.webkit.WebResourceResponse;
import com.liskovsoft.smartyoutubetv.webscripts.MainScriptManager;
import com.liskovsoft.smartyoutubetv.webscripts.ScriptManager;

public class ScriptManagerInterceptor extends RequestInterceptor {
    private final Context mContext;
    private final String[] mAppScripts = {
            "live.js",
            "app-prod.js",
            "tv-player.js"
    };
    private final String[] mAppStyles = {
            "airstream-prod-css.css"
    };
    private final ScriptManager mManager;

    public ScriptManagerInterceptor(Context context) {
        mContext = context;
        mManager = new MainScriptManager(context);
    }

    @Override
    public boolean test(String url) {
        for (String scr : mAppScripts) {
            if (url.endsWith(scr)) {
                return true;
            }
        }
        for (String style : mAppStyles) {
            if (url.endsWith(style)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public WebResourceResponse intercept(String url) {
        if (url.endsWith(mAppScripts[0])) {
            return prependResponse(url, mManager.getOnInitScripts());
        }

        if (url.endsWith(mAppScripts[mAppScripts.length - 1])) {
            return appendResponse(url, mManager.getOnLoadScripts());
        }

        if (url.endsWith(mAppStyles[0])) {
            return appendResponse(url, mManager.getStyles());
        }

        return null;
    }

}
