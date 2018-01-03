package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Utils {
    private static OkHttpClient mClient;

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

    public static InputStream getAsset(Context ctx, String fileName) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return is;
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
        String thisDeviceName = Utils.getDeviceName();
        for (String deviceName : devicesToProcess) {
            boolean match = matchSubstrNoCase(thisDeviceName, deviceName);
            if (match) {
                return true;
            }
        }
        return false;
    }

    public static String toString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    public static InputStream toStream(String content) {
        return new ByteArrayInputStream(content.getBytes(Charset.forName("UTF8")));
    }

    public static Response doOkHttpRequest(String url) {
        mClient = new OkHttpClient();

        Request okHttpRequest = new Request.Builder()
                .url(url)
                .build();

        Response okHttpResponse = null;
        try {
            okHttpResponse = mClient.newCall(okHttpRequest).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        return okHttpResponse;
    }


    public static void postOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
