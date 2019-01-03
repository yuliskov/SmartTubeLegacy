package com.liskovsoft.smartyoutubetv.bootstrap;

import android.os.Handler;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DataSource;
import com.liskovsoft.smartyoutubetv.common.helpers.LangUpdater;

import java.util.Map;

public class LanguageDataSource implements DataSource {
    private final BootstrapActivity mActivity;
    private LangUpdater mLangUpdater;

    public LanguageDataSource(BootstrapActivity activity) {
        mActivity = activity;
        initLangUpdater();
    }

    private void initLangUpdater() {
        mLangUpdater = new LangUpdater(mActivity);
    }

    @Override
    public Map<String, String> getDialogItems() {
        return mLangUpdater.getSupportedLocales();
    }

    @Override
    public String getSelected() {
        return mLangUpdater.getPreferredLocale();
    }

    @Override
    public void setSelected(String tag) {
        mLangUpdater.setPreferredLocale(tag);

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
