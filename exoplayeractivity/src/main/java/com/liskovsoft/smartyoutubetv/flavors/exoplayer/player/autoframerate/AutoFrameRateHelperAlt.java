package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;

class AutoFrameRateHelperAlt extends AutoFrameRateHelper {
    public AutoFrameRateHelperAlt(Activity context) {
        super(context);
        mSyncHelper = new DisplaySyncHelperAlt(context);
    }
}
