package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs;

import android.content.Context;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.liskovsoft.exoplayeractivity.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpeedDataSource implements GenericSelectorDialog.DataSource {
    private final Context mContext;
    private final ExoPlayer mPlayer;
    private final HashMap<String, String> mItems;

    public SpeedDataSource(Context context, ExoPlayer player) {
        mContext = context;
        mPlayer = player;
        mItems = new LinkedHashMap<>();
        mItems.put("0.25", "0.25");
        mItems.put("0.5", "0.5");
        mItems.put("0.75", "0.75");
        mItems.put(mContext.getString(R.string.normal), "1.0");
        mItems.put("1.25", "1.25");
        mItems.put("1.5", "1.5");
        mItems.put("2", "2.0");
    }

    @Override
    public Map<String, String> getDialogItems() {
        return mItems;
    }

    @Override
    public String getSelected() {
        return String.valueOf(mPlayer.getPlaybackParameters().speed);
    }

    @Override
    public void setSelected(String speed) {
        mPlayer.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(speed), 1.0f));
    }

    @Override
    public String getTitle() {
        return mContext.getString(R.string.select_video_speed);
    }
}
