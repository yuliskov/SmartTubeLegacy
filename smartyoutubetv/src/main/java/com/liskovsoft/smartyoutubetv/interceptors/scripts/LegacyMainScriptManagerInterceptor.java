package com.liskovsoft.smartyoutubetv.interceptors.scripts;

import android.content.Context;

import com.liskovsoft.sharedutils.helpers.Helpers;

import java.util.regex.Pattern;

/**
 * Legacy devices
 */
public class LegacyMainScriptManagerInterceptor extends MainScriptManagerInterceptor {
    private static final Pattern[] BASE_SCRIPT_REGEX = {Pattern.compile("youtube.com/s/_/kabuki_legacy/_/js/.*/m=base$")};
    private static final Pattern[] MAIN_SCRIPT_REGEX = {Pattern.compile("youtube.com/s/_/kabuki_legacy/_/js/.*/m=main$")};
    private static final Pattern[] MAIN_STYLE_REGEX = {Pattern.compile("youtube.com/s/_/kabuki_legacy/_/ss/.*")};

    public LegacyMainScriptManagerInterceptor(Context context) {
        super(context);
    }

    @Override
    protected boolean isBaseScript(String url) {
        return super.isBaseScript(url) || Helpers.matchAll(url, BASE_SCRIPT_REGEX);
    }

    @Override
    protected boolean isMainScript(String url) {
        return super.isMainScript(url) || Helpers.matchAll(url, MAIN_SCRIPT_REGEX);
    }

    @Override
    protected boolean isStyle(String url) {
        return super.isStyle(url) || Helpers.matchAll(url, MAIN_STYLE_REGEX);
    }
}
