package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

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
        return mPrefs.getBootstrapBetaUpdateCheck();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootstrapBetaUpdateCheck(checked);
    }
}
