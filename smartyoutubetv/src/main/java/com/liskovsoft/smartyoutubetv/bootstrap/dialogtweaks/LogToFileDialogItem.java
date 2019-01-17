package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.common.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class LogToFileDialogItem extends DialogItem {
    private final Context mContext;
    private final SmartPreferences mPrefs;

    public LogToFileDialogItem(Context context) {
        super(context.getResources().getString(R.string.tweak_log_to_file), false);

        mContext = context;
        mPrefs = SmartPreferences.instance(context);
    }

    @Override
    public boolean getChecked() {
        return mPrefs.getEnableLogToFile();
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            MessageHelpers.showLongMessage(mContext, R.string.log_stored_in_path);
        }

        mPrefs.setEnableLogToFile(checked);
    }
}
