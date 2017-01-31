package com.liskovsoft.browser.xwalk;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.squareup.otto.Subscribe;

public class XWalkDelayHandler {
    private static boolean sHandlerAdded;
    private final Runnable mRunnable;

    public XWalkDelayHandler(Runnable runnable) {
        mRunnable = runnable;
        Browser.getBus().register(this);
    }

    public static boolean add(Runnable runnable) {
        if (sHandlerAdded) {
            return false;
        }

        if (Browser.getEngineType() != EngineType.XWalk) {
            return false;
        }

        new XWalkDelayHandler(runnable);

        sHandlerAdded = true;

        return true;
    }

    @Subscribe
    public void onXWalkInitiCompleted(XWalkInitCompleted event) {
        mRunnable.run();
        sHandlerAdded = false;
        Browser.getBus().unregister(this);
    }
}
