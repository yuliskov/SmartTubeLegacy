package com.liskovsoft.smartyoutubetv.misc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;

public class SmartUtils {
    @SuppressLint("WrongConstant")
    public static void returnToLaunchersDialog(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, BootstrapActivity.class);
        intent.putExtra(BootstrapActivity.SKIP_RESTORE, true);

        if (Helpers.isActivityExists(intent, context)) {
            context.startActivity(intent);
            context.finish();
        }
    }

    public static void restartToBootstrap(Context context) {
        restartApp(context, true);
    }

    public static void restartApp(Context context) {
        restartApp(context, false);
    }

    private static void restartApp(Context context, boolean toBootstrap) {
        // give a time to settings to compareAndApply
        new Handler(context.getMainLooper()).postDelayed(() -> SmartUtils.restart(context, toBootstrap), 1_000);
    }

    private static void restart(Context context, boolean toBootstrap) {
        Intent intent = new Intent();
        intent.setClass(context, BootstrapActivity.class);

        if (toBootstrap) {
            intent.putExtra(BootstrapActivity.SKIP_RESTORE, true);
        }

        context.startActivity(intent);

        System.exit(0);
    }

    public static boolean isBolshoeTV() {
        return BuildConfig.FLAVOR.equals("Vbolshoetv");
    }
}
