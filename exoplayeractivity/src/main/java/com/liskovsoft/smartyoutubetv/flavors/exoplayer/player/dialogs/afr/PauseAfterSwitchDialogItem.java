package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.afr;

import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.SingleDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;

public class PauseAfterSwitchDialogItem extends SingleDialogItem {
    private final long mPauseMS;
    private final AutoFrameRateManager mAfrManager;

    public PauseAfterSwitchDialogItem(String title, long pauseMS, ExoPlayerFragment playerFragment) {
        super(title, false);

        mPauseMS = pauseMS;
        mAfrManager = playerFragment.getAutoFrameRateManager();
    }

    @Override
    public boolean getChecked() {
        return mAfrManager.getDelayTime() == mPauseMS;
    }

    @Override
    public void setChecked(boolean checked) {
        mAfrManager.setDelayTime(mPauseMS);
    }

    @Override
    public boolean doRender() {
        return mAfrManager.isDelayEnabled() && mAfrManager.isEnabled();
    }
}
