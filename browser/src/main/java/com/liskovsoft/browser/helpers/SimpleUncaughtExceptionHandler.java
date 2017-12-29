package com.liskovsoft.browser.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Locale;

public class SimpleUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleUncaughtExceptionHandler.class);
    private final Context mContext;

    public SimpleUncaughtExceptionHandler(Context context) {
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e)
    {
        logger.debug("MyApplication", "Hello from MyApplication.uncaughtException()");
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        extractLogToFile();

        System.exit(1); // kill off the crashed app
    }

    private String extractLogToFile()
    {
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo (mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER))
            model = Build.MANUFACTURER + " " + model;

        // Make file name - file must be saved to external storage or it wont be readable by
        // the email app.
        File path = new File(Environment.getExternalStorageDirectory(), info.packageName + "_crash");
        path.mkdirs();
        Calendar cal = Calendar.getInstance();
        String formattedTime = String.format(Locale.US, "%04d.%02d.%02d-%02d.%02d.%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND));
        //String fullName = path + "/" + cal.getTime() + ".txt";

        // Extract to file.
        File file = new File(path, formattedTime + ".txt");
        InputStreamReader reader = null;
        FileWriter writer = null;
        try
        {
            // For Android 4.0 and earlier, you will get all app's log output, so filter it to
            // mostly limit it to your app's output.  In later versions, the filtering isn't needed.
            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
                    "logcat -d -v time MyApp:v dalvikvm:v System.err:v *:s" :
                    "logcat -d -v time";

            // get input stream
            java.lang.Process process = Runtime.getRuntime().exec(cmd);
            reader = new InputStreamReader (process.getInputStream());

            // write output stream
            writer = new FileWriter (file);
            writer.write ("Android version: " +  Build.VERSION.SDK_INT + "\n");
            writer.write ("Device: " + model + "\n");
            writer.write ("App version: " + (info == null ? "(null)" : info.versionCode) + "\n");

            char[] buffer = new char[10000];
            do
            {
                int n = reader.read (buffer, 0, buffer.length);
                if (n == -1)
                    break;
                writer.write (buffer, 0, n);
            } while (true);

            reader.close();
            writer.close();

            logger.debug("MyApplication", "Hello from MyApplication.extractLogToFile()");
        }
        catch (IOException e)
        {
            logger.debug("MyApplication", e.getLocalizedMessage());
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e1) {
                }

            // You might want to write a failure message to the log here.
            return null;
        }

        return file.getPath();
    }
}
