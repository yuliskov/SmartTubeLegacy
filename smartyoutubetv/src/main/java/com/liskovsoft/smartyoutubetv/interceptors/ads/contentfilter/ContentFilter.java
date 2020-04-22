package com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ContentFilter {
    private static final String TAG = ContentFilter.class.getSimpleName();
    private final Context mContext;
    private final SmartPreferences mPrefs;
    private Map<String, String> mSecondReplacement = new HashMap<>();

    public ContentFilter(Context context) {
        mContext = context;
        mPrefs = CommonApplication.getPreferences();

        if (mPrefs.isAdBlockEnabled()) {
            //mSecondReplacement.add(new ReplacePair("b.masthead&&!b.masthead.isMuted()", "false"));
            mSecondReplacement.put("f.enable_masthead_small||f.enable_masthead_medium||f.enable_masthead_large", "false");
        }

        if (mPrefs.getEnableAnimatedPreviews()) {
            mSecondReplacement.put("animatedWebpSupport:I", "animatedWebpSupport:true");
        }

        // prefs key: ENABLE_HIGH_CONTRAST_MODE
        if (mPrefs.getEnableHighContrastMode()) {
            mSecondReplacement.put("c.zds||(b.get()?hH(a.body,\"high-contrast\")", "false||(true?hH(a.body,\"high-contrast\")");
        }

        // NOTE: Video menu items source: https://www.youtube.com/youtubei/v1/browse
        // NOTE: Menu items available on Cobalt user agent only
        // see: VideoMenuInterceptor
        if (mPrefs.getEnableVideoMenu()) {
            mSecondReplacement.put("enableSelectOnKeyup:l", "enableSelectOnKeyup:true");
            mSecondReplacement.put("enableVideoMenuOnBrowse:va", "enableVideoMenuOnBrowse:true");
        }
    }

    public InputStream filterFirstScript(InputStream result) {
        Log.d(TAG, "Filtering first script...");
        return result;
    }

    public InputStream filterSecondScript(InputStream result) {
        Log.d(TAG, "Filtering second script...");

        if (mSecondReplacement.isEmpty()) {
            return result;
        }

        return doReplacement(result, mSecondReplacement);
    }

    public InputStream filterLastScript(InputStream result) {
        Log.d(TAG, "Filtering last script...");
        return result;
    }

    public InputStream filterStyles(InputStream result) {
        Log.d(TAG, "Filtering styles...");
        return result;
    }

    private InputStream doReplacement(InputStream inputStream, Map<String, String> pairs) {
        String data = Helpers.toString(inputStream);

        data = StringUtils.replaceEachRepeatedly(data, pairs.keySet().toArray(new String[0]), pairs.values().toArray(new String[0]));

        return new ByteArrayInputStream(data.getBytes());
    }
}
