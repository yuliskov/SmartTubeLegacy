package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

public class TipsLoadingManager extends BaseLoadingManager {
    private final Activity mContext;
    private final boolean mBootstrapExists;
    private final Handler mHandler;
    private final boolean mHideTips;
    private final boolean mHideTips2;

    public TipsLoadingManager(Activity context) {
        super(context);

        mContext = context;
        mBootstrapExists = AppInfoHelpers.isActivityExists(mContext, "BootstrapActivity");
        mHideTips = mContext.getIntent().getBooleanExtra(LoadingManager.HIDE_TIPS, false);
        mHideTips2 = CommonApplication.getPreferences().getHideBootTips();
        mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void show() {
        if (mBootstrapExists && !(mHideTips || mHideTips2)) {
            showMessage(R.string.tip_show_main_screen_2);
        }

        super.show();
    }
}
