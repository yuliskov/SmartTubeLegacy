package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

public class TipsLoadingManager extends BaseLoadingManager {
    private final Activity mContext;
    private final boolean mBootstrapExists;
    private final Handler mHandler;
    private final boolean mHideTips;

    public TipsLoadingManager(Activity context) {
        super(context);

        mContext = context;
        mBootstrapExists = AppInfoHelpers.isActivityExists(mContext, "BootstrapActivity");
        mHideTips = mContext.getIntent().getBooleanExtra(LoadingManager.HIDE_TIPS, false);
        mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void show() {
        if (mBootstrapExists && !mHideTips) {
            showMessage(R.string.tip_show_main_screen_2);
        }

        super.show();
    }
}
