package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProMediumPlayerBufferTypeDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProMediumPlayerBufferTypeDialogItem(Context context) {
        super(context.getResources().getString(R.string.medium_player_buffer), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getPlayerBufferType().equals(SmartPreferences.PLAYER_BUFFER_TYPE_MEDIUM);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setPlayerBufferType(SmartPreferences.PLAYER_BUFFER_TYPE_MEDIUM);
        } else {
            mPrefs.setPlayerBufferType(SmartPreferences.PLAYER_BUFFER_TYPE_MEDIUM);
        }
    }
}
