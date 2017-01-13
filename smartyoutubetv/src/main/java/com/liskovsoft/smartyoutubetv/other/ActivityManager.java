package com.liskovsoft.smartyoutubetv.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

public class ActivityManager {
    private final Activity mActivity;

    public ActivityManager(Activity ctx) {
        mActivity = ctx;
    }

    public void startActivityForDevice() {
        Class<? extends Activity> activityClass = findBestMatchedActivityClass();

        startActivity(activityClass);

        mActivity.finish();
    }

    private Class<? extends Activity> findBestMatchedActivityClass() {
        // our china friend: Q1EN.2004 Hi3798MV100 - ???
        String[] xwlkDevices = {"Q1EN.2004", "Hi3798MV100", "MiBox_mini"};

        String model = Build.MODEL;
        Class<? extends Activity> activityClass;
        if (contains(model, xwlkDevices)) {
            activityClass = XWalkSmartYouTubeTVActivity.class;
        } else {
            activityClass = WebViewSmartYouTubeTVActivity.class;
        }
        return activityClass;
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(mActivity, activityClass);
        mActivity.startActivity(intent);
    }

    private boolean contains(String s, String[] arr) {
        for (String item : arr) {
            if (s.toLowerCase().contains(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
