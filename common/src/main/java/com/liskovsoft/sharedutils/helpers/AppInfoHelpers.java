package com.liskovsoft.sharedutils.helpers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppInfoHelpers {
    public static String getAppVersion(Context context) {
        return formatAppVersion(getAppVersionNum(context), getActivityLabel(context));
    }

    public static String getAppVersionRobust(Context context, String launchActivityName) {
        return formatAppVersion(getAppVersionNum(context), getActivityLabelRobust(context, launchActivityName));
    }

    private static String formatAppVersion(String version, String label) {
        return String.format("%s (%s)", version, label);
    }

    public static String getActivityLabelRobust(Context context, String launchActivityName) {
        return getActivityLabel(context, launchActivityName);
    }

    public static String getAppVersionNum(Context context) {
        String versionName = null;

        try {
            versionName = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    public static String getActivityLabel(Context context) {
        return getActivityLabel(context, (String) null);
    }

    public static String getActivityLabel(Context context, String cls) {
        if (cls != null) {
            return getActivityLabel(context, new ComponentName(context, cls));
        } else if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return getActivityLabel(context, activity.getComponentName());
        }

        return null;
    }

    private static String getActivityLabel(Context context, ComponentName name) {
        PackageManager pm = context.getPackageManager();

        try {
            ActivityInfo info = pm.getActivityInfo(name, 0);
            return context.getResources().getString(info.labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
