package com.liskovsoft.browser.xwalk;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;
import com.squareup.otto.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;

public class XWalkDelayHandler {
    private static AtomicBoolean sHandlerAdded = new AtomicBoolean();
    private final Runnable mRunnable;

    public XWalkDelayHandler(Runnable runnable) {
        mRunnable = runnable;
        Browser.getBus().register(this);
    }

    public static boolean add(Runnable runnable) {
        if (sHandlerAdded.get()) {
            return false;
        }

        if (Browser.getEngineType() != EngineType.XWalk) {
            return false;
        }

        new XWalkDelayHandler(runnable);

        sHandlerAdded.set(true);

        return true;
    }

    @Subscribe
    public void onXWalkInitiCompleted(XWalkInitCompleted event) {
        mRunnable.run();
        sHandlerAdded.set(false);
        Browser.getBus().unregister(this);
    }
}
