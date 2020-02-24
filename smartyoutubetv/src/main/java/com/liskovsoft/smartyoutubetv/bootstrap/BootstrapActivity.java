package com.liskovsoft.smartyoutubetv.bootstrap;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.liskovsoft.sharedutils.dialogs.MultiChoiceSelectorDialog;
import com.liskovsoft.sharedutils.dialogs.SingleChoiceSelectorDialog;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.bootstrap.dialoglanguage.LanguageDialogSource;
import com.liskovsoft.smartyoutubetv.bootstrap.dialogtweaks.TweaksDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTV4K;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTV4KAlt;
import com.liskovsoft.smartyoutubetv.flavors.webview.SmartYouTubeTV1080Activity;
import com.liskovsoft.smartyoutubetv.flavors.xwalk.SmartYouTubeTV1080AltActivity;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.widgets.BootstrapButtonBase;
import com.liskovsoft.smartyoutubetv.widgets.BootstrapCheckButton;

import java.util.HashMap;
import java.util.Map.Entry;

public class BootstrapActivity extends BootstrapActivityBase {
    private static final String TAG = BootstrapActivity.class.getSimpleName();
    public static final String FROM_BOOTSTRAP = "FROM_BOOTSTRAP";
    public static final String SKIP_RESTORE = "skip_restore";
    private SmartPreferences mPrefs;
    private HashMap<Integer, Class<?>> mLauncherMapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // do it before view instantiation
        initPrefs();
        tryToRestoreLastActivity();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bootstrap);

        initLauncherMapping();
        initVersion();
        lockOtherLaunchers();
    }

    private void initVersion() {
        TextView title = findViewById(R.id.bootstrap_title_message);
        CharSequence oldTitle = title.getText();
        String finalTitle = String.format("%s (%s)", oldTitle, BuildConfig.VERSION_NAME);
        title.setText(finalTitle);
    }

    private void initPrefs() {
        if (mPrefs == null) {
            mPrefs = SmartPreferences.instance(this);
        }
    }

    private void initCheckbox(int id, boolean isChecked) {
        BootstrapCheckButton chkbox = findViewById(id);
        chkbox.setChecked(isChecked);
    }

    public void onClick(View button) {
        switch (button.getId()) {
            case R.id.btn_select_lang:
                SingleChoiceSelectorDialog.create(this, new LanguageDialogSource(this), R.style.AppDialog);
                break;
            case R.id.btn_tweaks:
                MultiChoiceSelectorDialog.create(this, new TweaksDialogSource(this), R.style.AppDialog);
                break;
        }
    }

    private void tryToRestoreLastActivity() {
        boolean skipRestore = getIntent().getBooleanExtra(SKIP_RESTORE, false);
        if (skipRestore) {
            return;
        }

        String bootstrapActivityName = mPrefs.getBootActivityName();
        boolean isChecked = mPrefs.getBootstrapSaveSelection();
        boolean activityHasName = bootstrapActivityName != null;
        if (isChecked && activityHasName) {
            startActivity(this, bootstrapActivityName);
        }
    }

    private void initLauncherMapping() {
        mLauncherMapping = new HashMap<>();
        mLauncherMapping.put(R.id.button_exo, SmartYouTubeTV4K.class);
        mLauncherMapping.put(R.id.button_exo2, SmartYouTubeTV4KAlt.class);
        mLauncherMapping.put(R.id.button_webview, SmartYouTubeTV1080Activity.class);
        mLauncherMapping.put(R.id.button_xwalk, SmartYouTubeTV1080AltActivity.class);
    }

    public void selectFlavour(View view) {
        Class<?> clazz = mLauncherMapping.get(view.getId());

        if (clazz == null) {
            clazz = SmartYouTubeTV1080Activity.class;
        }

        mPrefs.setBootActivityName(clazz.getCanonicalName());
        startActivity(this, clazz);
    }

    private void startActivity(Context ctx, String clazz) {
        Intent intent = getIntent(); // modify original intent
        // value used in StateUpdater class
        intent.putExtra(FROM_BOOTSTRAP, true);
        // NOTE: make activity transparent (non-reachable from launcher or resents)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClassName(ctx, clazz);

        try {
            Log.d(TAG, "Starting from intent: " + intent.toUri(0));
            startActivity(intent);
        } catch (ActivityNotFoundException e) { // activity's name changed (choose again)
            e.printStackTrace();
        }
    }

    private void startActivity(Context ctx, Class<?> clazz) {
        Intent intent = getIntent(); // modify original intent
        // NOTE: make activity transparent (non-reachable from launcher or from resent list)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(ctx, clazz);

        Log.d(TAG, "Starting from intent: " + intent.toUri(0));
        startActivity(intent);
    }

    public void lockOtherLaunchers() {
        String activeLauncherClass = mPrefs.getBootActivityName();

        if (activeLauncherClass == null) {
            return;
        }

        boolean doLock = mPrefs.getLockLastLauncher();

        for (Entry<Integer, Class<?>> entry : mLauncherMapping.entrySet()) {
            Class<?> clazz = entry.getValue();
            boolean isActiveClass = activeLauncherClass.equals(clazz.getCanonicalName());
            BootstrapButtonBase view = findViewById(entry.getKey());

            if (doLock && !isActiveClass) {
                view.disable();
            }

            if (doLock && isActiveClass) {
                view.enable();
            }

            if (!doLock) {
                view.enable();
            }
        }
    }
}
