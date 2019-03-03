package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

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
        return SmartPreferences.UPDATE_CHECK_STABLE.equals(mPrefs.getBootstrapUpdateCheck());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setBootstrapUpdateCheck(SmartPreferences.UPDATE_CHECK_STABLE);
        } else {
            mPrefs.setBootstrapUpdateCheck(SmartPreferences.UPDATE_CHECK_DISABLED);
        }
    }
}
