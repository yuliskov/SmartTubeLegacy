package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class OkButtonDialogItem extends DialogItem {
    public OkButtonDialogItem(String title, SmartPreferences prefs) {
        super(title, false);
    }
}
