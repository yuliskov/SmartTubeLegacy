package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class FixAspectDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public FixAspectDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_fix_aspect), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getFixAspectRatio();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setFixAspectRatio(checked);
    }
}
