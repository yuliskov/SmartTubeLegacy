package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class OpenHistoryDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public OpenHistoryDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_open_history), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.HISTORY_PAGE.equals(mPrefs.getBootPage());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootPage(checked ? SmartPreferences.HISTORY_PAGE : SmartPreferences.DEFAULT_PAGE);
    }
}
