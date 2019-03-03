package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class OkButtonDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public OkButtonDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_ok_button_fix), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableOKPause();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableOKPause(checked);
    }
}
