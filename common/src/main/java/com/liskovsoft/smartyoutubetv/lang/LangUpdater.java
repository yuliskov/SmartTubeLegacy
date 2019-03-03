package com.liskovsoft.smartyoutubetv.lang;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.LangHelper;
import com.liskovsoft.smartyoutubetv.common.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class LangUpdater {
    private Context mContext;
    private String mLocale;

    public LangUpdater(Context ctx) {
        mContext = ctx;
    }

    public void update() {
        mLocale = LangHelper.guessLocale(mContext);

        String langCode = getPreferredLocale();

        // not set or default language selected
        if (langCode != null && !langCode.isEmpty()) {
            mLocale = langCode;
        }

        LangHelper.forceLocale(mContext, mLocale);
    }

    /**
     * Get locale as lang code (e.g. zh, ru_RU etc)
     * @return lang code
     */
    public String getPreferredLocale() {
        return SmartPreferences.instance(mContext).getPreferredLanguage();
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
