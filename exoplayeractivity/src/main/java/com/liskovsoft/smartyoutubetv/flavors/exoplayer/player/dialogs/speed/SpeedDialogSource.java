package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.speed;

import android.content.Context;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.liskovsoft.exoplayeractivity.BuildConfig;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.dialogs.GenericSelectorDialog.CombinedDialogSource;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.dialogs.ExoDialogSource;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

import java.util.ArrayList;
import java.util.List;

public class SpeedDialogSource extends ExoDialogSource implements CombinedDialogSource {
    private static final String TAG = SpeedDialogSource.class.getSimpleName();
    private final Context mContext;
    private final PlayerView mPlayerView;
    private final ArrayList<DialogItem> mItems;

    public SpeedDialogSource(Context context, SimpleExoPlayer player, PlayerView playerView) {
        super(playerView);
        mContext = context;
        mPlayerView = playerView;
        ExoPreferences prefs = ExoPreferences.instance(mContext);

        mItems = new ArrayList<>();
        mItems.add(new SaveSpeedDialogItem(mContext.getString(R.string.checkbox_save_speed), prefs));
        mItems.add(new SpeedDialogItem("0.25", "0.25", player, prefs));
        mItems.add(new SpeedDialogItem("0.5", "0.5", player, prefs));
        mItems.add(new SpeedDialogItem("0.75", "0.75", player, prefs));
        mItems.add(new SpeedDialogItem(mContext.getString(R.string.normal), "1.0", player, prefs));
        mItems.add(new SpeedDialogItem("1.10", "1.10", player, prefs));
        mItems.add(new SpeedDialogItem("1.15", "1.15", player, prefs));
        mItems.add(new SpeedDialogItem("1.25", "1.25", player, prefs));
        mItems.add(new SpeedDialogItem("1.5", "1.5", player, prefs));
        mItems.add(new SpeedDialogItem("1.75", "1.75", player, prefs));
        mItems.add(new SpeedDialogItem("2", "2.0", player, prefs));
        mItems.add(new SpeedDialogItem("2.25", "2.25", player, prefs));
        mItems.add(new SpeedDialogItem("2.5", "2.5", player, prefs));
        mItems.add(new SpeedDialogItem("2.75", "2.75", player, prefs));

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

    public static void handlePlayerState(int playbackState, SimpleExoPlayer player) {
        if (player == null) {
            return;
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Player state info. Is buffering: " + (playbackState == Player.STATE_BUFFERING));
            Log.d(TAG, "Player state info. ContentPosition: " + player.getContentPosition());
            Log.d(TAG, "Player state info. CurrentPosition: " + player.getCurrentPosition());
            Log.d(TAG, "Player state info. BufferedPosition: " + player.getBufferedPosition());
            Log.d(TAG, "Player state info. ContentBufferedPosition: " + player.getContentBufferedPosition());
            Log.d(TAG, "Player state info. Duration: " + player.getDuration());
            Log.d(TAG, "Player state info. ContentDuration: " + player.getContentDuration());
        }

        if (playbackState == Player.STATE_BUFFERING && player.getDuration() != C.TIME_UNSET) { // duration is initialized
            // suppose live stream if buffering near the end
            boolean isStream = Math.abs(player.getDuration() - player.getCurrentPosition()) < 10_000;

            Log.d(TAG, "Is current video a stream? " + isStream);

            if (isStream) {
                Log.d(TAG, "It's a stream. Setting playback speed to normal...");
                player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat("1.0"), 1.0f)); // set speed to normal
                SpeedDialogItem.sCurrentSpeed = "1.0";
            }
        }
    }

    public static void restoreSpeed(Context context, SimpleExoPlayer player) {
        if (player == null || context == null) {
            return;
        }

        ExoPreferences prefs = ExoPreferences.instance(context);

        if (prefs.getRestoreSpeed() || prefs.getForceRestoreSpeed()) {
            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(prefs.getCurrentSpeed()), 1.0f));
        }
    }
}
