package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.R;

import java.util.Random;

public class TipsLoadingManager extends BaseLoadingManager {
    private final Activity mContext;
    private final boolean mShowTips;
    private final Handler mHandler;

    public TipsLoadingManager(Activity context) {
        super(context);

        mContext = context;
        mShowTips = AppInfoHelpers.isActivityExists(mContext, "BootstrapActivity");

        mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void show() {
        if (mShowTips) {
            showMessage(R.string.tip_show_main_screen_2);
        }

        super.show();
    }
}
