package com.liskovsoft.smartyoutubetv.bootstrap;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;
import com.liskovsoft.smartyoutubetv.common.helpers.LangUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageDialogSource implements SingleDialogSource {
    private final BootstrapActivity mActivity;
    private LangUpdater mLangUpdater;

    public LanguageDialogSource(BootstrapActivity activity) {
        mActivity = activity;
        initLangUpdater();
    }

    private void initLangUpdater() {
        mLangUpdater = new LangUpdater(mActivity);
    }

    @Override
    public List<DialogItem> getItems() {
        Map<String, String> locales = mLangUpdater.getSupportedLocales();
        List<DialogItem> titleAndTag = new ArrayList<>();

        for (Map.Entry<String, String> entry : locales.entrySet()) {
            titleAndTag.add(DialogItem.create(entry.getKey(), entry.getValue()));
        }

        return titleAndTag;
    }

    @Override
    public Object getSelectedItemTag() {
        String langCode = mLangUpdater.getPreferredLocale();

        // short lang code fix, like ru instead of ru_RU
        return getCorrectTag(langCode);
    }

    /**
     * Short lang code fix, like ru instead of ru_RU
     * @param langCode lang code
     * @return real code from resources
     */
    private String getCorrectTag(String langCode) {
        if (langCode == null || langCode.isEmpty()) {
            return "";
        }

        Map<String, String> locales = mLangUpdater.getSupportedLocales();

        for (String tag : locales.values()) {
            if (tag.startsWith(langCode)) {
                return tag;
            }
        }

        return "";
    }

    @Override
    public void setSelectedItemByTag(Object tag) {
        mLangUpdater.setPreferredLocale((String) tag);

        // give a time to settings to apply
        new Handler(mActivity.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivity.restart();
            }
        }, 1_000);
    }

    @Override
    public String getTitle() {
        return mActivity.getString(R.string.language_dialog_title);
    }
}
