package com.liskovsoft.smartyoutubetv.flavors.common;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;

import java.util.Random;

public class LoadingManager {
    private final Activity mContext;
    private final Random mRandom;

    private final int[] mTips = {
            R.string.tip_show_main_screen,
            R.string.tip_solve_problem,
            R.string.tip_disable_updates
    };

    public LoadingManager(Activity context) {
        mContext = context;
        mRandom = new Random(System.currentTimeMillis());
    }

    private void showRandomTip() {
        int next = mRandom.nextInt(mTips.length) & Integer.MAX_VALUE; // only positive ints
        showMessage(mTips[next]);
    }

    /**
     * Dynamically load main view since it may not be initialized yet.
     * @return root view
     */
    private View getRootView() {
        return mContext.findViewById(R.id.loading_main);
    }

    public void show() {
        showRandomTip();
        getRootView().setVisibility(View.VISIBLE);
    }

    public void hide() {
        new Handler(mContext.getMainLooper())
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRootView().setVisibility(View.GONE);
                    }
                }, 500);
    }

    public void showMessage(int resId) {
        String msg = mContext.getResources().getString(resId);
        showMessage(msg);
    }

    public void showMessage(String message) {
        TextView msgView = getRootView().findViewById(R.id.loading_message);
        msgView.setText(message);
    }
}
