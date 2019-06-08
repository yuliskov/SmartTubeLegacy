package com.liskovsoft.smartyoutubetv.misc;

import com.liskovsoft.sharedutils.mylogger.Log;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client<br/>
 * Consists in two parts: {@link MyCookieLoader MyCookieLoader} and com.liskovsoft.smartyoutubetv.misc.MyCookieSaver
 */
public class MyCookieLoader {
    private static final String TAG = MyCookieLoader.class.getSimpleName();
    private static String sCookie;
    private static Map<String, List<Cookie>> sCache = new HashMap<>();

    public static List<Cookie> loadCookie(HttpUrl url) {
        List<Cookie> cookies = sCache.get(url.host());

        if (cookies != null) {
            return cookies;
        }

        String cookie = sCookie;
        List<Cookie> result = new ArrayList<>();

        if (cookie == null || cookie.isEmpty()) {
            Log.e(TAG, "Cookie is empty: " + cookie);
            return result;
        }

        String[] cookiesRaw = cookie.split(";");
        HttpUrl hostUrl = HttpUrl.parse("https://" + url.host());

        for (String rawCookie : cookiesRaw) {
            result.add(Cookie.parse(hostUrl, rawCookie));
        }

        sCache.put(url.host(), result);

        return result;
    }

    /**
     * Warning: routine isn't tested yet!
     */
    public static List<HttpCookie> loadCookie() {
        String cookie = sCookie;
        List<HttpCookie> result = new ArrayList<>();

        if (cookie == null || cookie.isEmpty()) {
            Log.e(TAG, "Cookie is empty: " + cookie);
            return result;
        }

        return HttpCookie.parse(cookie);
    }

    public static void setRawCookie(String cookie) {
        Log.d(TAG, "Setting cookie: " + cookie);
        sCookie = cookie;
    }

    public static String getRawCookie() {
        return sCookie;
    }
}
