package com.liskovsoft.smartyoutubetv.bootstrap.dialoglanguage;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

import java.util.Map;

public class LanguageDialogItem extends DialogItem {
    private final String mLangCode;
    private final LangUpdater mUpdater;
    private final BootstrapActivity mActivity;

    public LanguageDialogItem(String localeName, String langCode, LangUpdater updater, BootstrapActivity activity) {
        super(localeName, false);

        mLangCode = langCode;
        mUpdater = updater;
        mActivity = activity;
    }

    @Override
    public boolean getChecked() {
        String langCode = mUpdater.getPreferredLocale();

        // short lang code fix, like ru instead of ru_RU
        return mLangCode.equals(getCorrectTag(langCode));
    }

    @Override
    public void setChecked(boolean checked) {
        if (!checked) {
            return;
        }

        mUpdater.setPreferredLocale(mLangCode);

        new Handler(mActivity.getMainLooper()).postDelayed(() -> SmartUtils.restartToBootstrap(mActivity), 1_000);
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

        Map<String, String> locales = mUpdater.getSupportedLocales();

        for (String tag : locales.values()) {
            if (tag.startsWith(langCode)) {
                return tag;
            }
        }

        return "";
    }
}
