package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.content.Intent;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivityBase;

public class SmartYouTubeTVActivity extends SmartYouTubeTVActivityBase {
    private OnActivityResultListener mOnActivityResultListener;

    public interface OnActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public SmartYouTubeTVActivity() {
        // we must set engine type as early as possible
        Browser.setEngineType(EngineType.WebView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mOnActivityResultListener != null) {
            mOnActivityResultListener.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        mOnActivityResultListener = onActivityResultListener;
    }
}
