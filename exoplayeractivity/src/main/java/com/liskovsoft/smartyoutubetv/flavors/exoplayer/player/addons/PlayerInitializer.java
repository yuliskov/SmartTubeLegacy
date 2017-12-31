package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

public class PlayerInitializer {
    private final PlayerActivity mPlayer;
    private final SimpleExoPlayerView mExoPlayerView;
    private TextView videoTitle;
    private TextView videoTitle2;

    public PlayerInitializer(PlayerActivity player) {
        mPlayer = player;
        mExoPlayerView = (SimpleExoPlayerView) mPlayer.findViewById(R.id.player_view);

        initExoPlayerButtons();
        initVideoTitle();
        makeActivityFullscreen();
        makeActivityHorizontal();
    }

    private void makeActivityFullscreen() {
        mPlayer.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        if (VERSION.SDK_INT >= 19) {
            View decorView = mPlayer.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

        String secondTitle = String.format(
                "%s      %s      %s %s",
                intent.getStringExtra(PlayerActivity.VIDEO_AUTHOR),
                intent.getStringExtra(PlayerActivity.VIDEO_DATE),
                formatNumber(intent.getStringExtra(PlayerActivity.VIDEO_VIEW_COUNT)),
                mPlayer.getString(R.string.view_count)
        );

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

    private void initExoPlayerButtons() {
        initNextButton();
        initPrevButton();
        initTimeBar();
        initStatsButton();
    }

    private void initStatsButton() {
        ToggleButtonBase statsButton = (ToggleButtonBase)mPlayer.findViewById(R.id.exo_stats);
        statsButton.setOnCheckedChangeListener(new ToggleButtonBase.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
                mPlayer.showDebugView(isChecked);
            }
        });
    }

    // NOTE: example of visibility change listener
    private void initNextButton() {
        final View nextButton = mExoPlayerView.findViewById(R.id.exo_next);
        nextButton.getViewTreeObserver().addOnGlobalLayoutListener(obtainSetButtonEnabledListener(nextButton));
    }

    private void initPrevButton() {
        final View prevButton = mExoPlayerView.findViewById(R.id.exo_prev);
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
    
    private OnGlobalLayoutListener obtainSetButtonEnabledListener(final View nextButton) {
        return new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setButtonEnabled(true, nextButton);
            }
        };
    }

    private OnClickListener obtainNextListener(final View nextButton) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.doGracefulExit(PlayerActivity.BUTTON_NEXT);
            }
        };
    }

    private OnClickListener obtainPrevListener(final View prevButton) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.doGracefulExit(PlayerActivity.BUTTON_PREV);
            }
        };
    }

    private void setButtonEnabled(boolean enabled, View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(enabled);
        if (Util.SDK_INT >= 11) {
            setViewAlphaV11(view, enabled ? 1f : 0.3f);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @TargetApi(11)
    private void setViewAlphaV11(View view, float alpha) {
        view.setAlpha(alpha);
    }
}
