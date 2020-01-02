package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class UseExternalPlayer4KDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public UseExternalPlayer4KDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_open_externally) + " (4K)", false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.USE_EXTERNAL_PLAYER_4K.equals(mPrefs.getUseExternalPlayer());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setUseExternalPlayer(checked ? SmartPreferences.USE_EXTERNAL_PLAYER_4K : SmartPreferences.USE_EXTERNAL_PLAYER_NONE);
    }
}
