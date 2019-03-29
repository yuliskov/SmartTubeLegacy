package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

import java.util.Random;

public class TipsLoadingManager extends BaseLoadingManager {
    private final Activity mContext;
    private final Random mRandom;

    private final int[] mTips = {
            R.string.tip_show_main_screen,
            R.string.tip_solve_problem,
            R.string.tip_disable_updates
    };

    public TipsLoadingManager(Activity context) {
        super(context);
        mContext = context;
        mRandom = new Random(System.currentTimeMillis());
    }

    @Override
    public void showRandomTip() {
        int next = mRandom.nextInt(mTips.length) & Integer.MAX_VALUE; // only positive ints
        showMessage(mTips[next]);
    }

    /**
     * Dynamically load main view since it may not be initialized yet.
     * @return root view
     */
    private View getRootView() {
        return mContext.findViewById(R.id.loading_message);
    }

    @Override
    public void show() {
        if (CommonApplication.getFromBootstrap()) {
            showRandomTip();
            getRootView().setVisibility(View.VISIBLE);
        }

        super.show();
    }

    @Override
    public void hide() {
        if (CommonApplication.getFromBootstrap()) {
            new Handler(mContext.getMainLooper())
                    .postDelayed(() -> getRootView().setVisibility(View.GONE), 500);
        }

        super.hide();
    }
}
