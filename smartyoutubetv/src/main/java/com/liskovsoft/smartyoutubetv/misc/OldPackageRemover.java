package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivityBase;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.common.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.common.prefs.CommonParams;

public class OldPackageRemover {
    private final Context mContext;

    public OldPackageRemover(Context context) {
        mContext = context;
    }

    public void remove() {
        CommonParams params = CommonParams.instance(mContext);
        String[] names = params.getOldPackageNames();
        for (String name : names) {
            if (Helpers.isPackageExists(mContext, name)) {
                MessageHelpers.showLongMessage(mContext, R.string.toast_remove_old_package);
                Helpers.removePackage(mContext, name);
            }
        }
    }
}
