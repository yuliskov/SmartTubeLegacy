package com.liskovsoft.smartyoutubetv.misc.appstatewatcher.handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.liskovsoft.sharedutils.dialogs.YesNoDialog;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class AdBlockPermissionsHandler extends StateHandler implements OnClickListener {
    private final SmartPreferences mPrefs;
    private final Context mContext;

    public AdBlockPermissionsHandler(Context context) {
        mPrefs = CommonApplication.getPreferences();
        mContext = context;
    }

    @Override
    public void onInit() {
        boolean undefined = SmartPreferences.AD_BLOCK_UNDEFINED.equals(mPrefs.getAdBlockStatus());
        if (undefined) {
            showPermissionsDialog();
        }
    }

    private void showPermissionsDialog() {
        YesNoDialog.create(mContext, R.string.ad_blocking_permissions, this, R.style.AppDialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                mPrefs.setAdBlockStatus(SmartPreferences.AD_BLOCK_ENABLED);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                mPrefs.setAdBlockStatus(SmartPreferences.AD_BLOCK_DISABLED);
                break;
        }
    }
}
