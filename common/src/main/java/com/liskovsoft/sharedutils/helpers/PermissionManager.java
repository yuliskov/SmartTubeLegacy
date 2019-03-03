package com.liskovsoft.sharedutils.helpers;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

@TargetApi(16)
public class PermissionManager {
    // Storage Permissions
    public static final int REQUEST_EXTERNAL_STORAGE = 112;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage<br/>
     * If the app does not has permission then the user will be prompted to grant permissions<br/>
     * NOTE: runs async
     *
     * @param activity to apply permissions to
     */
    public static void verifyStoragePermissions(Activity activity) {
        if (!hasStoragePermissions(activity)) {
            requestStoragePermissions(activity);
        }
    }

    /**
     * Shows permissions dialog<br/>
     * NOTE: runs async
     * @param activity context
     */
    private static void requestStoragePermissions(Activity activity) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }

    /**
     * Only check. There is no prompt.
     * @param activity to apply permissions to
     * @return whether permission already granted
     */
    public static boolean hasStoragePermissions(Context activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Check if we have write permission
            return hasPermissions(activity, PERMISSIONS_STORAGE);
        }

        return true;
    }

    /**
     * Only check. There is no prompt.
     * @param activity to apply permissions to
     * @return whether permission already granted
     */
    private static boolean hasPermissions(Context activity, String... permissions) {
        for (String permission : permissions) {
            int result = ActivityCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
