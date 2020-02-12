package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import androidx.core.content.ContextCompat;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.misc.OldPackageRemover;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;

public abstract class BootstrapActivityBase extends ExceptionBootstrapActivity {
    private static final String TAG = BootstrapActivityBase.class.getSimpleName();

    @Override
    protected void onCreate(Bundle icicle) {
        setupLang();

        super.onCreate(icicle);

        initLogger();
        setupCrashLogs();
        setupFonSize();
        makeActivityFullscreen();
        makeActivityHorizontal();
        hideTitleBar();
        uninstallOldVersions();
    }

    private void initLogger() {
        SmartPreferences prefs = SmartPreferences.instance(this);
        Log.init(this, prefs.getLogType(), AppInfoHelpers.getActivityLabel(getApplicationContext(), prefs.getBootActivityName()));
    }

    private void makeActivityHorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    private void hideTitleBar() {
        // NOTE: we must set theme before ui instantiation
        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    private void makeActivityFullscreen() {
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        if (VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void setupFonSize() {
        Helpers.adjustFontScale(getResources().getConfiguration(), this);
    }

    private void setupLang() {
        new LangUpdater(this).update();
    }

    private void uninstallOldVersions() {
        OldPackageRemover remover = new OldPackageRemover(this);
        remover.remove();
    }
}