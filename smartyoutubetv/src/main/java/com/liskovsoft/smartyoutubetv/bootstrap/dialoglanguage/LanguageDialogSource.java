package com.liskovsoft.smartyoutubetv.bootstrap.dialoglanguage;

import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.SingleDialogSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LanguageDialogSource implements SingleDialogSource {
    private final BootstrapActivity mActivity;
    private LangUpdater mLangUpdater;
    private List<DialogItem> mTitleAndTag;

    public LanguageDialogSource(BootstrapActivity activity) {
        mActivity = activity;
        initLangUpdater();
    }

    private void initLangUpdater() {
        mLangUpdater = new LangUpdater(mActivity);

        Map<String, String> locales = mLangUpdater.getSupportedLocales();
        mTitleAndTag = new ArrayList<>();

        for (Map.Entry<String, String> entry : locales.entrySet()) {
            mTitleAndTag.add(new LanguageDialogItem(entry.getKey(), entry.getValue(), mLangUpdater, mActivity));
        }
    }

    @Override
    public List<DialogItem> getItems() {
        return mTitleAndTag;
    }

    @Override
    public String getTitle() {
        return mActivity.getString(R.string.language_dialog_title);
    }
}
