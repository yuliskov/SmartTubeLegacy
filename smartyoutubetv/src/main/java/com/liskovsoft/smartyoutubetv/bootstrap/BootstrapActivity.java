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
import edu.mit.mobile.android.appupdater.AppUpdateChecker;
import edu.mit.mobile.android.appupdater.OnUpdateDialog;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;

public class BootstrapActivity extends FullscreenActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);

        setupLang();
        setupCrashLogs();
        checkForUpdates();
    }

    private void checkForUpdates() {
        final String sUpdateUrl = "https://drive.google.com/uc?id=0ByORA7yiJiQXSGFqUURSUTlmVWc";
        AppUpdateChecker updateChecker = new AppUpdateChecker(this, sUpdateUrl, new OnUpdateDialog(this, getString(R.string.app_name)));
        updateChecker.forceCheckForUpdates();
    }

    private void displayLocaleScript() {
        String script = LocaleUtility.getScript(Locale.getDefault());
        Toast.makeText(this, script, Toast.LENGTH_LONG).show();
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

    private void startActivity(Context ctx, Class clazz) {
        Intent intent = new Intent();
        // NOTE: make activity transparent (non-reachable from launcher or resents)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(ctx, clazz);
        startActivity(intent);
    }
}
