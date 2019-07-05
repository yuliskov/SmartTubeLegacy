package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.WebView;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import org.xwalk.core.XWalkCookieManager;

/**
 * Main intent: to transfer auth data from WebView to OkHttp client<br/>
 */
public class MyCookieSaver {
    private static final String BROWSE_URL_PART = "youtube.com/tv#/surface";
    private static final String SITE_URL = "https://www.youtube.com";
    private static final String TAG = MyCookieSaver.class.getSimpleName();
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public MyCookieSaver(Context context) {
        mContext = context;
        mPrefs = SmartPreferences.instance(mContext);
    }

    public void saveCookie(WebView webView) {
        String url = webView.getUrl();
        if (url != null && url.contains(BROWSE_URL_PART)) {
            String cookie = getCookie(url);
            mPrefs.setCookieHeader(cookie);
        }
    }

    public void saveCookie() {
        Log.d(TAG, "Saving cookie...");

        String cookie = getCookie(SITE_URL);

        if (cookie != null) {
            mPrefs.setCookieHeader(cookie);
        }
    }

    private String getCookie(String url) {
        EngineType engine = Browser.getEngineType();
        String cookie = null;
        if (engine == EngineType.XWalk) {
            XWalkCookieManager xWalkCookieManager = new XWalkCookieManager();
            try {
                cookie = xWalkCookieManager.getCookie(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookie = cookieManager.getCookie(url);
        }

        return cookie;
    }
}
