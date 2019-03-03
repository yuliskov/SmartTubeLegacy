package com.liskovsoft.smartyoutubetv.misc;

import android.webkit.CookieManager;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.sharedutils.okhttp.MyCookieLoader;
import org.xwalk.core.XWalkCookieManager;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client<br/>
 * Consists in two parts: {@link MyCookieLoader MyCookieLoader} and {@link MyCookieSaver MyCookieSaver}
 */
public class MyCookieSaver {
    /**
     * full url ex: https://www.youtube.com/tv#/surface?c=FEmy_youtube&params=cAY%253D&resume
     */
    private static final String LOGIN_URL_PART = "my_youtube";
    private static String sCookie;

    public static void saveCookie(WebView webView) {
        String url = webView.getUrl();
        if (url != null && url.contains(LOGIN_URL_PART)) {
            sCookie = getCookie(url);
            MyCookieLoader.setCookie(sCookie);
        }
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
