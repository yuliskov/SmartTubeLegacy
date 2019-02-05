package com.liskovsoft.appbackupmanager.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.liskovsoft.appbackupmanager.Constants;

import java.util.Locale;

public class LanguageHelper
{
    // no need to change anything on start if default is choosen
    public static void initLanguage(Context context, String langCode)
    {
        if(!langCode.equals(Constants.PREFS_LANGUAGES_DEFAULT))
        {
            changeLanguage(context, langCode);
        }
    }
    public static boolean changeLanguage(Context context, String langCode)
    {
        if(!langCode.equals(Constants.PREFS_LANGUAGES_DEFAULT))
        {
            Resources res = context.getResources();
            Configuration conf = res.getConfiguration();
            String lang = conf.locale.getLanguage();
            String country = conf.locale.getCountry();
            // check if langCode is a regional language
            if(langCode.contains("_"))
            {
                String[] parts = langCode.split("_");
                conf.locale = new Locale(parts[0], parts[1]);
            }
            else
            {
                conf.locale = new Locale(langCode);
            }
            res.updateConfiguration(conf, res.getDisplayMetrics());
            // return true if language changed
            return (!langCode.equals(lang) || !conf.locale.getCountry().equals(country));
        }
        else
        {
            return changeLanguage(context, Locale.getDefault().getLanguage());
        }
    }
    public static void legacyKeepLanguage(Context context, String langCode)
    {
        // for some reason the locale changes back when PackageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES) is called on older apis (Process.waitFor() in shellCommands.checkSuperUser() does it too)
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            changeLanguage(context, langCode);
        }
    }
}
