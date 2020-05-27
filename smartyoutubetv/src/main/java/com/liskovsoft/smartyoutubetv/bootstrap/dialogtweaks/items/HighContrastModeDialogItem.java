package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class HighContrastModeDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public HighContrastModeDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_high_contrast_mode), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableHighContrastMode();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableHighContrastMode(checked);
    }
}
