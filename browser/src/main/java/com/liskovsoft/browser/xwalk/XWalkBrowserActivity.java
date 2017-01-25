package com.liskovsoft.browser.xwalk;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.liskovsoft.browser.BrowserActivity;
import org.xwalk.core.XWalkInitializer;
import org.xwalk.core.XWalkUpdater;

import java.util.HashMap;
import java.util.Map;

public class XWalkBrowserActivity extends BrowserActivity implements XWalkInitializer.XWalkInitListener, XWalkUpdater.XWalkUpdateListener {
    private XWalkInitializer mXWalkInitializer;
    private XWalkUpdater mXWalkUpdater;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Must call initAsync() before anything that involves the embedding
        // API, including invoking setContentView() with the layout which
        // holds the XWalkView object.

        mXWalkInitializer = new XWalkInitializer(this, this);
        mXWalkInitializer.initAsync();

        // Until onXWalkInitCompleted() is invoked, you should do nothing with the
        // embedding API except the following:
        // 1. Instantiate the XWalkView object
        // 2. Call XWalkPreferences.setValue()
        // 3. Call mXWalkView.setXXClient(), e.g., setUIClient
        // 4. Call mXWalkView.setXXListener(), e.g., setDownloadListener
        // 5. Call mXWalkView.addJavascriptInterface()

        mBundle = savedInstanceState;

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Try to initialize again when the user completed updating and
        // returned to current activity. The initAsync() will do nothing if
        // the initialization is proceeding or has already been completed.

        mXWalkInitializer.initAsync();
    }

    @Override
    public void onXWalkInitStarted() {
    }

    @Override
    public void onXWalkInitCancelled() {
        // Perform error handling here

        finish();
    }

    private boolean isGooglePlayInstalled() {
        Activity context = this;
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try
        {
            PackageInfo info = pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            String label = (String) info.applicationInfo.loadLabel(pm);
            app_installed = (label != null && !label.equals("Market"));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }

    private void fixUnsupportedArch() {
        String arch = System.getProperty("os.arch").toLowerCase();
        switch (arch) {
            case "armv8l":
                System.setProperty("os.arch", "armv8");
                break;
        }
    }

    private void setUpdateApkUrl() {
        //not working
        //fixUnsupportedArch();
        if (!isGooglePlayInstalled()) {
            setUpdateApkUrlToGDrive();
        }
    }

    private void setUpdateApkUrlToGDrive() {
        String abi = XWalkEnvironment.getRuntimeAbi();
        Map<String, String> apkUrls = new HashMap<>();
        //apkUrls.put("armeabi-v7a", "https://drive.google.com/uc?id=0ByORA7yiJiQXa2xwOUVRNWdTbEk"); // 23.53.589.4
        apkUrls.put("armeabi-v7a", "https://drive.google.com/uc?id=0ByORA7yiJiQXZkl1dW9IbFFJNEk"); // 22.52.561.4
        apkUrls.put("arm64-v8a", "https://drive.google.com/uc?id=0ByORA7yiJiQXcmd1ZFlRMnhaOTA"); // 22.52.561.4
        mXWalkUpdater.setXWalkApkUrl(apkUrls.get(abi));
    }

    @Override
    public void onXWalkInitFailed() {
        if (mXWalkUpdater == null) {
            mXWalkUpdater = new XWalkUpdater(this, this);
        }
        setUpdateApkUrlToGDrive();
        mXWalkUpdater.updateXWalkRuntime();
    }

    @Override
    public void onXWalkInitCompleted() {
        // Do anything with the embedding API

        initController(mBundle);
        onResume(); // resume already called with controller==null so give a second chance
    }

    @Override
    public void onXWalkUpdateCancelled() {
        // Perform error handling here

        finish();
    }
}
