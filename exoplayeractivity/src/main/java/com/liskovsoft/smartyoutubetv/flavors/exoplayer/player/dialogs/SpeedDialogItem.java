package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;

public class SpeedDialogItem extends DialogItem {
    private final ExoPlayerBaseFragment mPlayer;
    private final String mTag;

    public SpeedDialogItem(String title, String tag, ExoPlayerBaseFragment player) {
        super(title, false);

        mTag = tag;
        mPlayer = player;
    }

    @Override
    public boolean getChecked() {
        return mTag.equals(mPlayer.getPreferredSpeed());
    }

    @Override
    public void setChecked(boolean checked) {
        if (mPlayer == null) {
            return;
        }

        mPlayer.setSpeed(mTag);
    }
}
