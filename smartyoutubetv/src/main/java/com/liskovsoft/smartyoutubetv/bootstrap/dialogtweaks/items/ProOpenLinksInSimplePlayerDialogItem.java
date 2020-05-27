package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ProOpenLinksInSimplePlayerDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ProOpenLinksInSimplePlayerDialogItem(Context context) {
        super("PRO: " + context.getResources().getString(R.string.open_links_in_simple_view), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getOpenLinksInSimplePlayer();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setOpenLinksInSimpleView(checked);
    }
}
