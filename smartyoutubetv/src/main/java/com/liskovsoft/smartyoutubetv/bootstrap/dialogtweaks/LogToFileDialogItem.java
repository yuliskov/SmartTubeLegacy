package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class LogToFileDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;

    public LogToFileDialogItem(String title, SmartPreferences prefs) {
        super(title, false);

        mPrefs = prefs;
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableLogToFile();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableLogToFile(checked);
    }
}
