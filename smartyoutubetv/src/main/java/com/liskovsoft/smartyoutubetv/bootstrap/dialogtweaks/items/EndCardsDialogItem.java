package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class EndCardsDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public EndCardsDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_end_cards), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableEndCards();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setEnableEndCards(checked);
    }
}
