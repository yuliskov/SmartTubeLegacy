package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.ArrayList;

public class AppVersionChecker {
    private static final String TAG = AppVersionChecker.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<Handler> mHandlers;

    public interface Handler {
        void onUpdate();
        void onInstall();
    }

    public AppVersionChecker(Context context) {
        mContext = context;
        mHandlers = new ArrayList<>();
    }

    public void run() {
        SmartPreferences prefs = CommonApplication.getPreferences();
        int code = prefs.getPreviousAppVersionCode();

        if (code != BuildConfig.VERSION_CODE) {
            prefs.setPreviousAppVersionCode(BuildConfig.VERSION_CODE);

            for (Handler handler : mHandlers) {
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
}
