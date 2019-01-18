package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;

public class SpeedDialogItem extends DialogItem {
    private final SimpleExoPlayer mPlayer;
    private final String mTag;
    public static String sCurrentSpeed = "1.0";

    public SpeedDialogItem(String title, String tag, SimpleExoPlayer player) {
        super(title, false);

        mTag = tag;
        mPlayer = player;
    }

    @Override
    public boolean getChecked() {
        return mTag.equals(sCurrentSpeed);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mPlayer == null) {
            return;
        }

        mPlayer.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(mTag), 1.0f));
        sCurrentSpeed = mTag;
    }
}
