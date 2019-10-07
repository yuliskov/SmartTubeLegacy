package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.smartyoutubetv.R;

import java.util.Random;

public class RandomTipsLoadingManager extends BaseLoadingManager implements Runnable {
    private final Activity mContext;
    private final Random mRandom;
    private final boolean mShowTips;
    private final Handler mHandler;
    private static final long UPDATE_INTERVAL = 3_000;

    private final int[] mTips = {
            R.string.tip_show_main_screen,
            R.string.tip_solve_problem,
            R.string.tip_disable_updates,
            R.string.tip_hash_in_subscriptions
    };

    public RandomTipsLoadingManager(Activity context) {
        super(context);
        mContext = context;
        mRandom = new Random(System.currentTimeMillis());
        mShowTips = AppInfoHelpers.isActivityExists(mContext, "BootstrapActivity");

        mHandler = new Handler(context.getMainLooper());
    }

    private void showRandomTip() {
        int next = mRandom.nextInt(mTips.length) & Integer.MAX_VALUE; // only positive ints
        showMessage(mTips[next]);
    }

    @Override
    public void show() {
        if (mShowTips) {
            showRandomTip();
            mHandler.postDelayed(this, UPDATE_INTERVAL);
        }

        super.show();
    }

    @Override
    public void hide() {
        if (mShowTips) {
            mHandler.removeCallbacks(this);
        }

        super.hide();
    }

    @Override
    public void run() {
        showRandomTip();
        mHandler.postDelayed(this, UPDATE_INTERVAL);
    }
}
