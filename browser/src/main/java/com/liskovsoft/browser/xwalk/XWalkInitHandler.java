package com.liskovsoft.browser.xwalk;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.squareup.otto.Subscribe;

public class XWalkInitHandler {
    private static boolean sInitDone = false;
    private final Runnable mRunnable;

    public XWalkInitHandler(Runnable runnable) {
        mRunnable = runnable;
        Browser.getBus().register(this);
    }

    public static boolean add(Runnable runnable) {
        if (sInitDone) { // xwalk init already completed
            return false;
        }

        if (Browser.getEngineType() != EngineType.XWalk) {
            return false;
        }

        new XWalkInitHandler(runnable);

        return true;
    }

    public static void reset() {
        sInitDone = false;
    }

    @Subscribe
    public void onXWalkInitiCompleted(XWalkInitCompleted event) {
        sInitDone = true;
        Browser.getBus().unregister(this);
        mRunnable.run();
    }
}
