package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class SaveSelectionDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public SaveSelectionDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_remember_launcher), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getBootstrapSaveSelection();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootstrapSaveSelection(checked);
    }
}
