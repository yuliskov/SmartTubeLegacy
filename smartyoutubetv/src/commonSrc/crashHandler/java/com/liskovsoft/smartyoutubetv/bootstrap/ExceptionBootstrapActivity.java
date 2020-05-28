package com.liskovsoft.smartyoutubetv.bootstrap;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ExceptionBootstrapActivity extends AppCompatActivity {
    private static final String TAG = ExceptionBootstrapActivity.class.getSimpleName();
    
    protected void setupCrashLogs() {
        // OPTIONAL: If crash reporting has been explicitly disabled previously, add:
        // https://firebase.google.com/docs/crashlytics/upgrade-sdk?authuser=0&platform=android
        // FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }
}
