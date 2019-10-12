package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.sharedutils.locale.LangHelper;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class LangUpdater {
    private static final String TAG = LangUpdater.class.getSimpleName();
    private Context mContext;

    public LangUpdater(Context ctx) {
        mContext = ctx;
    }

    public void update() {
        String locale = getUpdatedLocale();

        Log.d(TAG, "Updating locale to " + locale);

        LangHelper.forceLocale(mContext, locale);
    }

    public String getUpdatedLocale() {
        String locale = LangHelper.guessLocale(mContext);

        String langCode = getPreferredLocale();

        // not set or default language selected
        if (langCode != null && !langCode.isEmpty()) {
            locale = langCode;
        }

        return locale;
    }

    /**
     * Get locale as lang code (e.g. zh, ru_RU etc)
     * @return lang code
     */
    public String getPreferredLocale() {
        return SmartPreferences.instance(mContext).getPreferredLanguage();
    }

    /**
     * Get locale in http format (e.g. zh, ru-RU etc)<br/>
     * Example: <code>ru,en-US;q=0.9,en;q=0.8,uk;q=0.7</code>
     * @return lang code
     */
    public String getPreferredBrowserLocale() {
        String locale = getPreferredLocale();

        if (locale == null || locale.isEmpty()) {
            locale = LangHelper.getDefaultLocale();
        }

        return locale.replace("_", "-").toLowerCase();
    }

    /**
     * E.g. ru, uk, en
     * @param langCode lang
     */
    public void setPreferredLocale(String langCode) {
        SmartPreferences.instance(mContext).setPreferredLanguage(langCode);
    }

    /**
     * Gets map of Human readable locale names and their respective lang codes
     * @return locale name/code map
     */
    public HashMap<String, String> getSupportedLocales() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String[] langs = mContext.getResources().getStringArray(R.array.supported_languages);
        map.put(mContext.getResources().getString(R.string.system_lang), "");
        for (String lang : langs) {
            StringTokenizer tokenizer = new StringTokenizer(lang, "|");
            String humanReadableName = tokenizer.nextToken();
            String langCode = tokenizer.nextToken();
            map.put(humanReadableName, langCode);
        }
        return map;
    }
}
