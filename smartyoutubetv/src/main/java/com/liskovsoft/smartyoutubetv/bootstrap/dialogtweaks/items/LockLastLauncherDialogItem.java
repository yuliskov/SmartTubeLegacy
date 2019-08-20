package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class LockLastLauncherDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public LockLastLauncherDialogItem(Context context) {
        super(context.getResources().getString(
                R.string.tweak_lock_last_launcher,
                AppInfoHelpers.getActivityLabelRobust(context, SmartPreferences.instance(context).getBootActivityName())),
                false);

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

        if (mContext instanceof BootstrapActivity) {
            ((BootstrapActivity) mContext).lockOtherLaunchers();
        }
    }
}
