package com.liskovsoft.browser.xwalk;

import android.os.Bundle;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.liskovsoft.browser.BaseBrowserFragment;
import org.xwalk.core.MyXWalkUpdater;
import org.xwalk.core.XWalkInitializer;

public abstract class XWalkBrowserFragment extends BaseBrowserFragment implements XWalkInitializer.XWalkInitListener, MyXWalkUpdater.XWalkUpdateListener {
    private XWalkInitializer mXWalkInitializer;
    private MyXWalkUpdater mXWalkUpdater;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Browser.getEngineType() == EngineType.XWalk) {
            initXWalk();
        }
    }

    private void initXWalk() {
        mXWalkUpdater = new MyXWalkUpdater(this, getActivity());

        // Must call initAsync() before anything that involves the embedding
        // API, including invoking setContentView() with the layout which
        // holds the XWalkView object.

        mXWalkInitializer = new XWalkInitializer(this, getActivity());
        mXWalkInitializer.initAsync();

        // Until onXWalkInitCompleted() is invoked, you should do nothing with the
        // embedding API except the following:
        // 1. Instantiate the XWalkView object
        // 2. Call XWalkPreferences.setValue()
        // 3. Call mXWalkView.setXXClient(), e.g., setUIClient
        // 4. Call mXWalkView.setXXListener(), e.g., setDownloadListener
        // 5. Call mXWalkView.addJavascriptInterface()
    }

    @Override
    public void onResume() {
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
