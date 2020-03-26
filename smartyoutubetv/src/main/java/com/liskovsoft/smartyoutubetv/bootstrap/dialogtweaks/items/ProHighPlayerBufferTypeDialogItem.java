package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProHighPlayerBufferTypeDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProHighPlayerBufferTypeDialogItem(Context context) {
        super(context.getResources().getString(R.string.high_player_buffer), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getPlayerBufferType().equals(SmartPreferences.PLAYER_BUFFER_TYPE_HIGH);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setPlayerBufferType(SmartPreferences.PLAYER_BUFFER_TYPE_HIGH);
        } else {
            mPrefs.setPlayerBufferType(SmartPreferences.PLAYER_BUFFER_TYPE_MEDIUM);
        }
    }
}
