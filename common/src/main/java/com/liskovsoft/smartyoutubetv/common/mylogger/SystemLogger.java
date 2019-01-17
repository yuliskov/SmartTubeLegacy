package com.liskovsoft.smartyoutubetv.common.mylogger;

import android.util.Log;

class SystemLogger extends MyLogger {
    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
