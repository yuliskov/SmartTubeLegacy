package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import android.content.Context;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class SpeedDialogSource implements SingleDialogSource {
    private final Context mContext;
    private final SimpleExoPlayer mPlayer;
    private final ArrayList<DialogItem> mItems;

    public SpeedDialogSource(Context context, SimpleExoPlayer player) {
        mContext = context;
        mPlayer = player;

        mItems = new ArrayList<>();
        mItems.add(new SpeedDialogItem("0.25", "0.25", mPlayer));
        mItems.add(new SpeedDialogItem("0.5", "0.5", mPlayer));
        mItems.add(new SpeedDialogItem("0.75", "0.75", mPlayer));
        mItems.add(new SpeedDialogItem(mContext.getString(R.string.normal), "1.0", mPlayer));
        mItems.add(new SpeedDialogItem("1.25", "1.25", mPlayer));
        mItems.add(new SpeedDialogItem("1.5", "1.5", mPlayer));
        mItems.add(new SpeedDialogItem("1.75", "1.75", mPlayer));
        mItems.add(new SpeedDialogItem("2", "2.0", mPlayer));

        SpeedDialogItem.sCurrentSpeed = String.valueOf(mPlayer.getPlaybackParameters().speed);
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
