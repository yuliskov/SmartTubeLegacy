package com.liskovsoft.smartyoutubetv.interceptors.scripts;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;

/**
 *  1) live.js<br/>
 *  2) airstream-prod-css.css
 *  3) app-prod.js<br/>
 *  4) tv-player.js or tv-player-ias.js<br/>
 */
public class MainScriptManagerInterceptor extends ScriptManagerInterceptor {
    private static final String[] FIRST_SCRIPT_NAME = {"live.js"};
    private static final String[] SECOND_SCRIPT_NAME = {"app-prod.js"};
    private static final String[] THIRD_SCRIPT_NAME = {"tv-player.js", "tv-player-ias.js"};
    private static final String[] MAIN_STYLE_NAME = {"airstream-prod-css.css", "kids-prod.css"};

    public MainScriptManagerInterceptor(Context context) {
        super(context);
    }

    @Override
    protected boolean isFirstScript(String url) {
        return Helpers.endsWith(url, FIRST_SCRIPT_NAME);
    }

    @Override
    protected boolean isLastScript(String url) {
        return Helpers.endsWith(url, THIRD_SCRIPT_NAME);
    }

    @Override
    protected boolean isStyle(String url) {
        return Helpers.endsWith(url, MAIN_STYLE_NAME);
    }
}
