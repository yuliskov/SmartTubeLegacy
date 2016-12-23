package com.liskovsoft.browser;

import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundHandler {

    static HandlerThread sLooperThread;
    static ExecutorService mThreadPool;

    static {
        sLooperThread = new HandlerThread("BackgroundHandler", HandlerThread.MIN_PRIORITY);
        sLooperThread.start();
        mThreadPool = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable runnable) {
        mThreadPool.execute(runnable);
    }

    public static Looper getLooper() {
        return sLooperThread.getLooper();
    }

    private BackgroundHandler() {}
}
