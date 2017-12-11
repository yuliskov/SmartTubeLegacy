package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom;

import android.view.Display;
import android.view.Display.Mode;
import android.view.WindowManager;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoFrameRateManager {
    private final PlayerActivity mCtx;
    private final SimpleExoPlayer mPlayer;

    public AutoFrameRateManager(PlayerActivity ctx, SimpleExoPlayer player) {
        mCtx = ctx;
        mPlayer = player;
    }

    public void apply() {
        if (mPlayer == null || mPlayer.getVideoFormat() == null) {
            return;
        }
        Format videoFormat = mPlayer.getVideoFormat();
        float frameRate = videoFormat.frameRate;
        prepareDisplay(frameRate);
    }

    private void prepareDisplay(float fps) {
        if (fps < 20) { // Hz
            return;
        }

        if (Util.SDK_INT < 23) {
            return;
        }

        Display display = mCtx.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams windowLayoutParams = mCtx.getWindow().getAttributes();

        float doubleFps = fps;

        if ((fps > 24.8 && fps < 25.2) || (fps > 29.7 && fps < 30.2)) doubleFps = fps * 2.0f;

        List<Mode> foundModes = new ArrayList<>();
        List<Float> accuracy = new ArrayList<>();

        Display.Mode bestMode = display.getMode();

        for (Display.Mode candidate : display.getSupportedModes()) {

            boolean refreshRateOk = Math.round(candidate.getRefreshRate()) == Math.round(doubleFps);
            boolean resolutionOk = candidate.getPhysicalWidth() == bestMode.getPhysicalWidth() &&
                    candidate.getPhysicalHeight() == bestMode.getPhysicalHeight();

            if ((!refreshRateOk) || (!resolutionOk)) {
                continue;
            }

            foundModes.add(candidate);
            accuracy.add(Math.abs(candidate.getRefreshRate() - doubleFps));
        }

        if (foundModes.isEmpty()) {
            windowLayoutParams.preferredDisplayModeId = 0;
            return;
        }

        int minIndex;
        minIndex = accuracy.indexOf(Collections.min(accuracy));

        windowLayoutParams.preferredDisplayModeId = foundModes.get(minIndex).getModeId();
    }
}
