package com.liskovsoft.sharedutils.helpers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;

import java.util.Locale;
import java.util.StringTokenizer;

public class LangHelper {
    private static final String LOCALE_EN_US = "en_US";
    private static final String LOCALE_RU = "ru_RU";
    private static String[] rusPackages = {"dkc.androidtv.tree", "dkc.video.fsbox", "dkc.video.hdbox", "dkc.video.uatv"};

    public static String guessLocale(Context context) {
        if (isRussianPackagesInstalled(context)) {
            return LOCALE_RU;
        }

        if (isChineseDevice()) {
            return LOCALE_EN_US;
        }

        return null;
    }

    private static boolean isRussianPackagesInstalled(Context context) {
        for (ApplicationInfo info : Helpers.getInstalledPackages(context)) {
            if (isRussianPackage(info.packageName)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isRussianPackage(String pkgName) {
        for (String rusPackage : rusPackages) {
            if (rusPackage.equals(pkgName)){
                return true;
            }
        }
        return false;
    }

    private static boolean isChineseDevice() {
        String deviceName = Helpers.getDeviceName();

        switch (deviceName) {
            case "ChangHong Android TV (full_mst638)":
                return true;
        }

        return false;
    }

    private static boolean isChineseLocale(Context context) {
        String script = LocaleUtility.getScript(Locale.getDefault());

        if (isChineseScript(script)) {
            return true;
        }

        return false;
    }

    private static boolean isChineseScript(String script) {
        switch (script) {
            case "Hani":
            case "Hans":
            case "Hant":
                return true;
        }
        return false;
    }

    // short lang code. ex: "ru"
    public static void forceLocale(Context context, String langCode) {
        if (langCode == null || langCode.isEmpty()) {
            return;
        }

        Locale locale = parseLangCode(langCode);
        Locale oldLocale = Locale.getDefault();
        if (oldLocale.equals(locale)) {
            return;
        }

        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources()
                .updateConfiguration(
                        config,
                        context.getResources().getDisplayMetrics()
                );
    }

    private static Locale parseLangCode(String langCode) {
        StringTokenizer tokenizer = new StringTokenizer(langCode, "_");
        String lang = tokenizer.nextToken();
        String country = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        return new Locale(lang, country);
    }
}
