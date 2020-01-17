package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.speed;

import android.content.Context;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.CombinedDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

import java.util.ArrayList;
import java.util.List;

public class SpeedDialogSource implements CombinedDialogSource {
    private final Context mContext;
    private final SimpleExoPlayer mPlayer;
    private final ArrayList<DialogItem> mItems;
    private final ExoPreferences mPrefs;

    public SpeedDialogSource(ExoPlayerFragment playerFragment) {
        mContext = playerFragment.getActivity();
        mPlayer = playerFragment.getPlayer();
        mPrefs = ExoPreferences.instance(mContext);

        mItems = new ArrayList<>();
        mItems.add(new SaveSpeedDialogItem(mContext.getString(R.string.checkbox_save_speed), mPrefs));
        mItems.add(new SpeedDialogItem("0.25", "0.25", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("0.5", "0.5", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("0.75", "0.75", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem(mContext.getString(R.string.normal), "1.0", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("1.25", "1.25", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("1.5", "1.5", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("1.75", "1.75", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("2", "2.0", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("2.25", "2.25", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("2.5", "2.5", mPlayer, mPrefs));
        mItems.add(new SpeedDialogItem("2.75", "2.75", mPlayer, mPrefs));

        if (mPlayer != null) {
            SpeedDialogItem.sCurrentSpeed = String.valueOf(mPlayer.getPlaybackParameters().speed);
        }
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_video_speed);
    }
}
