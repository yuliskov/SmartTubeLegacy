package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.jakewharton.disklrucache.DiskLruCache;
import com.liskovsoft.sharedutils.helpers.CacheHelpers;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;

import java.io.InputStream;

public class MainCachedScriptManager extends MainScriptManager {
    private final DiskLruCache mCache;
    private String ON_INIT_SCRIPTS_KEY;
    private String ON_LOAD_SCRIPTS_KEY;
    private String STYLES_KEY;

    public MainCachedScriptManager(Context context) {
        super(context);

        mCache = CommonApplication.getCache();

        int hash = getHash();
        ON_INIT_SCRIPTS_KEY = String.format("%s_%s_%s", "on_init_scripts", hash, BuildConfig.TIMESTAMP);
        ON_LOAD_SCRIPTS_KEY = String.format("%s_%s_%s", "on_load_scripts", hash, BuildConfig.TIMESTAMP);
        STYLES_KEY = String.format("%s_%s_%s", "styles", hash, BuildConfig.TIMESTAMP);
    }

    @Override
    public InputStream getOnInitScripts() {
        if (CacheHelpers.exists(mCache, ON_INIT_SCRIPTS_KEY)) {
            return CacheHelpers.returnFromCache(mCache, ON_INIT_SCRIPTS_KEY);
        }

        return CacheHelpers.saveToCache(mCache, super.getOnInitScripts(), ON_INIT_SCRIPTS_KEY);
    }

    @Override
    public InputStream getOnLoadScripts() {
        if (CacheHelpers.exists(mCache, ON_LOAD_SCRIPTS_KEY)) {
            return CacheHelpers.returnFromCache(mCache, ON_LOAD_SCRIPTS_KEY);
        }

        return CacheHelpers.saveToCache(mCache, super.getOnLoadScripts(), ON_LOAD_SCRIPTS_KEY);
    }

    @Override
    public InputStream getStyles() {
        if (CacheHelpers.exists(mCache, STYLES_KEY)) {
            return CacheHelpers.returnFromCache(mCache, STYLES_KEY);
        }

        return CacheHelpers.saveToCache(mCache, super.getStyles(), STYLES_KEY);
    }

    private int getHash() {
        int fullHash = 0;
        for (ScriptManager manager : getManagers()) {
            int hash = manager.getClass().getName().hashCode();
            fullHash += hash;
        }
        return fullHash;
    }
}
