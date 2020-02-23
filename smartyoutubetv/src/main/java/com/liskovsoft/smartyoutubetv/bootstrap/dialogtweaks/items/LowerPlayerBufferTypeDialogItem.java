package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class LowerPlayerBufferTypeDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public LowerPlayerBufferTypeDialogItem(Context context) {
        super(context.getResources().getString(R.string.lower_player_buffer), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getPlayerBufferType().equals(SmartPreferences.PLAYER_BUFFER_TYPE_LOW);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setPlayerBufferType(SmartPreferences.PLAYER_BUFFER_TYPE_LOW);
        } else {
            mPrefs.setPlayerBufferType(SmartPreferences.PLAYER_BUFFER_TYPE_MEDIUM);
        }
    }
}
