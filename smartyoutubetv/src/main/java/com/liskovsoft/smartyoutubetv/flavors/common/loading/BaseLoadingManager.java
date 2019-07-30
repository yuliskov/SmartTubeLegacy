package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

public abstract class BaseLoadingManager implements LoadingManager {
    private final Activity mContext;

    public BaseLoadingManager(Activity context) {
        mContext = context;
    }

    @Override
    public abstract void showRandomTip();

    /**
     * Dynamically load main view since it may not be initialized yet.
     * @return root view
     */
    private View getRootView() {
        return mContext.findViewById(R.id.loading_main);
    }

    @Override
    public void show() {
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        new Handler(mContext.getMainLooper())
                .postDelayed(() -> {
                    getRootView().setVisibility(View.GONE);
                }, 500);
    }

    @Override
    public void showMessage(int resId) {
        String msg = mContext.getResources().getString(resId);
        showMessage(msg);
    }

    @Override
    public void showMessage(String message) {
        TextView msgView = getRootView().findViewById(R.id.loading_message);
        msgView.setText(message);
    }
}
