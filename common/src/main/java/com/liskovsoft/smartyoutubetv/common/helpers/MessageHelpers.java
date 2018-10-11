package com.liskovsoft.smartyoutubetv.common.helpers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class MessageHelpers {
    private static long sExitMsgTimeMS = 0;
    private static final int LONG_MSG_TIMEOUT = 5000;

    public static void showMessage(final Context ctx, final String TAG, final Throwable ex) {
        showMessage(ctx, TAG, Helpers.toString(ex));
    }

    public static void showMessage(final Context ctx, final String TAG, final String msg) {
        showMessage(ctx, String.format("%s: %s", TAG, msg));
    }

    public static void showMessageThrottled(final Context ctx, final String msg) {
        // throttle msg calls
        if (System.currentTimeMillis() - sExitMsgTimeMS < LONG_MSG_TIMEOUT) {
            return;
        }
        sExitMsgTimeMS = System.currentTimeMillis();
        showMessage(ctx, msg);
    }

    public static void showMessage(final Context ctx, final String msg) {
        if (ctx == null) {
            return;
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void showLongMessage(Context ctx, String TAG, String msg) {
        for (int i = 0; i < 3; i++) {
            showMessage(ctx, TAG, msg);
        }
    }
}
