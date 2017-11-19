package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import android.os.Handler;

public abstract class DelayedPressCommandBase extends PressCommandBase {
    private final int mDelayMillis;

    public DelayedPressCommandBase(int delayMillis) {
        mDelayMillis = delayMillis;
    }

    @Override
    protected void passToBrowser(final String hugeFunction) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DelayedPressCommandBase.super.passToBrowser(hugeFunction);
            }
        }, mDelayMillis);
    }
}
