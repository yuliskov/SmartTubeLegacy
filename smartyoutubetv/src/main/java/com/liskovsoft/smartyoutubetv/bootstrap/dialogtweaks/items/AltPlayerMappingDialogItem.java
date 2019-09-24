package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class AltPlayerMappingDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public AltPlayerMappingDialogItem(Context context) {
        super(context.getResources().getString(R.string.alt_player_mapping), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getAltPlayerMappingEnabled();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setAltPlayerMappingEnabled(checked);
    }
}
