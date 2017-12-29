package com.liskovsoft.browser.addons.xwalk;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.browser.BrowserActivity;
import org.xwalk.core.MyXWalkUpdater;
import org.xwalk.core.XWalkInitializer;

public abstract class XWalkBrowserActivity extends BrowserActivity implements XWalkInitializer.XWalkInitListener, MyXWalkUpdater.XWalkUpdateListener {
    private XWalkInitializer mXWalkInitializer;
    private MyXWalkUpdater mXWalkUpdater;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Browser.getEngineType() == EngineType.XWalk) {
            initXWalk(savedInstanceState);
        }
    }

    protected void initXWalk(Bundle icicle) {
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

        mBundle = icicle;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mXWalkInitializer == null) {
            return;
        }

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

    private boolean isUnsupportedArch() {
        String arch = System.getProperty("os.arch").toLowerCase();
        switch (arch) {
            case "armv8l":
                return true;
        }
        return false;
    }

    private void setUpdateApkUrl() {
        if (isUnsupportedArch()) {
            setupXWalkApkUrl();
            return;
        }

        if (!isGooglePlayInstalled()) {
            setupXWalkApkUrl();
            return;
        }
    }

    private void setupXWalkApkUrl() {
        String abi = XWalkEnvironment.getRuntimeAbi();
        String xwalkUrl = Browser.getProperty("xwalk.url." + abi);
        if (xwalkUrl == null) {
            return;
        }
        mXWalkUpdater.setXWalkApkUrl(xwalkUrl);
    }

    @Override
    public void onXWalkInitFailed() {
        // source taken from: https://github.com/crosswalk-project/crosswalk/tree/e3259b966dcedd18dc456b8cc97cd1a52aad58ea/runtime/android/core/src/org/xwalk/core
        if (mXWalkUpdater == null) {
            mXWalkUpdater = new MyXWalkUpdater(this, this);
        }
        // setUpdateApkUrl();
        setupXWalkApkUrl();
        mXWalkUpdater.updateXWalkRuntime();
    }

    @Override
    public void onXWalkInitCompleted() {
        // Do anything with the embedding API

        // notify about end of initialization
        Browser.getBus().post(new XWalkInitCompleted());

        //initController(mBundle);
        onResume(); // resume already called with controller==null so give a second chance
    }

    @Override
    public void onXWalkUpdateCancelled() {
        // Perform error handling here

        finish();
    }
}
