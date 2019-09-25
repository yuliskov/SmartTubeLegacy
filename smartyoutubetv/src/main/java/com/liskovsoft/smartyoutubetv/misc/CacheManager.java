package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.io.File;
import java.util.ArrayList;

public class CacheManager {
    private static final String TAG = CacheManager.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<File> mCacheDirs;
    private static final String WEBVIEW_CACHE_SUBDIR = "app_webview/Cache";
    private static final String XWALK_CACHE_SUBDIR = "app_xwalkcore/Default/Cache";

    public CacheManager(Context context) {
        mContext = context;
        mCacheDirs = new ArrayList<>();
        mCacheDirs.add(mContext.getCacheDir());
        mCacheDirs.add(new File(mContext.getApplicationInfo().dataDir, WEBVIEW_CACHE_SUBDIR));
        mCacheDirs.add(new File(mContext.getApplicationInfo().dataDir, XWALK_CACHE_SUBDIR));
    }

    public void clearCache() {
        Log.d(TAG, "Removing cache files...");

        for (File cacheDir : mCacheDirs) {
            if (cacheDir.isDirectory()) {
                Log.d(TAG, "Removing cache: " + cacheDir.getAbsolutePath());
                FileHelpers.delete(cacheDir);
            }
        }
    }
}
