package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import android.content.Context;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.dialogs.GenericSelectorDialog.SingleDialogSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SpeedDialogSource implements SingleDialogSource {
    private final Context mContext;
    private final ExoPlayer mPlayer;
    private final ArrayList<DialogItem> mItems;

    public SpeedDialogSource(Context context, ExoPlayer player) {
        mContext = context;
        mPlayer = player;
        mItems = new ArrayList<>();
        mItems.add(DialogItem.create("0.25", "0.25"));
        mItems.add(DialogItem.create("0.5", "0.5"));
        mItems.add(DialogItem.create("0.75", "0.75"));
        mItems.add(DialogItem.create(mContext.getString(R.string.normal), "1.0"));
        mItems.add(DialogItem.create("1.25", "1.25"));
        mItems.add(DialogItem.create("1.5", "1.5"));
        mItems.add(DialogItem.create("1.75", "1.75"));
        mItems.add(DialogItem.create("2", "2.0"));
    }

    @Override
    public List<DialogItem> getItems() {
        return mItems;
    }

    @Override
    public Object getSelectedItemTag() {
        if (mPlayer == null) {
            return "1.0";
        }

        return String.valueOf(mPlayer.getPlaybackParameters().speed);
    }

    @Override
    public void setSelectedItemTag(Object speed) {
        if (mPlayer == null)
            return;
        mPlayer.setPlaybackParameters(new PlaybackParameters(Float.parseFloat((String) speed), 1.0f));
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_video_speed);
    }
}
