package com.liskovsoft.smartyoutubetv.common.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        File cacheDir = getCacheDir(context);

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

        streamToFile(asset, cachedFile);
    }

    private static String mangleSpecialChars(String id) {
        return id
                .replace("/", "_")
                .replace("\\", "_");
    }

    private static File getCacheDir(Context context) {
        // NOTE: Android 6.0 fix
        File cacheDir = context.getExternalCacheDir();
        if (!PermissionManager.checkStoragePermissions((Activity) context)) {
            MessageHelpers.showMessage(context, "Storage permission not granted!");
            return null;
        }

        if (cacheDir == null) { // no storage, try to use SDCard
            cacheDir = Environment.getExternalStorageDirectory();
            MessageHelpers.showMessage(context, "Please, make sure that SDCard is mounted");
        }

        return cacheDir;
    }

    private static void streamToFile(InputStream is, File destination) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } finally {
            Helpers.closeStream(fos);
            Helpers.closeStream(is);
        }
    }
}
