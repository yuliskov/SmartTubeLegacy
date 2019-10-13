package com.liskovsoft.smartyoutubetv.flavors.common;

import android.app.Activity;
import android.content.Intent;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.lang.Thread.UncaughtExceptionHandler;

public class GlobalExceptionHandler implements UncaughtExceptionHandler {
    private static final String TAG = GlobalExceptionHandler.class.getSimpleName();
    private final Activity mContext;
    private UncaughtExceptionHandler mOldHandler;

    public GlobalExceptionHandler(Activity context) {
        mContext = context;
    }
    
    public void onInit() {
        mOldHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        //Do your own error handling here

        Log.d(TAG, "Oops... uncaught exception happens.. ");

        Intent intent = new Intent(mContext, CrashHandlerActivity.class);
        mContext.startActivity(intent);

        if (mOldHandler != null) {
            mOldHandler.uncaughtException(paramThread, paramThrowable); //Delegates to Android's error handling
        } else {
            System.exit(0); // If you don't kill the VM here the app goes into limbo
        }

        //android.os.Process.killProcess(android.os.Process.myPid());
    }
}
