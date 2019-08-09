package com.liskovsoft.smartyoutubetv.misc.versiontracker;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.ArrayList;

public class AppVersionTrackerBase {
    private static final String TAG = AppVersionTracker.class.getSimpleName();
    private final ArrayList<Handler> mHandlers;

    public AppVersionTrackerBase() {
        mHandlers = new ArrayList<>();
    }

    public void run() {
        SmartPreferences prefs = CommonApplication.getPreferences();
        int code = prefs.getPreviousAppVersionCode();

        for (Handler handler : mHandlers) {
            handler.onBoot();

            if (code != BuildConfig.VERSION_CODE) {
                prefs.setPreviousAppVersionCode(BuildConfig.VERSION_CODE);

                if (code != 0) {
                    // put cleanup logic here
                    // FileHelpers.deleteCache(mContext);
                    Log.d(TAG, "App has been updated... Clearing cache...");

                    handler.onUpdate();
                } else {
                    // put restore logic here
                    Log.d(TAG, "App just installed... Restoring data...");

                    handler.onInstall();
                }
            }
        }
    }

    public void addHandler(Handler handler) {
        mHandlers.add(handler);
    }

    public void removeHandler(Handler handler) {
        mHandlers.remove(handler);
    }

    public static abstract class Handler {
        public void onUpdate() {
        }

        public void onInstall() {
        }

        public void onBoot() {
        }
    }
}
