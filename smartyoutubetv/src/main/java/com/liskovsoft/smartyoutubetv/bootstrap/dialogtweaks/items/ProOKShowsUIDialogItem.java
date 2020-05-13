package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProOKShowsUIDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProOKShowsUIDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_ok_shows_ui), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getOKShowsUI();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setOKShowsUI(checked);
    }
}
