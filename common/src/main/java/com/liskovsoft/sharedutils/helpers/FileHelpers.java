package com.liskovsoft.sharedutils.helpers;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FileHelpers {
    public static File getCacheDir(Context context) {
        // NOTE: Android 6.0 fix
        File cacheDir = context.getExternalCacheDir();

        if (!PermissionManager.hasStoragePermissions(context)) {
            MessageHelpers.showMessage(context, "Storage permission not granted!");
            return null;
        }

        if (cacheDir == null) { // no storage, try to use internal one
            cacheDir = context.getCacheDir();
        }

        return cacheDir;
    }

    public static Collection<File> listFileTree(File dir) {
        Set<File> fileTree = new HashSet<>();

        if (dir == null || dir.listFiles() == null){
            return fileTree;
        }

        for (File entry : dir.listFiles()) {
            if (entry.isFile()) {
                fileTree.add(entry);
            } else {
                fileTree.addAll(listFileTree(entry));
            }
        }

        return fileTree;
    }

    /**
     * Deletes cache of app that belongs to the given context
     * @param context app activity or context
     */
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static void streamToFile(InputStream is, File destination) {
        FileOutputStream fos = null;

        try {
            destination.getParentFile().mkdirs();
            destination.createNewFile();

            fos = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
        } catch (FileNotFoundException ex) { // fix: open failed: EACCES (Permission denied)
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        } finally {
            closeStream(fos);
            closeStream(is);
        }
    }

    public static InputStream toStream(String content) {
        if (content == null) {
            return null;
        }

        return new ByteArrayInputStream(content.getBytes(Charset.forName("UTF8")));
    }

    public static void closeStream(Closeable fos) {
        try {
            if (fos != null)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // NOTE: Android 7.0 fix
    public static Uri getFileUri(Context context, String filePath) {
        // if your targetSdkVersion is 24 or higher, we have to use FileProvider class
        // https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
        if (VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".update_provider", new File(filePath));
        } else {
            return Uri.fromFile(new File(filePath));
        }
    }

    public static Uri getFileUri(Context context, File filePath) {
        if (filePath == null) {
            return null;
        }

        return getFileUri(context, filePath.getAbsolutePath());
    }
}
