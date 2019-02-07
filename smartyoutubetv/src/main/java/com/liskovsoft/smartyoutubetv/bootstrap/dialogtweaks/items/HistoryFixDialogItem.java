package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class HistoryFixDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public HistoryFixDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_history_fix), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableHistoryFix();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableHistoryFix(checked);
    }
}
