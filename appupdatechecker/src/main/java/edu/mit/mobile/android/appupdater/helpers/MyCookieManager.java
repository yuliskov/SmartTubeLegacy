package edu.mit.mobile.android.appupdater.helpers;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client
 */
public class MyCookieManager {
    private static final String LOGIN_URL = "my_youtube";
    private final Context mContext;
    private static String sCookie = "";

    public MyCookieManager(Context context) {
        mContext = context;
    }

    public void saveCookie(WebView webView) {
        String url = webView.getUrl();
        if (url.contains(LOGIN_URL)) {
            CookieSyncManager syncManager = CookieSyncManager.createInstance(webView.getContext());
            CookieManager cookieManager = CookieManager.getInstance();

            String cookie = cookieManager.getCookie(url);

            sCookie = cookie;

            syncManager.sync();
        }
    }

    public static List<Cookie> loadCookie(HttpUrl url) {
        String cookie = sCookie;
        List<Cookie> result = new ArrayList<>();

        if (cookie.isEmpty()) {
            return result;
        }

        String[] cookiesRaw = cookie.split(";");
        for (String rawCookie : cookiesRaw) {
            result.add(Cookie.parse(url, rawCookie));
        }

        return result;
    }
}
