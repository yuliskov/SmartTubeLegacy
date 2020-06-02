package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProOKPauseWithoutUIDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProOKPauseWithoutUIDialogItem(Context context) {
        super("PRO: " + context.getResources().getString(R.string.tweak_ok_pause_without_ui), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.OK_PAUSE_TYPE_WITHOUT_UI.equals(mPrefs.getOKPauseType());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setOKPauseType(SmartPreferences.OK_PAUSE_TYPE_WITHOUT_UI);
        } else {
            mPrefs.setOKPauseType(SmartPreferences.OK_PAUSE_TYPE_DEFAULT);
        }
    }
}
