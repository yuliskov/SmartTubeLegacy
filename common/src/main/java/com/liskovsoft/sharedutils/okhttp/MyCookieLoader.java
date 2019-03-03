package com.liskovsoft.sharedutils.okhttp;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client<br/>
 * Consists in two parts: {@link MyCookieLoader MyCookieLoader} and {@link com.liskovsoft.smartyoutubetv.misc.MyCookieSaver MyCookieSaver}
 */
public class MyCookieLoader {
    private static String sCookie;

    public static List<Cookie> loadCookie(HttpUrl url) {
        String cookie = sCookie;
        List<Cookie> result = new ArrayList<>();

        if (cookie == null || cookie.isEmpty()) {
            return result;
        }

        String[] cookiesRaw = cookie.split(";");
        for (String rawCookie : cookiesRaw) {
            result.add(Cookie.parse(url, rawCookie));
        }

        return result;
    }

    public static void setCookie(String cookie) {
        sCookie = cookie;
    }
}
