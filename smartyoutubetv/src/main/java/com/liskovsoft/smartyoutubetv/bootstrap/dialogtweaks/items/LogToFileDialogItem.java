package com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.items;

import android.content.Context;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

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
        return mPrefs.getLogType().equals(Log.LOG_TYPE_FILE);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mPrefs.setLogType(Log.LOG_TYPE_FILE);
        } else {
            mPrefs.setLogType(Log.LOG_TYPE_SYSTEM);
        }

        Context ctx = mContext.getApplicationContext();
        Log.init(ctx, mPrefs.getLogType(), AppInfoHelpers.getActivityLabel(ctx, mPrefs.getBootActivityName()));
    }
}
