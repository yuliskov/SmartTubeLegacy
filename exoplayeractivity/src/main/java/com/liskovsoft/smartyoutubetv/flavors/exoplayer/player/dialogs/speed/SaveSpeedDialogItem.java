package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.speed;

import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.MultiDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

public class SaveSpeedDialogItem extends MultiDialogItem {
    private final ExoPreferences mPrefs;

    public SaveSpeedDialogItem(String title, ExoPreferences prefs) {
        super(title, false);
        mPrefs = prefs;
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getRestoreSpeed();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setRestoreSpeed(checked);
    }
}
