package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.afr;

import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.MultiDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;

public class EnableResolutionSwitchDialogItem extends MultiDialogItem {
    private final AutoFrameRateManager mAfrManager;

    public EnableResolutionSwitchDialogItem(ExoPlayerFragment playerFragment) {
        super(playerFragment.getActivity().getString(R.string.enable_autoframerate_resolution), false);

        mAfrManager = playerFragment.getAutoFrameRateManager();
    }

    @Override
    public boolean getChecked() {
        return mAfrManager.isResolutionSwitchEnabled() && mAfrManager.isEnabled();
    }

    @Override
    public void setChecked(boolean checked) {
        mAfrManager.setResolutionSwitchEnabled(checked);

        mAfrManager.setEnabled(true);
    }
}
