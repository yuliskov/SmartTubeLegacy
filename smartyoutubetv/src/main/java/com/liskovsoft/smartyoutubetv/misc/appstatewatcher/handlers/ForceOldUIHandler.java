package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.app.Activity;
import android.os.Handler;
import android.webkit.CookieManager;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTV4K;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTV1080Activity;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;
import org.xwalk.core.XWalkCookieManager;

public class ForceOldUIHandler extends StateHandler {
    private static final String TAG = ForceOldUIHandler.class.getSimpleName();
    private static final String NEW_UI_COOKIE2 = "VISITOR_INFO1_LIVE=xcc12hbEjFM; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String NEW_UI_COOKIE = "VISITOR_INFO1_LIVE=cp3UVuEA3l4; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String OLD_UI_COOKIE = "VISITOR_INFO1_LIVE=ErVksiAQ6pg; path=/; domain=.youtube.com; expires=Sat, 25-Apr-2025 15:42:26 GMT; httponly";
    private static final String COOKIE_URL = "https://www.youtube.com";
    private final Activity mContext;
    private static final long XWALK_INIT_DELAY_MS = 3_000;

    public ForceOldUIHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onInit() {
        if (!CommonApplication.getPreferences().isUserLogged()) {
            Log.d(TAG, "User is not logged. Don't force OldUI...");
            return;
        }

        String cookie = OLD_UI_COOKIE;

        try {
            if (mContext instanceof SmartYouTubeTV1080Activity || mContext instanceof SmartYouTubeTV4K) {
                Log.d(TAG, "Setting WebView cookie");
                CookieManager.getInstance().setCookie(COOKIE_URL, cookie);
            } else {
                // NOTE: early initialization could break embedding of XWalk core
                new Handler(mContext.getMainLooper()).postDelayed(() -> {
                    Log.d(TAG, "Setting XWalk cookie");
                    XWalkCookieManager cookieManager = new XWalkCookieManager();
                    try {
                        cookieManager.setCookie(COOKIE_URL, cookie);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }, XWALK_INIT_DELAY_MS);
            }
        } catch (Exception e) {
            // WebView not installed?
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
