package com.liskovsoft.smartyoutubetv.common.helpers;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CacheHelper {
    public static InputStream getFile(Context context, String id) {
        if (BuildConfig.DEBUG) {
            return null;
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
        if (BuildConfig.DEBUG) {
            return;
        }

        File cachedFile = getCachedFile(context, id);

        if (asset == null || cachedFile == null || cachedFile.exists()) {
            return;
        }

        Helpers.streamToFile(asset, cachedFile);
    }

    private static File getCachedFile(Context context, String id) {
        File cacheDir = Helpers.getCacheDir(context);

        String versionName;

        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (cacheDir == null) {
            return null;
        }

        String cachedFileName = mangleSpecialChars(id) + versionName;
        return new File(cacheDir, cachedFileName);
    }

    private static String mangleSpecialChars(String id) {
        return id
                .replace("/", "_")
                .replace("\\", "_");
    }
}
