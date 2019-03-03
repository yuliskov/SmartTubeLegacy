package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.prefs.CommonParams;

import java.util.List;

public class OldPackageRemover {
    private final Context mContext;

    public OldPackageRemover(Context context) {
        mContext = context;
    }

    public void remove() {
        CommonParams params = CommonParams.instance(mContext);
        String[] names = params.getOldPackageNames();

        if (names == null) {
            return;
        }

        for (String name : names) {
            List<String> pkgNames = Helpers.findPackagesByPrefix(mContext, name); // use prefix to remove kids and live versions too

            for (String pkgName : pkgNames) {
                MessageHelpers.showLongMessage(mContext, R.string.toast_remove_old_package);
                Helpers.removePackage(mContext, pkgName);
            }
        }
    }
}
