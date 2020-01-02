package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class UseExternalPlayerSDDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public UseExternalPlayerSDDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_open_externally) + " (SD)", false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.USE_EXTERNAL_PLAYER_SD.equals(mPrefs.getUseExternalPlayer());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setUseExternalPlayer(checked ? SmartPreferences.USE_EXTERNAL_PLAYER_SD : SmartPreferences.USE_EXTERNAL_PLAYER_NONE);
    }
}
