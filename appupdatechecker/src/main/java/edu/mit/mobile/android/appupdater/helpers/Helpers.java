package edu.mit.mobile.android.appupdater.helpers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.Closeable;
import java.io.IOException;

public class Helpers {
    public static void closeStream(Closeable fos) {
        try {
            if (fos != null)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String toString(Throwable ex) {
        if (ex instanceof IllegalStateException &&
                ex.getCause() != null) {
            ex = ex.getCause();
        }
        return String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage());
    }

    public static void showMessage(final Context ctx, final String TAG, final Throwable ex) {
        showMessage(ctx, TAG, toString(ex));
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
