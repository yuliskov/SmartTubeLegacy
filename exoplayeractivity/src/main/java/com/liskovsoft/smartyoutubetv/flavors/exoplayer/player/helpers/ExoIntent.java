package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.content.Intent;

public class ExoIntent {
    private static final String POSITION_SEC = "position_sec";
    private static final String VIDEO_PAUSED = "video_paused";
    private int mPositionSec = -1;
    private boolean mPaused;

    public static ExoIntent parse(Intent intent) {
        ExoIntent exoIntent = new ExoIntent();
        exoIntent.mPositionSec = intent.getIntExtra(POSITION_SEC, -1);
        exoIntent.mPaused = intent.getBooleanExtra(VIDEO_PAUSED, false);

        return exoIntent;
    }

    public Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtra(POSITION_SEC, mPositionSec);
        intent.putExtra(VIDEO_PAUSED, mPaused);

        return intent;
    }

    public void setPositionSec(int positionSec) {
        mPositionSec = positionSec;
    }

    public int getPositionSec() {
        return mPositionSec;
    }

    public void setPaused(boolean paused) {
        mPaused = paused;
    }

    public boolean getPaused() {
        return mPaused;
    }
}
