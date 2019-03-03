package com.liskovsoft.smartyoutubetv.interceptors;

import android.content.Context;
import android.util.Log;
import android.webkit.WebResourceResponse;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.webscripts.MainScriptManager;
import com.liskovsoft.smartyoutubetv.webscripts.ScriptManager;

import java.io.InputStream;

public class ScriptManagerInterceptor extends RequestInterceptor {
    private static final String TAG = ScriptManagerInterceptor.class.getSimpleName();
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
            Log.d(TAG, "Begin onInitScripts");
            InputStream onInitScripts = mManager.getOnInitScripts();
            Log.d(TAG, "End onInitScripts");
            return prependResponse(url, onInitScripts);
        }

        if (Helpers.endsWith(url, LAST_SCRIPT_NAME)) {
            Log.d(TAG, "Begin onLoadScript");
            InputStream onLoadScripts = mManager.getOnLoadScripts();
            Log.d(TAG, "End onLoadScript");
            return appendResponse(url, onLoadScripts);
        }

        if (Helpers.endsWith(url, LAST_STYLE_NAME)) {
            Log.d(TAG, "Begin onStyles");
            InputStream styles = mManager.getStyles();
            Log.d(TAG, "End onStyles");
            return appendResponse(url, styles);
        }

        return null;
    }

}
