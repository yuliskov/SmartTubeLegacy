package com.liskovsoft.sharedutils.mylogger;

import android.content.Context;

public class Log {
    public static final int LOG_TYPE_FILE = 0;
    public static final int LOG_TYPE_SYSTEM = 1;

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

    public static void init(Context context, int logType) {
        switch (logType) {
            case LOG_TYPE_FILE:
                sLogger = new FileLogger(context);
                break;
            case LOG_TYPE_SYSTEM:
                sLogger = new SystemLogger();
                break;
        }
    }
}
