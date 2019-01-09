package com.liskovsoft.smartyoutubetv.common.helpers;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;

public class CacheHelper {
    private static final String PREFIX = CacheHelper.class.getSimpleName();
    private static InputStream sDebugStream;
    private static boolean sCleanupDone;

    public static InputStream getFile(Context context, String id) {
        // don't use cache while in debug mode
        if (BuildConfig.DEBUG) {
            InputStream debugStream = sDebugStream;
            sDebugStream = null;
            return debugStream;
        }

        FileInputStream fis = null;

        File cachedFile = getCachedFile(context, id);

        if (cachedFile == null || !cachedFile.exists()) {
            return null;
        }

        try {
            fis = new FileInputStream(cachedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return fis;
    }

    public static void putFile(Context context, InputStream asset, String id) {
        // don't use cache while in debug mode
        if (BuildConfig.DEBUG) {
            sDebugStream = asset;
            return;
        }

        // do cleanup on put so this minimize overall calls
        performCleanup(context);

        File cachedFile = getCachedFile(context, id);

        if (asset == null || cachedFile == null || cachedFile.exists()) {
            return;
        }

        Helpers.streamToFile(asset, cachedFile);
    }

    private static File getCachedFile(Context context, String id) {
        File cacheDir = Helpers.getCacheDir(context);

        if (cacheDir == null) {
            return null;
        }

        String versionName = Helpers.getAppVersion(context);

        String cachedFileName = String.format("%s_%s_%s", PREFIX, mangleSpecialChars(id), versionName);

        return new File(cacheDir, cachedFileName);
    }

    private static String mangleSpecialChars(String id) {
        return id
                .replace("/", "_")
                .replace("\\", "_");
    }

    private static void performCleanup(Context context) {
        if (sCleanupDone) {
            return;
        }

        Collection<File> files = Helpers.listFileTree(Helpers.getCacheDir(context));

        for (File file : files) {
            String name = file.getName();

            boolean hasPrefix = name.contains(PREFIX);

            if (!hasPrefix) {
                continue;
            }

            boolean hasActualVersion = name.contains(Helpers.getAppVersion(context));

            if (hasActualVersion) {
                continue;
            }

            file.delete();
        }

        sCleanupDone = true;
    }
}
