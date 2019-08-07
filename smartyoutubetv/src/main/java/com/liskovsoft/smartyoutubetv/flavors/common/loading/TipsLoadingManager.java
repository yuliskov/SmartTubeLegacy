package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.R;

import java.util.Random;

public class TipsLoadingManager extends BaseLoadingManager {
    private final Activity mContext;
    private final Random mRandom;
    private final boolean mShowTips;

    private final int[] mTips = {
            R.string.tip_show_main_screen,
            R.string.tip_solve_problem,
            R.string.tip_disable_updates
    };

    public TipsLoadingManager(Activity context) {
        super(context);
        mContext = context;
        mRandom = new Random(System.currentTimeMillis());
        mShowTips = AppInfoHelpers.isActivityExists(mContext, "BootstrapActivity");
    }

    @Override
    public void showRandomTip() {
        int next = mRandom.nextInt(mTips.length) & Integer.MAX_VALUE; // only positive ints
        showMessage(mTips[next]);
    }

    @Override
    public void show() {
        if (mShowTips) {
            showRandomTip();
        }

        super.show();
    }
}
