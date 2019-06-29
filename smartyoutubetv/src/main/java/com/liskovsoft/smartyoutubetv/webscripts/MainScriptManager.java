package com.liskovsoft.smartyoutubetv.webscripts;

import android.content.Context;
import com.jakewharton.disklrucache.DiskLruCache;
import com.liskovsoft.sharedutils.helpers.CacheHelpers;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.CommonApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainScriptManager implements ScriptManager {
    private final Context mContext;
    private final List<ScriptManager> mManagers = new ArrayList<>();
    private final DiskLruCache mCache;
    private static final String ON_INIT_SCRIPTS = "on_init_scripts";
    private static final String ON_LOAD_SCRIPTS = "on_load_scripts";
    private static final String STYLES = "styles";

    public MainScriptManager(Context context) {
        mContext = context;
        mManagers.add(new CommonScriptManager(context));
        mManagers.add(new AddonsScriptManager(context));
        mManagers.add(new ExoScriptManager(context));
        mManagers.add(new EndCardsScriptManager(context));

        mCache = CommonApplication.getCache();
    }

    @Override
    public InputStream getOnInitScripts() {
        if (CacheHelpers.exists(mCache, ON_INIT_SCRIPTS)) {
            return CacheHelpers.returnFromCache(mCache, ON_INIT_SCRIPTS);
        }

        InputStream is = null;
        for (ScriptManager manager : mManagers) {
            is = Helpers.appendStream(is, manager.getOnInitScripts());
        }

        return CacheHelpers.saveToCache(mCache, is, ON_INIT_SCRIPTS);
    }

    @Override
    public InputStream getOnLoadScripts() {
        if (CacheHelpers.exists(mCache, ON_LOAD_SCRIPTS)) {
            return CacheHelpers.returnFromCache(mCache, ON_LOAD_SCRIPTS);
        }

        InputStream is = null;
        for (ScriptManager manager : mManagers) {
            is = Helpers.appendStream(is, manager.getOnLoadScripts());
        }

        return CacheHelpers.saveToCache(mCache, is, ON_LOAD_SCRIPTS);
    }

    @Override
    public InputStream getStyles() {
        if (CacheHelpers.exists(mCache, STYLES)) {
            return CacheHelpers.returnFromCache(mCache, STYLES);
        }

        InputStream is = null;
        for (ScriptManager manager : mManagers) {
            is = Helpers.appendStream(is, manager.getStyles());
        }

        return CacheHelpers.saveToCache(mCache, is, STYLES);
    }
}
