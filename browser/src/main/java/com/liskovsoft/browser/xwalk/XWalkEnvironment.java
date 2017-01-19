// Copyright (c) 2016 Intel Corporation. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package com.liskovsoft.browser.xwalk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class XWalkEnvironment {
    private static final String TAG = "XWalkLib";

    private static final String META_XWALK_ENABLE_DOWNLOAD_MODE = "xwalk_enable_download_mode";
    private static final String META_XWALK_DOWNLOAD_MODE = "xwalk_download_mode";
    private static final String META_XWALK_DOWNLOAD_MODE_UPDATE = "xwalk_download_mode_update";
    private static final String META_XWALK_APK_URL = "xwalk_apk_url";
    private static final String META_XWALK_VERIFY = "xwalk_verify";

    private static final String PRIVATE_DATA_DIRECTORY_SUFFIX = "xwalkcore";
    private static final String XWALK_CORE_EXTRACTED_DIR = "extracted_xwalkcore";
    private static final String OPTIMIZED_DEX_DIR = "dex";
    private static final String PACKAGE_RE = "[a-z]+\\.[a-z0-9]+\\.[a-z0-9]+.*";

    private static Context sApplicationContext;

    private static String sDeviceAbi;
    private static String sRuntimeAbi;
    private static String sXWalkApkUrl;
    private static String sApplicationName;

    private static Boolean sIsDownloadMode;
    private static Boolean sIsDownloadModeUpdate;
    private static Boolean sIsXWalkVerify;

    public static void init(Context context) {
        sApplicationContext = context.getApplicationContext();
    }

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static SharedPreferences getSharedPreferences() {
        return sApplicationContext.getSharedPreferences("libxwalkcore", Context.MODE_PRIVATE);
    }

    public static String getPrivateDataDir() {
        return sApplicationContext.getDir(PRIVATE_DATA_DIRECTORY_SUFFIX,
                Context.MODE_PRIVATE).getAbsolutePath();
    }

    public static String getExtractedCoreDir() {
        return sApplicationContext.getDir(XWALK_CORE_EXTRACTED_DIR,
                Context.MODE_PRIVATE).getAbsolutePath();
    }

    public static String getOptimizedDexDir() {
        return sApplicationContext.getDir(OPTIMIZED_DEX_DIR,
                Context.MODE_PRIVATE).getAbsolutePath();
    }

    public static void setXWalkApkUrl(String url) {
        sXWalkApkUrl = url;
        Log.d(TAG, "Crosswalk APK download URL: " + sXWalkApkUrl);
    }

    public static String getXWalkApkUrl() {
        if (sXWalkApkUrl == null) {
            String url = getApplicationMetaData(META_XWALK_APK_URL);
            if (url == null) {
                sXWalkApkUrl = "";
            } else {
                String archQuery = "arch=" + getRuntimeAbi();
                try {
                    URI uri = new URI(url);
                    String query = uri.getQuery();
                    if (query == null) {
                        query = archQuery;
                    } else {
                        query += "&" + archQuery;
                    }

                    sXWalkApkUrl = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(),
                            query, uri.getFragment()).toString();
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Invalid xwalk_apk_url", e);
                }
            }
            Log.d(TAG, "Crosswalk APK download URL: " + sXWalkApkUrl);
        }
        return sXWalkApkUrl;
    }

    public static String getApplicationName() {
        if (sApplicationName == null) {
            try {
                PackageManager packageManager = sApplicationContext.getPackageManager();
                ApplicationInfo appInfo = packageManager.getApplicationInfo(
                        sApplicationContext.getPackageName(), 0);
                sApplicationName = (String) packageManager.getApplicationLabel(appInfo);
            } catch (PackageManager.NameNotFoundException e) {
            }

            if (sApplicationName == null || sApplicationName.isEmpty()
                    || sApplicationName.matches(PACKAGE_RE)) {
                sApplicationName = "this application";
            }
            Log.d(TAG, "Crosswalk application name: " + sApplicationName);
        }
        return sApplicationName;

    }

    public static boolean isDownloadMode() {
        if (sIsDownloadMode == null) {
            String enable = getApplicationMetaData(META_XWALK_DOWNLOAD_MODE);
            if (enable == null) {
                enable = getApplicationMetaData(META_XWALK_ENABLE_DOWNLOAD_MODE);
            }
            sIsDownloadMode = enable != null
                    && (enable.equalsIgnoreCase("enable") || enable.equalsIgnoreCase("true"));
            Log.d(TAG, "Crosswalk download mode: " + sIsDownloadMode);
        }
        return sIsDownloadMode;
    }

    public static boolean isDownloadModeUpdate() {
        if (sIsDownloadModeUpdate == null) {
            String enable = getApplicationMetaData(META_XWALK_DOWNLOAD_MODE_UPDATE);
            sIsDownloadModeUpdate = enable != null
                    && (enable.equalsIgnoreCase("enable") || enable.equalsIgnoreCase("true"));
            Log.d(TAG, "Crosswalk download mode update: " + sIsDownloadModeUpdate);
        }
        return sIsDownloadModeUpdate;
    }

    public static boolean isXWalkVerify() {
        if (sIsXWalkVerify == null) {
            String verify = getApplicationMetaData(META_XWALK_VERIFY);
            sIsXWalkVerify = verify == null
                    || (!verify.equalsIgnoreCase("disable") && !verify.equalsIgnoreCase("false"));
            Log.d(TAG, "Crosswalk verify: " + sIsXWalkVerify);
        }
        return sIsXWalkVerify;
    }

    public static boolean isIaDevice() {
        String abi = getDeviceAbi();
        return abi.equals("x86") || abi.equals("x86_64");
    }

    public static boolean is64bitDevice() {
        String abi = getDeviceAbi();
        return abi.equals("arm64-v8a") || abi.equals("x86_64");
    }

    public static boolean is64bitApp() {
        String abi = getRuntimeAbi();
        return abi.equals("arm64-v8a") || abi.equals("x86_64");
    }

    public static String getRuntimeAbi() {
        if (sRuntimeAbi == null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    throw new NoSuchFieldError();
                }
                String abi = Build.CPU_ABI.toLowerCase();
                switch (abi) {
                    case "armeabi":
                    case "armeabi-v7a":
                        sRuntimeAbi = "armeabi-v7a";
                        break;
                    case "arm64-v8a":
                        sRuntimeAbi = "arm64-v8a";
                        break;
                    case "x86":
                        sRuntimeAbi = "x86";
                        break;
                    case "x86_64":
                        sRuntimeAbi = "x86_64";
                        break;
                    default:
                        throw new RuntimeException("Unexpected CPU_ABI: " + abi);
                }
            } catch (NoSuchFieldError e) {
                String arch = System.getProperty("os.arch").toLowerCase();
                switch (arch) {
                    case "x86":
                    case "i686":
                    case "i386":
                    case "ia32":
                        sRuntimeAbi = "x86";
                        break;
                    case "x64":
                    case "x86_64":
                        if (is64bitDevice()) {
                            sRuntimeAbi = "x86_64";
                        } else {
                            sRuntimeAbi = "x86";
                        }
                        break;
                    case "armv7l":
                    case "armeabi":
                    case "armeabi-v7a":
                        sRuntimeAbi = "armeabi-v7a";
                        break;
                    case "aarch64":
                    case "armv8":
                    case "armv8l":
                    case "arm64":
                        if (is64bitDevice()) {
                            sRuntimeAbi = "arm64-v8a";
                        } else {
                            sRuntimeAbi = "armeabi-v7a";
                        }
                        break;
                    default:
                        throw new RuntimeException("Unexpected os.arch: " + arch);
                }
            }

            // The value may be ARM on Houdini devices.
            if (sRuntimeAbi.equals("armeabi-v7a")) {
                if (isIaDevice()) {
                    sRuntimeAbi = "x86";
                }
            } else if (sRuntimeAbi.equals("arm64-v8a")) {
                if (isIaDevice()) {
                    sRuntimeAbi = "x86_64";
                }
            }
            Log.d(TAG, "Runtime ABI: " + sRuntimeAbi);
        }
        return sRuntimeAbi;
    }

    public static String getDeviceAbi() {
        if (sDeviceAbi == null) {
            try {
                sDeviceAbi = Build.SUPPORTED_ABIS[0].toLowerCase();
            } catch (NoSuchFieldError e) {
                try {
                    Process process = Runtime.getRuntime().exec("getprop ro.product.cpu.abi");
                    InputStreamReader ir = new InputStreamReader(process.getInputStream());
                    BufferedReader input = new BufferedReader(ir);
                    sDeviceAbi = input.readLine().toLowerCase();
                    input.close();
                    ir.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Can not detect device's ABI");
                }
            }
            Log.d(TAG, "Device ABI: " + sDeviceAbi);
        }
        return sDeviceAbi;
    }

    private static String getApplicationMetaData(String name) {
        try {
            PackageManager packageManager = sApplicationContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(
                    sApplicationContext.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.get(name).toString();
        } catch (NameNotFoundException | NullPointerException e) {
            return null;
        }
    }
}
