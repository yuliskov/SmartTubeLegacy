package com.liskovsoft.smartyoutubetv.misc;

import android.webkit.CookieManager;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.MyCookieLoader;
import org.xwalk.core.XWalkCookieManager;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client<br/>
 * Consists in two parts: {@link MyCookieLoader MyCookieLoader} and {@link MyCookieSaver MyCookieSaver}
 */
public class MyCookieSaver {
    private static final String BROWSE_URL_PART = "youtube.com/tv#/surface";
    private static final String SITE_URL = "https://www.youtube.com";
    private static final String TAG = MyCookieSaver.class.getSimpleName();

    public static void saveCookie(WebView webView) {
        String url = webView.getUrl();
        if (url != null && url.contains(BROWSE_URL_PART)) {
            String cookie = getCookie(url);
            MyCookieLoader.setRawCookie(cookie);
        }
    }

    public static void saveCookie() {
        Log.d(TAG, "Saving cookie...");

        MyCookieLoader.setRawCookie(getCookie(SITE_URL));
    }

    private static String getCookie(String url) {
        EngineType engine = Browser.getEngineType();
        String cookie;
        if (engine == EngineType.XWalk) {
            XWalkCookieManager xWalkCookieManager = new XWalkCookieManager();
            cookie = xWalkCookieManager.getCookie(url);
        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookie = cookieManager.getCookie(url);
        }

        return cookie;
    }
}
