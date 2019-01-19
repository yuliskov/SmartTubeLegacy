package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class UnplayableVideoFixDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public UnplayableVideoFixDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_unplayable_video_fix), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getUnplayableVideoFix();
    }

    @Override
    public void setChecked(boolean checked) {
        mPrefs.setUnplayableVideoFix(checked);
    }
}
