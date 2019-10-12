package com.liskovsoft.smartyoutubetv.misc.appstatewatcher;

import android.app.Activity;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.DisplayHolder.Mode;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.UhdHelper;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase.StateHandler;

public class DisplayModeSaverHandler extends StateHandler {
    private final Activity mContext;

    public DisplayModeSaverHandler(Activity context) {
        mContext = context;
    }

    @Override
    public void onInit() {
        Mode mode = UhdHelper.getCurrentMode(mContext);

        CommonApplication.getPreferences().setDefaultDisplayMode(UhdHelper.formatMode(mode));
    }
}
