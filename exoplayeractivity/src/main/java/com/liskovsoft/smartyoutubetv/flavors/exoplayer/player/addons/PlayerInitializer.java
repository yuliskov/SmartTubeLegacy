package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

public class PlayerInitializer {
    private final PlayerActivity mPlayer;
    private final SimpleExoPlayerView mExoPlayerView;
    private TextView videoTitle;
    private TextView videoTitle2;

    public PlayerInitializer(PlayerActivity player) {
        mPlayer = player;
        mExoPlayerView = (SimpleExoPlayerView) mPlayer.findViewById(R.id.player_view);

        initTimeBar();
        initVideoTitle();
        makeActivityFullscreen();
        makeActivityHorizontal();
    }

    private void makeActivityFullscreen() {
        mPlayer.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        if (VERSION.SDK_INT >= 19) {
            View decorView = mPlayer.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void makeActivityHorizontal() {
        mPlayer.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void initVideoTitle() {
        videoTitle = (TextView) mPlayer.findViewById(R.id.video_title);
        videoTitle.setText(getMainTitle());
        videoTitle2 = (TextView) mPlayer.findViewById(R.id.video_title2);
        videoTitle2.setText(getSecondTitle());
    }

    public String getMainTitle() {
        return mPlayer.getIntent().getStringExtra(PlayerActivity.VIDEO_TITLE);
    }

    public String getSecondTitle() {
        Intent intent = mPlayer.getIntent();

        String secondTitle = String.format("%s      %s      %s %s", intent.getStringExtra(PlayerActivity.VIDEO_AUTHOR), intent.getStringExtra
                (PlayerActivity.VIDEO_DATE), formatNumber(intent.getStringExtra(PlayerActivity.VIDEO_VIEW_COUNT)), mPlayer.getString(R.string
                .view_count));

        return secondTitle;
    }

    private String formatNumber(String num) {
        if (num == null) {
            return null;
        }

        long no = Long.parseLong(num);
        String str = String.format("%,d", no);
        return str;
    }

    private void initTimeBar() {
        final int timeIncrementMS = 15000;

        // time bar: rewind and fast forward to 15 secs
        TimeBar timeBar = (TimeBar) mExoPlayerView.findViewById(R.id.exo_progress);
        timeBar.setKeyTimeIncrement(timeIncrementMS);

        // Playback control view.
        mExoPlayerView.setRewindIncrementMs(timeIncrementMS);
        mExoPlayerView.setFastForwardIncrementMs(timeIncrementMS);
    }
}
