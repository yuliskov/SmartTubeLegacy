package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.misc.SmartPreferences;
import com.liskovsoft.smartyoutubetv.widgets.BootstrapCheckBox;
import io.fabric.sdk.android.Fabric;

public class BootstrapActivity extends ActivityBase {
    public static final String FROM_BOOTSTRAP = "FROM_BOOTSTRAP";
    public static final String DO_NOT_RESTORE = "doNotRestore";
    private SmartPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // do it before view instantiation
        initPrefs();
        setupLang();
        tryToRestoreLastActivity();

        super.onCreate(savedInstanceState);
        initLayout();

        setupCrashLogs();
    }

    private void initPrefs() {
        if (mPrefs == null) {
            mPrefs = SmartPreferences.instance(this);
        }
    }

    private void initLayout() {
        setContentView(R.layout.activity_bootstrap);
        boolean isChecked = mPrefs.getBootstrapSaveSelection();
        BootstrapCheckBox chkbox = (BootstrapCheckBox) findViewById(R.id.chk_save_selection);
        chkbox.setChecked(isChecked);
    }

    public void onCheckedChanged(BootstrapCheckBox checkBox, boolean b) {
        mPrefs.setBootstrapSaveSelection(b);
    }

    private void tryToRestoreLastActivity() {
        if (getIntent().getBooleanExtra(DO_NOT_RESTORE, false)) {
            mPrefs.setBootstrapActivityName(null);
        }

        String bootstrapActivityName = mPrefs.getBootstrapActivityName();
        if (bootstrapActivityName != null) {
            // String activityLabel = getActivityLabelByClass(bootstrapActivityName);
            // String popupText = String.format(getString(R.string.starting_popup_fmt), activityLabel);
            // Toast.makeText(this, popupText, Toast.LENGTH_LONG).show();
            startActivity(this, bootstrapActivityName);
        }
    }

    private String getActivityLabelByClass(String clazz) {
        PackageManager pm = getPackageManager();
        String activityLabel = null;
        try {
            ActivityInfo activityInfo = pm.getActivityInfo(new ComponentName(this, clazz), 0);
            activityLabel = getString(activityInfo.labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return activityLabel;
    }

    private void setupCrashLogs() {
        Fabric.with(this, new Crashlytics());
    }

    private void setupLang() {
        new LangUpdater(this).update();
    }

    public void selectFlavour(View view) {
        switch (view.getId()) {
            case R.id.button_webview:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity.class);
                break;
            case R.id.button_xwalk:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTVActivity.class);
                break;
            case R.id.button_exo:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVActivity.class);
                break;
            case R.id.button_exo2:
                startActivity(this, com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVActivity2.class);
                break;
        }
    }

    private void startActivity(Context ctx, String clazz) {
        Intent intent = new Intent();
        // value used in StateUpdater class
        intent.putExtra(FROM_BOOTSTRAP, true);
        // NOTE: make activity transparent (non-reachable from launcher or resents)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClassName(ctx, clazz);
        startActivity(intent);
    }

    private void startActivity(Context ctx, Class clazz) {
        Intent intent = new Intent();
        // NOTE: make activity transparent (non-reachable from launcher or resents)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(ctx, clazz);
        startActivity(intent);
        saveActivityNameForFurtherLaunches(clazz);
    }

    private void saveActivityNameForFurtherLaunches(Class clazz) {
        BootstrapCheckBox chk = (BootstrapCheckBox) findViewById(R.id.chk_save_selection);
        if (chk.isChecked()) {
            mPrefs.setBootstrapActivityName(clazz.getCanonicalName());
        } else {
            mPrefs.setBootstrapActivityName(null);
        }
    }
}
