package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.misc.LocaleUtility;
import com.liskovsoft.smartyoutubetv.misc.SmartPreferences;
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;

public class BootstrapActivity extends ActivityBase {
    public static final String FROM_BOOTSTRAP = "FROM_BOOTSTRAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // do it before view instantiation
        setupLang();
        tryToRestoreLastActivity();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);

        setupCrashLogs();
    }

    private void tryToRestoreLastActivity() {
        SmartPreferences prefs = SmartPreferences.instance(this);
        String bootstrapActivityName = prefs.getBootstrapActivityName();
        if (bootstrapActivityName != null) {
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
    }
}
