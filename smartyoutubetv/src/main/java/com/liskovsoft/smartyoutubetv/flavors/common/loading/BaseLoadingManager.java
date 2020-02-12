package com.liskovsoft.smartyoutubetv.flavors.common.loading;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

public abstract class BaseLoadingManager implements LoadingManager {
    private final Activity mContext;

    public BaseLoadingManager(Activity context) {
        mContext = context;
        checkHighContrast();
    }

    private void checkHighContrast() {
        boolean highContrastEnabled = CommonApplication.getPreferences().isHighContrastEnabled();
        if (highContrastEnabled && getRootView() != null) {
            //mContext.getWindow().setBackgroundDrawable(null);
            //mContext.getWindow().setBackgroundDrawableResource(R.color.youtube_background_high_contrast);
            getRootView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.youtube_background_high_contrast));
        }
    }

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
        checkHighContrast();
    }

    @Override
    public void hide() {
        new Handler(mContext.getMainLooper())
                .postDelayed(() -> getRootView().setVisibility(View.GONE), 500);
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
