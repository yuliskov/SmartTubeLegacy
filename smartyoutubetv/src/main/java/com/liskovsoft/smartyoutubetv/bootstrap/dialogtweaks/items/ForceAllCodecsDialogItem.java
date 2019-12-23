package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class ForceAllCodecsDialogItem extends DialogItem {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public ForceAllCodecsDialogItem(Context context) {
        super(context.getResources().getString(R.string.force_all_codecs_lite_only), false);

        mContext = context;
        mPrefs = CommonApplication.getPreferences();
    }

    @Override
    public boolean getChecked() {
        return SmartPreferences.ALL_CODECS.equals(mPrefs.getPreferredCodec());
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setPreferredCodec(SmartPreferences.ALL_CODECS);
        } else {
            mPrefs.setPreferredCodec(SmartPreferences.NONE);
        }
    }
}
