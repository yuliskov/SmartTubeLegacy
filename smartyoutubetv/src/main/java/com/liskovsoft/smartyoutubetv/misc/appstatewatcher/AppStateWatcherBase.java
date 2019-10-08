package com.liskovsoft.smartyoutubetv.misc.appstatewatcher;

import android.content.Intent;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

import java.util.ArrayList;

public class AppStateWatcherBase {
    private static final String TAG = AppStateWatcher.class.getSimpleName();
    private final ArrayList<StateHandler> mHandlers;

    public AppStateWatcherBase() {
        mHandlers = new ArrayList<>();
    }

    public void run() {
        SmartPreferences prefs = CommonApplication.getPreferences();
        int code = prefs.getPreviousAppVersionCode();

        for (StateHandler handler : mHandlers) {
            handler.onInit();

            if (code != BuildConfig.VERSION_CODE) {
                prefs.setPreviousAppVersionCode(BuildConfig.VERSION_CODE);

                if (code != 0) {
                    // put cleanup logic here
                    // FileHelpers.deleteCache(mContext);
                    Log.d(TAG, "App has been updated... Clearing cache, doing backup...");

                    handler.onUpdate();
                } else {
                    // put restore logic here
                    Log.d(TAG, "App just installed... Restoring data...");

                    handler.onFirstRun();
                }
            }
        }
    }

    public void addHandler(StateHandler handler) {
        mHandlers.add(handler);
    }

    public void removeHandler(StateHandler handler) {
        mHandlers.remove(handler);
    }

    public void onLoad() {
        Log.d(TAG, "App has been loaded... Calling handlers...");

        for (StateHandler handler : mHandlers) {
            handler.onLoad();
        }
    }

    public void onNewIntent(Intent intent) {
        for (StateHandler handler : mHandlers) {
            handler.onNewIntent(intent);
        }
    }

    public static abstract class StateHandler {
        public void onNewIntent(Intent intent) {
            
        }

        public void onUpdate() {
        }

        public void onFirstRun() {
        }

        public void onInit() {
        }

        public void onLoad() {
            
        }
    }
}
