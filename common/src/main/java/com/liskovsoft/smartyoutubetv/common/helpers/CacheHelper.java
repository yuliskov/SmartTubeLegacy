package com.liskovsoft.smartyoutubetv.common.helpers;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CacheHelper {
    private static final String VERSION = BuildConfig.VERSION_NAME;

    public static InputStream getFile(Context context, String id) {
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

    private static File getCachedFile(Context context, String id) {
        File cacheDir = Helpers.getCacheDir(context);

        if (cacheDir == null) {
            return null;
        }

        String cachedFileName = mangleSpecialChars(id) + VERSION;
        return new File(cacheDir, cachedFileName);
    }

    public static void putFile(Context context, InputStream asset, String id) {
        File cachedFile = getCachedFile(context, id);

        if (asset == null || cachedFile == null || cachedFile.exists()) {
            return;
        }

        Helpers.streamToFile(asset, cachedFile);
    }

    private static String mangleSpecialChars(String id) {
        return id
                .replace("/", "_")
                .replace("\\", "_");
    }
}
