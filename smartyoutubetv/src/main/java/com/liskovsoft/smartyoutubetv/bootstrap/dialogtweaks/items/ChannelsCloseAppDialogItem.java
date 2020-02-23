package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ChannelsCloseAppDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ChannelsCloseAppDialogItem(Context context) {
        super(context.getResources().getString(R.string.channels_close_app), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getChannelsCloseApp();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setChannelsCloseApp(checked);
    }
}
