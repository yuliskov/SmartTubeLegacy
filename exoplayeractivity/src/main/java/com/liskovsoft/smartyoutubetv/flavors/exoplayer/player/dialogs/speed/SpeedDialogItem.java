package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.speed;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.DialogSourceBase.SingleDialogItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

public class SpeedDialogItem extends SingleDialogItem {
    private final ExoPlayer mPlayer;
    private final ExoPreferences mPrefs;
    private final String mTag;
    public static String sCurrentSpeed = "1.0";

    public SpeedDialogItem(String title, String tag, ExoPlayer player, ExoPreferences prefs) {
        super(title, false);

        mTag = tag;
        mPlayer = player;
        mPrefs = prefs;
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
        mPrefs.setCurrentSpeed(mTag);
        sCurrentSpeed = mTag;
    }
}
