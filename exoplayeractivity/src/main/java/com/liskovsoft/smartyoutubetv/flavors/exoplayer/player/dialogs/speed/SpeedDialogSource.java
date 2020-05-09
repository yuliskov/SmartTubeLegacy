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
    private final List<DialogItem> mItems;

    public SpeedDialogSource(ExoPlayerFragment playerFragment) {
        mContext = playerFragment.getActivity();
        SimpleExoPlayer player = playerFragment.getPlayer();
        ExoPreferences preferences = ExoPreferences.instance(mContext);

        mItems = new ArrayList<>();
        mItems.add(new SaveSpeedDialogItem(mContext.getString(R.string.checkbox_save_speed), preferences));
        mItems.add(new SpeedDialogItem("0.25", "0.25", player, preferences));
        mItems.add(new SpeedDialogItem("0.5", "0.5", player, preferences));
        mItems.add(new SpeedDialogItem("0.75", "0.75", player, preferences));
        mItems.add(new SpeedDialogItem(mContext.getString(R.string.normal), "1.0", player, preferences));
        mItems.add(new SpeedDialogItem("1.25", "1.25", player, preferences));
        mItems.add(new SpeedDialogItem("1.5", "1.5", player, preferences));
        mItems.add(new SpeedDialogItem("1.75", "1.75", player, preferences));
        mItems.add(new SpeedDialogItem("2", "2.0", player, preferences));
        mItems.add(new SpeedDialogItem("2.25", "2.25", player, preferences));
        mItems.add(new SpeedDialogItem("2.5", "2.5", player, preferences));
        mItems.add(new SpeedDialogItem("2.75", "2.75", player, preferences));

        if (player != null) {
            SpeedDialogItem.sCurrentSpeed = String.valueOf(player.getPlaybackParameters().speed);
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
