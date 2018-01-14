package edu.mit.mobile.android.appupdater.addons;

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

    public static String toString(final Throwable ex) {
        return String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage());
    }

    public static void showMessage(final Context ctx, final Throwable ex) {
        showMessage(ctx, toString(ex));
    }

    public static void showMessage(final Context ctx, final Throwable ex, final String TAG) {
        showMessage(ctx, String.format("%s: %s", TAG, toString(ex)));
    }

    public static void showMessage(final Context ctx, final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
