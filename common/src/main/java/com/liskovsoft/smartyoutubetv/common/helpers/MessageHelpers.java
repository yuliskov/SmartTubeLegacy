package com.liskovsoft.smartyoutubetv.common.helpers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class MessageHelpers {
    public static void showMessage(final Context ctx, final String TAG, final Throwable ex) {
        showMessage(ctx, TAG, Helpers.toString(ex));
    }

    public static void showMessage(final Context ctx, final String TAG, final String msg) {
        showMessage(ctx, String.format("%s: %s", TAG, msg));
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
