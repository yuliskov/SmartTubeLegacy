package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.afr;

import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.MultiDialogItem;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.SingleDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;

public class EnablePauseAfterSwitchDialogItem extends MultiDialogItem {
    private final AutoFrameRateManager mAfrManager;

    public EnablePauseAfterSwitchDialogItem(ExoPlayerFragment playerFragment) {
        super(playerFragment.getActivity().getString(R.string.enable_autoframerate_pause), false);

        mAfrManager = playerFragment.getAutoFrameRateManager();
    }

    @Override
    public boolean getChecked() {
        return mAfrManager.isDelayEnabled() && mAfrManager.isEnabled();
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mAfrManager.setEnabled(checked);
        }

        mAfrManager.setDelayEnabled(checked);
    }
}
