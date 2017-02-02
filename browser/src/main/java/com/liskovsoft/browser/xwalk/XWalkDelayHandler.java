package com.liskovsoft.browser.xwalk;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.squareup.otto.Subscribe;

public class XWalkDelayHandler {
    private static XWalkDelayHandler sLastHandler;
    private final Runnable mRunnable;

    public XWalkDelayHandler(Runnable runnable) {
        mRunnable = runnable;
        Browser.getBus().register(this);
    }

    public static boolean add(Runnable runnable) {
        if (sLastHandler != null) { // handler already added
            return false;
        }

        if (Browser.getEngineType() != EngineType.XWalk) {
            return false;
        }

        sLastHandler = new XWalkDelayHandler(runnable);

        return true;
    }

    @Subscribe
    public void onXWalkInitiCompleted(XWalkInitCompleted event) {
        mRunnable.run();
        sLastHandler = null;
        Browser.getBus().unregister(this);
    }
}
