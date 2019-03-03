package com.liskovsoft.sharedutils.helpers;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssetHelper {
    private static InputStream appendNewLine(InputStream textStream) {
        InputStream newLineStream = new ByteArrayInputStream("\n".getBytes());
        return Helpers.appendStream(textStream, newLineStream);
    }

    /**
     * Merge string assets. Silently add new line after each asset.
     */
    public static InputStream getAssetMerged(Context ctx, List<String> paths) {
        return getAssetMerged(ctx, paths, true);
    }

    private static InputStream getAssetMerged(Context ctx, List<String> paths, boolean newLine) {
        if (paths == null) {
            return null;
        }

        InputStream is = null;

        for (String path : paths) {
            InputStream asset = getAsset(ctx, path);
            if (newLine)
                asset = appendNewLine(asset);
            is = Helpers.appendStream(is, asset);
        }
        return is;
    }

    public static InputStream getAsset(Context ctx, String fileName) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static InputStream getAssetCSSFilesMerged(Context ctx, String dir) {
        String fileId = dir + "CSS";
        InputStream cachedStream = CacheHelper.getFile(ctx, fileId);

        if (cachedStream != null) {
            return cachedStream;
        }

        List<String> assetFiles = getAssetCSSFiles(ctx, dir);
        InputStream assetMerged = getAssetMerged(ctx, assetFiles);

        return CacheHelper.putFile(ctx, assetMerged, fileId);
    }

    public static InputStream getAssetJSFilesMerged(Context ctx, String dir) {
        String fileId = dir + "JS";
        InputStream cachedStream = CacheHelper.getFile(ctx, fileId);

        if (cachedStream != null) {
            return cachedStream;
        }

        List<String> assetFiles = getAssetJSFiles(ctx, dir);
        InputStream assetMerged = getAssetMerged(ctx, assetFiles);

        return CacheHelper.putFile(ctx, assetMerged, fileId);
    }

    private static List<String> getAssetJSFiles(Context ctx, String dir) {
        return getAssetFiles(ctx, dir, ".js");
    }

    private static List<String> getAssetCSSFiles(Context ctx, String dir) {
        return getAssetFiles(ctx, dir, ".css");
    }

    public static List<String> getAssetFiles(Context ctx, String dir) {
        return getAssetFiles(ctx, dir, null);
    }

    private static List<String> getAssetFiles(Context ctx, String dir, String endsWith) {
        String [] list;
        List<String> result = new ArrayList<>();
        try {
            list = ctx.getAssets().list(dir);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    List<String> nestedList = getAssetFiles(ctx, dir + "/" + file, endsWith); // folder???
                    if (!nestedList.isEmpty()) // folder???
                        result.addAll(nestedList);
                    else {
                        // This is a file
                        if (endsWith == null || file.endsWith(endsWith))
                            result.add(dir + "/" + file);
                    }
                }
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }

        return result;
    }

    //public static List<String> getAssetFiles(Context ctx, String dir, String endsWith) {
    //    String key = dir + endsWith;
    //    List<String> cached = sCache.get(key);
    //    if (cached != null)
    //        return cached;
    //    List<String> newFiles = getAssetFiles_(ctx, dir, endsWith);
    //    sCache.put(key, newFiles);
    //    return newFiles;
    //}
}
