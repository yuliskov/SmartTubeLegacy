package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class EnableAdBlockDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public EnableAdBlockDialogItem(Context context) {
        super(context.getResources().getString(R.string.enable_ad_block), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.AD_BLOCK_ENABLED.equals(mPrefs.getAdBlockStatus());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setAdBlockStatus(checked ? SmartPreferences.AD_BLOCK_ENABLED : SmartPreferences.AD_BLOCK_DISABLED);
    }
}
