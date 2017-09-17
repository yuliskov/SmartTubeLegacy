package com.liskovsoft.smartyoutubetv.misc;

import android.content.Context;
import android.os.Bundle;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.TabControl;
import com.liskovsoft.browser.custom.ControllerEventHandler;
import com.liskovsoft.browser.custom.PageDefaults;

public class MyControllerEventHandler implements ControllerEventHandler {
    private final PageDefaults mPageDefaults;
    private final LangUpdater mLangUpdater;
    private final StateUpdater mStateUpdater;
    private Context mContext;
    private Controller mController;

    public MyControllerEventHandler(Controller controller) {
        mController = controller;
        mContext = controller.getContext();
        mPageDefaults = mController.getPageDefaults();
        mLangUpdater = new LangUpdater(mContext);
        mStateUpdater = new StateUpdater(mController);
    }

    @Override
    public void onControllerStart() {
        mLangUpdater.update();

        // if you need to disable auto-saving webview state:
        // mController.getCrashRecoveryHandler().pauseState();
    }

    @Override
    public void beforeSaveInstanceState(Bundle state) {
        // mStateUpdater.fixPlaylistUrl(state);
    }

    @Override
    public void beforeRestoreInstanceState(Bundle state) {
        mStateUpdater.updateState(state);
    }
}
