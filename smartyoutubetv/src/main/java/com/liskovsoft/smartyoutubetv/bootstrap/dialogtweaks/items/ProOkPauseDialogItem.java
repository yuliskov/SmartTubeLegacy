package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class ProOkPauseDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProOkPauseDialogItem(Context context) {
        super("PRO: " + context.getResources().getString(R.string.tweak_press_ok_to_pause), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.OK_PAUSE_TYPE_WITH_UI.equals(mPrefs.getOKPauseType());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setOKPauseType(SmartPreferences.OK_PAUSE_TYPE_WITH_UI);
        } else {
            mPrefs.setOKPauseType(SmartPreferences.OK_PAUSE_TYPE_DEFAULT);
        }
    }
}
