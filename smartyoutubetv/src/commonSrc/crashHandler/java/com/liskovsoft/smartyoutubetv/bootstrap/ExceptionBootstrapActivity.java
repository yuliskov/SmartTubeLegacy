package com.liskovsoft.smartyoutubetv.bootstrap;

import androidx.appcompat.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.InitializationCallback;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class ExceptionBootstrapActivity extends AppCompatActivity {
    private static final String TAG = ExceptionBootstrapActivity.class.getSimpleName();

    /**
     * Detect {@link Crashlytics} from the property file. See <em>build.gradle</em>. <a href="https://docs.fabric.io/android/crashlytics/build-tools.html">More info</a>
     */
    protected void setupCrashLogs() {
        if (BuildConfig.CRASHLYTICS_ENABLED) {
            Log.d(TAG, "Crashlytics is enabled. Running setup...");

            CrashlyticsCore core = new CrashlyticsCore.Builder()
                    .disabled(BuildConfig.DEBUG)
                    .build();

            Fabric.with(
                    new Fabric.Builder(this).kits(
                            new Crashlytics.Builder()
                                    .core(core)
                                    .build())
                            .initializationCallback(new InitializationCallback<Fabric>() {
                                @Override
                                public void success(Fabric fabric) {
                                    setupGlobalExceptionHandler();
                                }

                                @Override
                                public void failure(Exception e) {

                                }
                            })
                            .build());
        }
    }

    private void setupGlobalExceptionHandler() {
        final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            StringWriter out = new StringWriter();
            PrintWriter writer = new PrintWriter(out);
            throwable.printStackTrace(writer);
            Log.e(TAG, out);
            Log.flush();

            // Pass exception to the Crashlytics handler
            if (oldHandler != null) {
                oldHandler.uncaughtException(thread, throwable); // Delegates to Android's error handling}
            } else {
                System.exit(2); // Prevents the service/app from freezing}
            }
        });
    }

    private void setupCrashLogsOld() {
        if (BuildConfig.CRASHLYTICS_ENABLED) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
