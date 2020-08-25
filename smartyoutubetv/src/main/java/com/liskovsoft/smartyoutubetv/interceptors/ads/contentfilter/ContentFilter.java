package com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter;

import android.content.Context;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ContentFilter {

    private static final String TAG = ContentFilter.class.getSimpleName();
    private final Map<String, String> mSecondReplacement = new HashMap<>();
    private final Map<String, String> mSecondReplacementRegExp = new HashMap<>();

    public ContentFilter(Context context) {
        SmartPreferences prefs = CommonApplication.getPreferences();

        // Workaround for XWalk only. WebView did filter in another place.
        if (prefs.isAdBlockEnabled() && SmartUtils.isXWalk(context)) {
            mSecondReplacementRegExp.put("enableMastheadLarge:[!\\.\\w]+,", "enableMastheadLarge:false,");
            mSecondReplacementRegExp.put("enableMastheadSmall:[!\\.\\w]+,", "enableMastheadSmall:true,");
        }

        if (prefs.getEnableAnimatedPreviews()) {
            mSecondReplacementRegExp.put("animatedWebpSupport:[!\\.\\w]+,", "animatedWebpSupport:true,");
        }

        // prefs key: ENABLE_HIGH_CONTRAST_MODE
        if (prefs.getEnableHighContrastMode()) {
            mSecondReplacementRegExp.put(
                    // c.zds||(b.get()?hH(a.body,"high-contrast"):jH(a.body,"high-contrast"))
                    "\\w+\\.\\w+\\|\\|\\(\\w+\\.get\\(\\)\\?([\\w$]+\\(\\w+\\.body,\"high-contrast\"\\)):\\w+\\(\\w+\\.body,\"high-contrast\"\\)\\)",
                    // hH(a.body,"high-contrast")
                    "$1"
            );
        }

        // NOTE: Video menu items source: https://www.youtube.com/youtubei/v1/browse
        // NOTE: Menu items available on Cobalt user agent only
        // see: VideoMenuInterceptor
        if (prefs.getEnableVideoMenu()) {
            mSecondReplacementRegExp.put("enableSelectOnKeyup:[!\\.\\w]+,", "enableSelectOnKeyup:true,");
            mSecondReplacementRegExp.put("enableVideoMenuOnBrowse:[!\\.\\w]+,", "enableVideoMenuOnBrowse:true,");
        }

        if (prefs.getEnableAnimatedUI()) {
            //Force enable animation, just scroll animation side panel animation is something else
            mSecondReplacementRegExp.put("enableAnimations:![!\\.\\w]+,", "enableAnimations:true,");
        }

        if (Helpers.isMicAvailable(context)) {
            mSecondReplacementRegExp.put("this\\.environment\\.supportsVoiceSearch", "true");
            mSecondReplacementRegExp.put("b\\.supportsVoiceSearch", "true");
        }
    }

    public InputStream filterFirstScript(InputStream result) {
        Log.d(TAG, "Filtering first script...");
        return result;
    }

    public InputStream filterSecondScript(InputStream result) {
        Log.d(TAG, "Filtering second script...");

        return doReplacement(result, mSecondReplacement, mSecondReplacementRegExp);
    }

    public InputStream filterLastScript(InputStream result) {
        Log.d(TAG, "Filtering last script...");
        return result;
    }

    public InputStream filterStyles(InputStream result) {
        Log.d(TAG, "Filtering styles...");
        return result;
    }

    private InputStream doReplacement(InputStream inputStream, Map<String, String> pairs, Map<String, String> pairsRegExp) {
        String data = null;

        if (pairs != null && !pairs.isEmpty()) {
            if (data == null) {
                data = Helpers.toString(inputStream);
            }

            for (String key : pairs.keySet()) {
                data = StringUtils.replace(data, key, pairs.get(key));
            }
        }

        if (pairsRegExp != null && !pairsRegExp.isEmpty()) {
            if (data == null) {
                data = Helpers.toString(inputStream);
            }

            for (String key : pairsRegExp.keySet()) {
                data = RegExUtils.replaceAll(data, key, pairsRegExp.get(key));
            }
        }

        InputStream result;

        if (data != null) {
            result = new ByteArrayInputStream(data.getBytes());
        } else {
            result = inputStream;
        }

        return result;
    }
}
