package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class UpdateCheckDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;

    public UpdateCheckDialogItem(String title, SmartPreferences prefs) {
        super(title, false);

        mPrefs = prefs;
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getBootstrapUpdateCheck();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootstrapUpdateCheck(checked);
    }
}
