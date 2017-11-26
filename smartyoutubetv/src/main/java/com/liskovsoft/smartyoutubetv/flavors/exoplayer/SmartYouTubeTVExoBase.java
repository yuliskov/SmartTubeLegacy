package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.content.Intent;
import com.liskovsoft.smartyoutubetv.SmartYouTubeTVActivityBase;

// marker class
public class SmartYouTubeTVExoBase extends SmartYouTubeTVActivityBase {
    private OnActivityResultListener mOnActivityResultListener;

    public interface OnActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
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
