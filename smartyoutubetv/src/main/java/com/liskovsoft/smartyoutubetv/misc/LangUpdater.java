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
        tryToSetupRussian();
    }

    private boolean tryToSetupRussian() {
        List<String> installedPackages = getListInstalledPackages();
        for (String pkgName : installedPackages) {
            if (isRussianPackage(pkgName)) {
                forceRussianLocale();
                return true;
            }
        }
        return false;
    }

    private void forceRussianLocale() {
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
        Configuration config = mContext.getResources().getConfiguration();
        config.locale = locale;
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());
    }

    //private void addRussianHeaders() {
    //    Map<String, String> headers = mPageDefaults.getHeaders();
    //    headers.put("Accept-Language", "ru,en-US;q=0.8,en;q=0.6");
    //}

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
}
