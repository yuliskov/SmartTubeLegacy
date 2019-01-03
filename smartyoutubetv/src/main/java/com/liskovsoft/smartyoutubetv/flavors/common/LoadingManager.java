package com.liskovsoft.smartyoutubetv.flavors.common;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;

public class LoadingManager {
    private final Activity mContext;

    public LoadingManager(Activity context) {
        mContext = context;
    }

    /**
     * Dynamically load main view since it may not be initialized yet.
     * @return root view
     */
    private View getRootView() {
        return mContext.findViewById(R.id.loading_main);
    }

    public void show() {
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

    public void setMessage(String message) {
        TextView text = getRootView().findViewById(R.id.loading_message);
        text.setText(message);
    }
}
