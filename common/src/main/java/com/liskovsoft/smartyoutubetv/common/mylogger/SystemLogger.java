package com.liskovsoft.smartyoutubetv.common.mylogger;

import android.util.Log;

class SystemLogger extends MyLogger {
    private static MyLogger sLogger;

    private SystemLogger() {}

    public static MyLogger instance() {
        if (sLogger == null) {
            sLogger = new SystemLogger();
        }

        return sLogger;
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
