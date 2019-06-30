package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.jakewharton.disklrucache.DiskLruCache;
import com.liskovsoft.sharedutils.helpers.CacheHelpers;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;

import java.io.InputStream;

public class MainCachedScriptManager extends MainScriptManager {
    private final DiskLruCache mCache;
    private static final String ON_INIT_SCRIPTS = "on_init_scripts" + BuildConfig.TIMESTAMP;
    private static final String ON_LOAD_SCRIPTS = "on_load_scripts" + BuildConfig.TIMESTAMP;
    private static final String STYLES = "styles" + BuildConfig.TIMESTAMP;

    public MainCachedScriptManager(Context context) {
        super(context);

        mCache = CommonApplication.getCache();
    }

    @Override
    public InputStream getOnInitScripts() {
        if (CacheHelpers.exists(mCache, ON_INIT_SCRIPTS)) {
            return CacheHelpers.returnFromCache(mCache, ON_INIT_SCRIPTS);
        }

        return CacheHelpers.saveToCache(mCache, super.getOnInitScripts(), ON_INIT_SCRIPTS);
    }

    @Override
    public InputStream getOnLoadScripts() {
        if (CacheHelpers.exists(mCache, ON_LOAD_SCRIPTS)) {
            return CacheHelpers.returnFromCache(mCache, ON_LOAD_SCRIPTS);
        }

        return CacheHelpers.saveToCache(mCache, super.getOnLoadScripts(), ON_LOAD_SCRIPTS);
    }

    @Override
    public InputStream getStyles() {
        if (CacheHelpers.exists(mCache, STYLES)) {
            return CacheHelpers.returnFromCache(mCache, STYLES);
        }

        return CacheHelpers.saveToCache(mCache, super.getStyles(), STYLES);
    }
}
