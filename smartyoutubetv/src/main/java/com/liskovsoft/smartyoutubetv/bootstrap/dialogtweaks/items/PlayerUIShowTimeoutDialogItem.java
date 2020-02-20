package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class PlayerUIShowTimeoutDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public PlayerUIShowTimeoutDialogItem(Context context) {
        super(context.getResources().getString(R.string.player_ui_show_timeout), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getDecreasePlayerUITimeout();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setDecreasePlayerUITimeout(checked);
    }
}
