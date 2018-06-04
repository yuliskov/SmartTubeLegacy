package com.liskovsoft.smartyoutubetv.misc;

import android.webkit.CookieManager;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import edu.mit.mobile.android.appupdater.helpers.MyCookieLoader;
import org.xwalk.core.XWalkCookieManager;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client<br/>
 * Consists in two parts: {@link MyCookieLoader MyCookieLoader} and {@link MyCookieSaver MyCookieSaver}
 */
public class MyCookieSaver {
    private static final String LOGIN_URL = "my_youtube";
    private static String sCookie;

    public static void saveCookie(WebView webView) {
        String url = webView.getUrl();
        if (url.contains(LOGIN_URL)) {
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
