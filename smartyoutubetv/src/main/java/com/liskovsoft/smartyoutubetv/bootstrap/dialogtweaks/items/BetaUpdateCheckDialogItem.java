package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class BetaUpdateCheckDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public BetaUpdateCheckDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_beta_update_check), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.UPDATE_CHECK_BETA.equals(mPrefs.getBootstrapUpdateCheck());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setBootstrapUpdateCheck(SmartPreferences.UPDATE_CHECK_BETA);
        } else {
            mPrefs.setBootstrapUpdateCheck(SmartPreferences.UPDATE_CHECK_DISABLED);
        }
    }
}
