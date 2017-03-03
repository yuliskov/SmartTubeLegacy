package com.liskovsoft.smartyoutubetv.helpers;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Helpers {
    /**
     * Simple wildcard matching routine. Implemented without regex. So you may expect huge performance boost.
     * @param host
     * @param mask
     * @return
     */
    public static boolean match(String host, String mask) {
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

    public static InputStream getAsset(Context ctx, String fileName) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static String encodeURI(byte[] data) {
        try {
            // make behaviour of java uri-encode the same as javascript's one
            return URLEncoder.encode(new String(data, "UTF-8"), "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
