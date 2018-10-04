package com.liskovsoft.smartyoutubetv.common.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Helpers {
    private static HashMap<String, List<String>> sCache = new HashMap<>();

    /**
     * Simple wildcard matching routine. Implemented without regex. So you may expect huge performance boost.
     * @param host
     * @param mask
     * @return
     */
    public static boolean matchSubstr(String host, String mask) {
        String[] sections = mask.split("\\*");
        String text = host;
        for (String section : sections) {
            int index = text.indexOf(section);
            if (index == -1) {
                return false;
            }
            text = text.substring(index + section.length());
        }
        return true;
    }

    public static boolean matchSubstrNoCase(String host, String mask) {
        return matchSubstr(host.toLowerCase(), mask.toLowerCase());
    }

    public static InputStream appendStream(InputStream first, InputStream second) {
        if (first == null && second == null) {
            return null;
        }

        if (first == null) {
            return second;
        }

        if (second == null) {
            return first;
        }

        return new SequenceInputStream(first, second);
    }

    private static InputStream appendNewLine(InputStream textStream) {
        InputStream newLineStream = new ByteArrayInputStream("\n".getBytes());
        return appendStream(textStream, newLineStream);
    }

    /**
     * Merge string assets. Silently add new line after each asset.
     */
    public static InputStream getAssetMerged(Context ctx, List<String> paths) {
        return getAssetMerged(ctx, paths, true);
    }

    public static InputStream getAssetMerged(Context ctx, List<String> paths, boolean newLine) {
        if (paths == null) {
            return null;
        }

        InputStream is = null;

        for (String path : paths) {
            InputStream asset = Helpers.getAsset(ctx, path);
            if (newLine)
                asset = appendNewLine(asset);
            is = appendStream(is, asset);
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
        List<String> assetFiles = getAssetCSSFiles(ctx, dir);
        return Helpers.getAssetMerged(ctx, assetFiles);
    }

    public static InputStream getAssetJSFilesMerged(Context ctx, String dir) {
        List<String> assetFiles = getAssetJSFiles(ctx, dir);
        return Helpers.getAssetMerged(ctx, assetFiles);
    }

    public static List<String> getAssetJSFiles(Context ctx, String dir) {
        return getAssetFiles(ctx, dir, ".js");
    }

    public static List<String> getAssetCSSFiles(Context ctx, String dir) {
        return getAssetFiles(ctx, dir, ".css");
    }

    public static List<String> getAssetFiles(Context ctx, String dir) {
        return getAssetFiles(ctx, dir, null);
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

    public static String encodeURI(byte[] data) {
        try {
            // make behaviour of java uri-encode the same as javascript's one
            return URLEncoder.encode(new String(data, "UTF-8"), "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean floatEquals(float num1, float num2) {
        float epsilon = 0.1f;
        return Math.abs(num1 - num2) < epsilon;
    }

    public static String getDeviceName() {
        return String.format("%s (%s)", Build.MODEL, Build.PRODUCT);
    }

    public static boolean deviceMatch(String[] devicesToProcess) {
        String thisDeviceName = Helpers.getDeviceName();
        for (String deviceName : devicesToProcess) {
            boolean match = matchSubstrNoCase(thisDeviceName, deviceName);
            if (match) {
                return true;
            }
        }
        return false;
    }

    public static String toString(Throwable ex) {
        if (ex instanceof IllegalStateException &&
                ex.getCause() != null) {
            ex = ex.getCause();
        }
        return String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage());
    }

    public static String toString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
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

    public static void postOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static String unixToLocalDate(Context ctx, String timestamp) {
        Locale current = ctx.getResources().getConfiguration().locale;
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, current);
        Date date;
        if (timestamp == null) {
            date = new Date();
        } else {
            date = new Date((long) Integer.parseInt(timestamp) * 1000);
        }
        return dateFormat.format(date);
    }

    public static String runMultiMatcher(String input, String... patterns) {
        if (input == null) {
            return null;
        }

        Pattern regex;
        Matcher matcher;
        String result = null;
        for (String pattern : patterns) {
            regex = Pattern.compile(pattern);
            matcher = regex.matcher(input);

            if (matcher.find()) {
                result = matcher.group(matcher.groupCount()); // get last group
                break;
            }
        }

        return result;
    }

    public static boolean isCallable(Context ctx, Intent intent) {
        List<ResolveInfo> list = ctx.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * Get scale for use in {@link android.webkit.WebView} instantiation
     * @param ctx context
     * @param picWidth constant that I knew beforehand
     * @return calculated scale
     */
    public static int getScale(Context ctx, int picWidth) {
        Point p = new Point();
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getSize(p);
        int width = p.x;
        Double val = (double) width / (double) picWidth;
        val = val * 100d;
        return val.intValue();
    }
}
