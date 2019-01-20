package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class LockLastLauncherDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public LockLastLauncherDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_lock_last_launcher, AppInfoHelpers.getActivityLabelRobust(context)), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getLockLastLauncher();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setLockLastLauncher(checked);
    }
}
