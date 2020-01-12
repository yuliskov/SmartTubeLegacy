package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.afr;

import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.MultiDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;

public class EnableAfrDialogItem extends MultiDialogItem {
    private final AutoFrameRateManager mAfrManager;

    public EnableAfrDialogItem(ExoPlayerFragment playerFragment) {
        super(playerFragment.getActivity().getString(R.string.enable_autoframerate), false);
        mAfrManager = playerFragment.getAutoFrameRateManager();
    }

    @Override
    public boolean getChecked() {
        return mAfrManager.isEnabled();
    }

    @Override
    public void setChecked(boolean checked) {
        mAfrManager.setEnabled(checked);
    }
}
