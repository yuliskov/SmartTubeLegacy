package com.liskovsoft.sharedutils.mylogger;

import android.content.Context;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public class Log {
    private static MyLogger sLogger = new SystemLogger();

    public static void d(String tag, Object msg) {
        sLogger.d(tag, msg.toString());
    }

    public static void i(String tag, Object msg) {
        sLogger.i(tag, msg.toString());
    }

    public static void w(String tag, Object msg) {
        sLogger.w(tag, msg.toString());
    }

    public static void e(String tag, Object msg) {
        sLogger.e(tag, msg.toString());
    }

    public static void i(String tag, Object msg, Throwable ex) {
        i(tag, msg + " " + ex.getMessage());
    }


    public static void e(String tag, Object msg, Throwable ex) {
        e(tag, msg + " " + ex.getMessage());
    }

    public static void d(String tag, Object msg, Throwable ex) {
        d(tag, msg + " " + ex.getMessage());
    }

    public static void w(String tag, Object msg, Throwable ex) {
        w(tag, msg + " " + ex.getMessage());
    }

    /**
     * In case of file, flushes all data to disk
     */
    public static void flush() {
        sLogger.flush();
    }

    public static void init(Context context) {
        SmartPreferences prefs = SmartPreferences.instance(context);

        if (prefs.getEnableLogToFile()) {
            sLogger = new FileLogger(context);
        } else {
            sLogger = new SystemLogger();
        }
    }
}
