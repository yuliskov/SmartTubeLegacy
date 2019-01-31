package com.liskovsoft.smartyoutubetv.misc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;

public class SmartUtils {
    @SuppressLint("WrongConstant")
    public static void returnToLaunchersDialog(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, BootstrapActivity.class);
        intent.putExtra(BootstrapActivity.SKIP_RESTORE, true);

        boolean activityExists = intent.resolveActivityInfo(context.getPackageManager(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) != null;

        if (activityExists) {
            context.startActivity(intent);
            context.finish();
        }
    }
}
