package com.liskovsoft.smartyoutubetv.bootstrap;

import android.os.Handler;
import android.support.v4.util.Pair;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;
import com.liskovsoft.smartyoutubetv.common.helpers.LangUpdater;

import java.util.ArrayList;
import java.util.Collections;
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
        return mLangUpdater.getPreferredLocale();
    }

    @Override
    public void setSelectedItemTag(Object tag) {
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
