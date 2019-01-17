package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class UpdateCheckDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public UpdateCheckDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_update_check), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
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
