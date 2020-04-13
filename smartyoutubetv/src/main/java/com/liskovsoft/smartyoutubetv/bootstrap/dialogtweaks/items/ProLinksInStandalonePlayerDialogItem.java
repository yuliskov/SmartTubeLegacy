package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProLinksInStandalonePlayerDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProLinksInStandalonePlayerDialogItem(Context context) {
        super("PRO: " + context.getResources().getString(R.string.links_in_standalone_player), false);

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
