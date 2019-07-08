package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class BackPressExitDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public BackPressExitDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_back_press_exit), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableBackPressExit();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableBackPressExit(checked);
    }
}
