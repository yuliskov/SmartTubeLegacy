package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import com.google.android.exoplayer2.ui.PlayerView;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase;

public abstract class ExoDialogSource implements DialogSourceBase {
    private final PlayerView mPlayerView;
    private int mControllerShowTimeoutMs;

    public ExoDialogSource(PlayerView playerView) {
        mPlayerView = playerView;

        if (playerView != null) {
            mControllerShowTimeoutMs = playerView.getControllerShowTimeoutMs();
            playerView.setControllerShowTimeoutMs(0); // show indefinitely while dialog is active
        }
    }

    @Override
    public void onDismiss() {
        if (mPlayerView != null) {
            mPlayerView.setControllerShowTimeoutMs(mControllerShowTimeoutMs); // restore player ui show timeout on hide
        }
    }
}
