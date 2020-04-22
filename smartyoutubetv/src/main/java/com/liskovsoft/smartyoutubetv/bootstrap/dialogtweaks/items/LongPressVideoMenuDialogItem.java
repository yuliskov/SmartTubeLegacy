package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class LongPressVideoMenuDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public LongPressVideoMenuDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_hold_ok_video_menu), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableVideoMenu();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableVideoMenu(checked);
    }
}
