package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class SaveSelectionDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;

    public SaveSelectionDialogItem(String title, SmartPreferences prefs) {
        super(title, false);

        mPrefs = prefs;
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getBootstrapSaveSelection();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootstrapSaveSelection(checked);
    }
}
