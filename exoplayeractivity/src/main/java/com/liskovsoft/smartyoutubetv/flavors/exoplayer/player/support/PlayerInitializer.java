package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Intent;
import android.text.Html;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;

import java.util.Locale;

public class PlayerInitializer {
    private static final String TAG = PlayerInitializer.class.getSimpleName();
    private final ExoPlayerBaseFragment mPlayerFragment;
    private final PlayerView mExoPlayerView;
    private final View mRootView;
    private final String mTemplMessage;
    private final TextView videoTitle;
    private final TextView videoTitle2;
    private final TextView videoQuality;
    private static final int SEEK_INCREMENT_10MIN_MS = 5 * 1000;
    private static final int SEEK_INCREMENT_60MIN_MS = 10 * 1000;
    private static final int SEEK_INCREMENT_120MIN_MS = 10 * 1000;
    private static final int SEEK_INCREMENT_180MIN_MS = 10 * 1000;
    private static final int SEEK_INCREMENT_MORE_180MIN_MS = 10 * 1000;

    public PlayerInitializer(ExoPlayerBaseFragment playerFragment) {
        mPlayerFragment = playerFragment;
        mRootView = mPlayerFragment.getView();

        if (mRootView == null) {
            throw new IllegalStateException("Fragment's root view is null");
        }

        videoTitle = mRootView.findViewById(R.id.video_title);
        videoTitle2 = mRootView.findViewById(R.id.video_title2);
        videoQuality = mRootView.findViewById(R.id.video_quality);

        mExoPlayerView = mRootView.findViewById(R.id.player_view);

        mTemplMessage = "";
    }

    public void initVideoTitle() {
        videoTitle.setText(getMainTitle());
        videoTitle2.setText(getSecondTitle());
    }

    public void resetVideoTitle() {
        videoTitle.setText(mTemplMessage);
        videoTitle2.setText(mTemplMessage);
        videoQuality.setText(mTemplMessage);
    }

    public String getMainTitle() {
        Intent intent = mPlayerFragment.getIntent();
        String title = intent.getStringExtra(ExoPlayerFragment.VIDEO_TITLE);
        return title == null ? mTemplMessage : title;
    }

    public String getSecondTitle() {
        // limit max size by 30 chars e.g. '%.30s'
        String secondTitle = String.format("%.20s      %.30s      %.20s",
                getAuthor(),
                getPublishDate(),
                getViewCount());

        return secondTitle;
    }

    private String getAuthor() {
        Intent intent = mPlayerFragment.getIntent();
        String author = intent.getStringExtra(ExoPlayerFragment.VIDEO_AUTHOR);
        return author == null ? mTemplMessage : author;
    }

    /**
     * The date may be null if screen mirroring is active (WebView isn't accessible)
     * @return publish date
     */
    private String getPublishDate() {
        Intent intent = mPlayerFragment.getIntent();
        String published = intent.getStringExtra(ExoPlayerFragment.VIDEO_DATE);
        return published == null ? mTemplMessage : published.replace("&nbsp;", " "); // &nbsp; sometimes appears in output
    }

    private String getViewCount() {
        Intent intent = mPlayerFragment.getIntent();
        String viewCount = intent.getStringExtra(ExoPlayerFragment.VIDEO_VIEW_COUNT);
        return formatNumber(viewCount);
    }

    /**
     * Format number in country depended manner
     * @param num number as string
     * @return formatted number as string
     */
    private String formatNumber(String num) {
        if (num == null) {
            return mTemplMessage;
        }
        
        if (!Helpers.isNumeric(num)) {
            return Html.fromHtml(num).toString();
        }

        long no = Long.parseLong(num);
        String count = String.format(Locale.getDefault(), "%,d", no);
        String views = mPlayerFragment.getString(R.string.view_count);
        return String.format("%s %s", count, views);
    }

    /**
     * Set different seek time depending on the video length
     */
    public void initTimeBar() {
        if (mPlayerFragment.getPlayer() == null) {
            Log.e(TAG, "Player is null");
            return;
        }

        int incrementMS;
        final long durationMS = mPlayerFragment.getPlayer().getDuration();

        if (durationMS < 10*60*1000) { // 0 - 10 min
            incrementMS = SEEK_INCREMENT_10MIN_MS;
        } else if (durationMS < 60*60*1000) { // 10 - 60 min
            incrementMS = SEEK_INCREMENT_60MIN_MS;
        } else if (durationMS < 120*60*1000) { // 60 - 120 min
            incrementMS = SEEK_INCREMENT_120MIN_MS;
        } else if (durationMS < 180*60*1000) { // 120 - 180 min
            incrementMS = SEEK_INCREMENT_180MIN_MS;
        } else { // 180 - ... min
            incrementMS = SEEK_INCREMENT_MORE_180MIN_MS;
        }

        // time bar: rewind and fast forward to 15 secs
        final TimeBar timeBar = mExoPlayerView.findViewById(R.id.exo_progress);
        timeBar.setKeyTimeIncrement(incrementMS);

        // Playback control view.
        mExoPlayerView.setRewindIncrementMs(incrementMS);
        mExoPlayerView.setFastForwardIncrementMs(incrementMS);
    }

    /**
     * Nasty hacks to fix the sync problems on the Android 4
     * @param player video player
     * @param trackSelector track selector
     */
    public void applySurfaceFix(SimpleExoPlayer player, DefaultTrackSelector trackSelector) {
        SurfaceView videoSurfaceView = (SurfaceView) mExoPlayerView.getVideoSurfaceView();
        SurfaceManager manager = new SurfaceManager(player, trackSelector);
        videoSurfaceView.getHolder().addCallback(manager);
    }

    /**
     * Nasty hacks to fix the sync problems on the Android 4
     * @param player video player
     */
    public void applySurfaceFix(SimpleExoPlayer player) {
        SurfaceView videoSurfaceView = (SurfaceView) mExoPlayerView.getVideoSurfaceView();
        SurfaceManager2 manager = new SurfaceManager2(mPlayerFragment.getActivity(), player);
        videoSurfaceView.getHolder().addCallback(manager);
    }

    public void initTitleQualityInfo() {
        if (mRootView == null) {
            Log.e(TAG, "Fragment's root view is null");
            return;
        }

        if (mPlayerFragment.getPlayer() == null) {
            Log.e(TAG, "Player is null");
            return;
        }

        TextView quality = mRootView.findViewById(R.id.video_quality);
        quality.setText(PlayerUtil.getVideoQualityLabel(mPlayerFragment.getPlayer()));
    }
}
