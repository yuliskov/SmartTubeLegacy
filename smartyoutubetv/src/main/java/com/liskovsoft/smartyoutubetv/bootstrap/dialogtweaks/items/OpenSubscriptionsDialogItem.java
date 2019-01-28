package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class OpenSubscriptionsDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public OpenSubscriptionsDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_open_subscriptions), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.SUBSCRIPTIONS_PAGE.endsWith(mPrefs.getBootPage());
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setBootPage(checked ? SmartPreferences.SUBSCRIPTIONS_PAGE: SmartPreferences.DEFAULT_PAGE);
    }
}
