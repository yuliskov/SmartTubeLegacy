package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoWebView;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoXWalk;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.misc.SmartPreferences;
import com.liskovsoft.smartyoutubetv.widgets.BootstrapCheckBox;
import com.liskovsoft.smartyoutubetv.widgets.BootstrapTextButton;
import io.fabric.sdk.android.Fabric;

public class BootstrapActivity extends ActivityBase {
    public static final String FROM_BOOTSTRAP = "FROM_BOOTSTRAP";
    public static final String SKIP_RESTORE = "skip_restore";
    private SmartPreferences mPrefs;
    private LanguageSelector mLangSelector;

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

        BootstrapCheckBox chkbox = (BootstrapCheckBox) findViewById(R.id.chk_save_selection);
        boolean isChecked = mPrefs.getBootstrapSaveSelection();
        chkbox.setChecked(isChecked);
    }

    public void onClick(View button) {
        if (mLangSelector == null) {
            mLangSelector = new LanguageSelector(this);
        }
        if (button.getId() == R.id.btn_select_lang) {
            mLangSelector.run();
        }
    }

    public void onCheckedChanged(BootstrapCheckBox checkBox, boolean b) {
        mPrefs.setBootstrapSaveSelection(b);
    }

    private void tryToRestoreLastActivity() {
        boolean skipRestore = getIntent().getBooleanExtra(SKIP_RESTORE, false);
        if (skipRestore) {
            return;
        }

        String bootstrapActivityName = mPrefs.getBootstrapActivityName();
        boolean isChecked = mPrefs.getBootstrapSaveSelection();
        boolean activityHasName = bootstrapActivityName != null;
        if (isChecked && activityHasName) {
            startActivity(this, bootstrapActivityName);
        }
    }

    private void setupCrashLogs() {
        Fabric.with(this, new Crashlytics());
    }

    private void setupLang() {
        new LangUpdater(this).update();
    }

    public void selectFlavour(View view) {
        Class clazz = com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity.class;
        switch (view.getId()) {
            case R.id.button_webview:
                clazz = com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTVActivity.class;
                break;
            case R.id.button_xwalk:
                clazz = com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTVActivity.class;
                break;
            case R.id.button_exo:
                clazz = SmartYouTubeTVExoWebView.class;
                break;
            case R.id.button_exo2:
                clazz = SmartYouTubeTVExoXWalk.class;
                break;
        }

        mPrefs.setBootstrapActivityName(clazz.getCanonicalName());
        startActivity(this, clazz);
    }

    private void startActivity(Context ctx, String clazz) {
        Intent intent = new Intent();
        // value used in StateUpdater class
        intent.putExtra(FROM_BOOTSTRAP, true);
        // NOTE: make activity transparent (non-reachable from launcher or resents)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClassName(ctx, clazz);
        intent.setData(getOriginData());

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) { // activity's name changed (choose again)
            e.printStackTrace();
        }
    }

    private void startActivity(Context ctx, Class clazz) {
        Intent intent = new Intent();
        // NOTE: make activity transparent (non-reachable from launcher or resents)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(ctx, clazz);

        intent.setData(getOriginData());
        startActivity(intent);
    }

    private Uri getOriginData() {
        return getIntent().getData();
    }
}
