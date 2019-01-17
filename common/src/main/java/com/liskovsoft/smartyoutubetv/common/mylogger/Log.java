package com.liskovsoft.smartyoutubetv.common.mylogger;

public class Log {
    private static final int LOG_TYPE = LogTypes.SYSTEM;
    private static MyLogger sLogger;

    private class LogTypes {
        private static final int FILE = 1;
        private static final int SYSTEM = 2;
        private static final int BOTH = 3;
    }

    static {
        switch (LOG_TYPE) {
            case LogTypes.FILE:
                sLogger = new FileLogger();
                break;
            case LogTypes.SYSTEM:
                sLogger = new SystemLogger();
                break;
        }
    }

    public static void d(String tag, String msg) {
        sLogger.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        sLogger.i(tag, msg);
    }

    /**
     * In case of file, flushes all data to disk
     */
    public static void flush() {
        sLogger.flush();
    }
}
