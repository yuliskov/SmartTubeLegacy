package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.afr;

import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.MultiDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;

public class Enable60fpsCorrectionDialogItem extends MultiDialogItem {
    private final AutoFrameRateManager mAfrManager;

    public Enable60fpsCorrectionDialogItem(ExoPlayerFragment playerFragment) {
        super(playerFragment.getActivity().getString(R.string.enable_autoframerate_60fps_correction) +
                ": 60 => 59.94, 30 => 29.97", false);

        mAfrManager = playerFragment.getAutoFrameRateManager();
    }

    @Override
    public boolean getChecked() {
        return mAfrManager.is60fpsCorrectionEnabled() && mAfrManager.isEnabled();
    }

    @Override
    public void setChecked(boolean checked) {
        mAfrManager.set60fpsCorrectionEnabled(checked);

        mAfrManager.setEnabled(true);
    }
}
