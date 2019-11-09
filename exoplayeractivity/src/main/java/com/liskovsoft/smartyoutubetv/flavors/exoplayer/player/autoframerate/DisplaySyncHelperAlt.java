package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;
import android.view.Window;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.DisplayHolder.Mode;

import java.util.HashMap;

class DisplaySyncHelperAlt extends DisplaySyncHelper {
    private static final String TAG = DisplaySyncHelperAlt.class.getSimpleName();
    private static final float LAGGING_UI_THRESHOLD = 30;
    private int mVideoWidth;

    public DisplaySyncHelperAlt(Activity context) {
        super(context);
    }

    @Override
    protected HashMap<Integer, int[]> getRateMapping() {
        return SWITCH_TO_UHD && mVideoWidth > 1920 ? super.getRateMapping() : getFHDRateMapping();
    }

    private HashMap<Integer, int[]> getFHDRateMapping() {
        HashMap<Integer, int[]> relatedRates = new HashMap<>();
        relatedRates.put(1500, new int[]{6000, 3000});
        relatedRates.put(2397, new int[]{2397, 2400, 3000, 6000});
        relatedRates.put(2400, new int[]{2400, 3000, 6000});
        relatedRates.put(2497, new int[]{5000, 2500});
        relatedRates.put(2500, new int[]{5000, 2500});
        relatedRates.put(2997, new int[]{6000, 2997, 3000});
        relatedRates.put(3000, new int[]{6000, 3000});
        relatedRates.put(5000, new int[]{5000, 2500});
        relatedRates.put(5994, new int[]{5994, 6000, 3000});
        relatedRates.put(6000, new int[]{6000, 3000});
        return relatedRates;
    }

    @Override
    public boolean syncDisplayMode(Window window, int videoWidth, float videoFramerate) {
        mVideoWidth = videoWidth;

        return super.syncDisplayMode(window, videoWidth, videoFramerate);
    }

    @Override
    public boolean restoreOriginalState(Window window) {
        Mode currentMode = getUhdHelper().getCurrentMode();

        if (currentMode != null && mOriginalMode != null && (currentMode.getPhysicalHeight() != mOriginalMode.getPhysicalHeight() || currentMode.getRefreshRate() < LAGGING_UI_THRESHOLD)) {
            String msg =
                    "Restoring original state: rate: " + mOriginalMode.getRefreshRate() +
                            ", resolution: " + mOriginalMode.getPhysicalWidth() + "x" + mOriginalMode.getPhysicalHeight();
            Log.d(TAG, msg);
            super.restoreOriginalState(window);
        }

        return false;
    }
}
