package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class GlobalAfrFixDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public GlobalAfrFixDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_global_afx_fix), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.GLOBAL_AFR_FIX_STATE_ENABLED.equals(mPrefs.getGlobalAfrFixState());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            MessageHelpers.showMessage(mContext, R.string.global_afr_fix_warning);
        }

        mPrefs.setGlobalAfrFixState(checked ? SmartPreferences.GLOBAL_AFR_FIX_STATE_ENABLED : SmartPreferences.GLOBAL_AFR_FIX_STATE_DISABLED);
    }
}
