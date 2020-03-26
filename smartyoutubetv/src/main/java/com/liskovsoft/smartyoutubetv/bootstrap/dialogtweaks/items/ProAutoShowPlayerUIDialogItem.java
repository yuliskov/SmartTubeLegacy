package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProAutoShowPlayerUIDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProAutoShowPlayerUIDialogItem(Context context) {
        super("PRO: " + context.getResources().getString(R.string.tweak_auto_show_player_ui), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getAutoShowPlayerUI();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setAutoShowPlayerUI(checked);
    }
}
