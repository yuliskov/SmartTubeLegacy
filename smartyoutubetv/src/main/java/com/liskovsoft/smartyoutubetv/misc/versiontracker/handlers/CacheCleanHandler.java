package com.liskovsoft.smartyoutubetv.misc.versiontracker.handlers;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.versiontracker.AppVersionTrackerBase.Handler;

import java.io.File;
import java.util.ArrayList;

public class CacheCleanHandler extends Handler {
    private static final String TAG = CacheCleanHandler.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<File> mCacheDirs;
    private static final String WEBVIEW_CACHE_SUBDIR = "app_webview/Cache";
    private static final String XWALK_CACHE_SUBDIR = "app_xwalkcore/Default/Cache";

    public CacheCleanHandler(Context context) {
        mContext = context;
        mCacheDirs = new ArrayList<>();
        mCacheDirs.add(mContext.getCacheDir());
        mCacheDirs.add(new File(mContext.getApplicationInfo().dataDir, WEBVIEW_CACHE_SUBDIR));
        mCacheDirs.add(new File(mContext.getApplicationInfo().dataDir, XWALK_CACHE_SUBDIR));
    }

    private void clearCache() {
        Log.d(TAG, "App has been updated or installed. Removing cache files...");

        for (File cacheDir : mCacheDirs) {
            if (cacheDir.isDirectory()) {
                Log.d(TAG, "Removing cache: " + cacheDir.getAbsolutePath());
                FileHelpers.delete(cacheDir);
            }
        }
    }

    @Override
    public void onUpdate() {
        clearCache();
    }

    @Override
    public void onInstall() {
        clearCache();
    }
}
