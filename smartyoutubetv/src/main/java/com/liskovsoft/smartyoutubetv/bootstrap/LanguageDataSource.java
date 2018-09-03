package com.liskovsoft.smartyoutubetv.bootstrap;

import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.GenericSelectorDialog.DataSource;
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
        mActivity.restart();
    }

    @Override
    public String getTitle() {
        return mActivity.getString(R.string.language_dialog_title);
    }
}
