package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
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
        return SmartPreferences.AFR_FIX_STATE_ENABLED.equals(mPrefs.getAfrFixState());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setAfrFixState(checked ? SmartPreferences.AFR_FIX_STATE_ENABLED : SmartPreferences.AFR_FIX_STATE_DISABLED);
    }
}
