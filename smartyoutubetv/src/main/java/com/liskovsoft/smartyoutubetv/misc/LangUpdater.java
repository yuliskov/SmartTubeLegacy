package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LangUpdater {
    private Context mContext;
    private String[] rusPackages = {"dkc.androidtv.tree", "dkc.video.fsbox", "dkc.video.hdbox", "dkc.video.uatv"};

    public LangUpdater(Context ctx) {
        mContext = ctx;
    }

    public void update() {
        tryToEnableRussian();
        tryToForceEnglishOnDevices();
        tryToRestoreLanguage();
    }

    private void tryToRestoreLanguage() {
        String langCode = SmartPreferences.instance(mContext).getPreferredLanguage();
        if (langCode != null) {
            forceLocale(langCode);
        }
    }

    private void tryToForceEnglishOnDevices() {
        String deviceName = Helpers.getDeviceName();
        switch (deviceName) {
            case "ChangHong Android TV (full_mst638)":
                forceLocale("en");
        }
    }

    private void tryToBypassChinese() {
        String script = LocaleUtility.getScript(Locale.getDefault());
        if (isChineseScript(script)) {
            forceLocale("en");
        }
    }

    private boolean isChineseScript(String script) {
        switch (script) {
            case "Hani":
            case "Hans":
            case "Hant":
                return true;
        }
        return false;
    }

    private void tryToEnableRussian() {
        List<String> installedPackages = getListInstalledPackages();
        for (String pkgName : installedPackages) {
            if (isRussianPackage(pkgName)) {
                forceLocale("ru");
            }
        }
    }

    // short lang code. ex: "ru"
    private void forceLocale(String langCode) {
        if (langCode == null || langCode.isEmpty()) {
            return;
        }

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = mContext.getResources().getConfiguration();
        config.locale = locale;
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());
    }

    private boolean isRussianPackage(String pkgName) {
        for (String rusPackage : rusPackages) {
            if (rusPackage.equals(pkgName)){
                return true;
            }
        }
        return false;
    }

    private List<String> getListInstalledPackages() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mContext.getPackageManager().queryIntentActivities( mainIntent, 0);
        List<String> result = new ArrayList<>();
        for (ResolveInfo info : pkgAppsList) {
            result.add(info.activityInfo.packageName);
        }
        return result;
    }

    //public String getLocale() {
    //    Configuration config = mContext.getResources().getConfiguration();
    //    return config.locale.getLanguage();
    //}

    public String getLocale() {
        return SmartPreferences.instance(mContext).getPreferredLanguage();
    }

    public void setLocale(String langCode) {
        SmartPreferences.instance(mContext).setPreferredLanguage(langCode);
    }
}
