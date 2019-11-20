package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class Ugoos50HZFixDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public Ugoos50HZFixDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_ugoos_afr_fix), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getUgoos50HZFix();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setUgoos50HZFix(checked);
    }
}
