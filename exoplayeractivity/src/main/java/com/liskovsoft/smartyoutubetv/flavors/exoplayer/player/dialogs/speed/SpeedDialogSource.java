package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.speed;

import android.content.Context;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.liskovsoft.exoplayeractivity.BuildConfig;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.CombinedDialogSource;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

import java.util.ArrayList;
import java.util.List;

public class SpeedDialogSource implements CombinedDialogSource {
    private static final String TAG = SpeedDialogSource.class.getSimpleName();
    private final Context mContext;
    private final SimpleExoPlayer mPlayer;
    private final ArrayList<DialogItem> mItems;
    private final ExoPreferences mPrefs;

    public SpeedDialogSource(Context context, SimpleExoPlayer player) {
        mContext = context;
        mPlayer = player;
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

    public static void handlePlayerState(int playbackState, SimpleExoPlayer player) {
        if (player == null) {
            return;
        }

        //if (BuildConfig.DEBUG) {
        //    Log.d(TAG, "Player state info. Is buffering: " + (playbackState == Player.STATE_BUFFERING));
        //    Log.d(TAG, "Player state info. ContentPosition: " + player.getContentPosition());
        //    Log.d(TAG, "Player state info. CurrentPosition: " + player.getCurrentPosition());
        //    Log.d(TAG, "Player state info. BufferedPosition: " + player.getBufferedPosition());
        //    Log.d(TAG, "Player state info. ContentBufferedPosition: " + player.getContentBufferedPosition());
        //    Log.d(TAG, "Player state info. Duration: " + player.getDuration());
        //    Log.d(TAG, "Player state info. ContentDuration: " + player.getContentDuration());
        //}

        if (playbackState == Player.STATE_BUFFERING && player.getDuration() > 0) { // duration is initialized
            if (player.getDuration() - player.getCurrentPosition() < 10_000) { // // seems live stream (buffering near the end)
                Log.d(TAG, "Setting playback speed to normal...");
                player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat("1.0"), 1.0f)); // set speed to normal
                SpeedDialogItem.sCurrentSpeed = "1.0";
            }
        }
    }
}
