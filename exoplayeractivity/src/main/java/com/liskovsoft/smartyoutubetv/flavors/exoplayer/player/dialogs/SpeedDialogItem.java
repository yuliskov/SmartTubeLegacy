package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.DialogSourceBase.DialogItem;

public class SpeedDialogItem extends DialogItem {
    private final ExoPlayer mPlayer;
    private final String mTag;

    public SpeedDialogItem(String title, String tag, ExoPlayer player) {
        super(title, false);

        mTag = tag;
        mPlayer = player;
    }

    @Override
    public boolean getChecked() {
        String speed;

        if (mPlayer == null) {
            speed = "1.0";
        } else {
            speed = String.valueOf(mPlayer.getPlaybackParameters().speed);
        }

        return mTag.equals(speed);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mPlayer == null) {
            return;
        }

        mPlayer.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(mTag), 1.0f));
    }
}
