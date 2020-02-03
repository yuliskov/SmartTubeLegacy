package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.content.Intent;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class ExoIntent {
    private static final String POSITION_SEC = "position_sec";
    private int mPositionSec;

    public static ExoIntent parse(Intent intent) {
        ExoIntent exoIntent = new ExoIntent();
        exoIntent.mPositionSec = intent.getIntExtra(POSITION_SEC, -1);

        return exoIntent;
    }

    public void setPositionSec(int positionSec) {
        mPositionSec = positionSec;
    }

    public int getPositionSec() {
        return mPositionSec;
    }

    public Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtra(POSITION_SEC, mPositionSec);

        return intent;
    }
}
