/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.util.Locale;

// NOTE: original file taken from
// https://github.com/google/ExoPlayer/blob/release-v2/library/ui/src/main/java/com/google/android/exoplayer2/ui/DebugTextViewHelper.java

/**
 * A helper class for periodically updating a {@link TextView} with debug information obtained from
 * a {@link SimpleExoPlayer}.
 */
public final class DebugViewGroupHelper implements Runnable, Player.EventListener {
    private static final int REFRESH_INTERVAL_MS = 1000;
    private static final float TEXT_SIZE_SP = 10;

    private final SimpleExoPlayer player;
    private final ViewGroup viewGroup;
    private final Activity context;

    private boolean started;
    private LinearLayout column1;
    private LinearLayout column2;

    /**
     * @param player   The {@link SimpleExoPlayer} from which debug information should be obtained.
     * @param viewGroup The {@link TextView} that should be updated to display the information.
     */
    public DebugViewGroupHelper(SimpleExoPlayer player, ViewGroup viewGroup) {
        this(player, viewGroup, null);
    }

    /**
     * @param player   The {@link SimpleExoPlayer} from which debug information should be obtained.
     * @param viewGroup The {@link TextView} that should be updated to display the information.
     */
    public DebugViewGroupHelper(SimpleExoPlayer player, ViewGroup viewGroup, Activity ctx) {
        this.player = player;
        this.viewGroup = viewGroup;
        this.context = ctx;
        inflate();
    }

    private void inflate() {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.debug_view, viewGroup, true);
        column1 = (LinearLayout) viewGroup.findViewById(R.id.debug_view_column1);
        column2 = (LinearLayout) viewGroup.findViewById(R.id.debug_view_column2);
    }

    /**
     * Starts periodic updates of the {@link TextView}. Must be called from the application's main
     * thread.
     */
    public void start() {
        if (started) {
            return;
        }
        started = true;
        player.addListener(this);
        updateAndPost();
    }

    /**
     * Stops periodic updates of the {@link TextView}. Must be called from the application's main
     * thread.
     */
    public void stop() {
        if (!started) {
            return;
        }
        started = false;
        player.removeListener(this);
        viewGroup.removeCallbacks(this);
    }

    // Player.EventListener implementation.

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing.
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        updateAndPost();
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        // Do nothing.
    }

    @Override
    public void onPositionDiscontinuity() {
        updateAndPost();
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        // Do nothing.
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing.
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // Do nothing.
    }

    @Override
    public void onTracksChanged(TrackGroupArray tracks, TrackSelectionArray selections) {
        // Do nothing.
    }

    // Runnable implementation.

    @Override
    public void run() {
        updateAndPost();
    }

    // Private methods.

    @SuppressLint("SetTextI18n")
    private void updateAndPost() {
        column1.removeAllViews();
        column2.removeAllViews();

        appendVideoInfo();
        appendOtherInfo();
        appendPlayerState();
        appendPlayerWindowIndex();
        appendPreferredDisplayModeId();

        viewGroup.removeCallbacks(this);
        viewGroup.postDelayed(this, REFRESH_INTERVAL_MS);
    }

    private void appendVideoInfo() {
        Format video = player.getVideoFormat();
        Format audio = player.getAudioFormat();
        if (video == null || audio == null) {
            return;
        }

        appendRow("Current Res", video.width + "x" + video.height + "@" + ((int)video.frameRate));
        appendRow("Codecs", String.format(
                "%s(%s)/%s(%s)",
                video.sampleMimeType.replace("video/", ""),
                video.id,
                audio.sampleMimeType.replace("audio/", ""),
                audio.id
        ));
        appendRow("Bitrate", String.format(
                "%s/%s",
                toHumanReadable(video.bitrate),
                toHumanReadable(audio.bitrate)
        ));
        String par = video.pixelWidthHeightRatio == Format.NO_VALUE ||
                video.pixelWidthHeightRatio == 1f ?
                "n/a" : String.format(Locale.US, "%.02f", video.pixelWidthHeightRatio);
        appendRow("Aspect Ratio", par);
    }

    private void appendOtherInfo() {
        DecoderCounters counters = player.getVideoDecoderCounters();
        if (counters == null)
            return;

        counters.ensureUpdated();
        appendRow("Dropped Frames", counters.droppedOutputBufferCount);
        appendRow("Peak Dropped Frames", counters.maxConsecutiveDroppedOutputBufferCount);
        appendRow("Skipped Frames", counters.skippedOutputBufferCount);
        appendRow("Rendered Frames", counters.renderedOutputBufferCount);
    }

    private void appendPlayerState() {
        appendRow("PlayWhenReady", player.getPlayWhenReady());

        String text;
        switch (player.getPlaybackState()) {
            case Player.STATE_BUFFERING:
                text = "buffering";
                break;
            case Player.STATE_ENDED:
                text = "ended";
                break;
            case Player.STATE_IDLE:
                text = "idle";
                break;
            case Player.STATE_READY:
                text = "ready";
                break;
            default:
                text = "unknown";
                break;
        }
        appendRow("PlaybackState", text);
    }

    private void appendPlayerWindowIndex() {
        appendRow("Window Index", player.getCurrentWindowIndex());
    }

    private void appendPreferredDisplayModeId() {
        if (Util.SDK_INT < 23) {
            return;
        }
        Activity ctx = this.context;
        if (ctx == null) {
            ctx = (PlayerActivity) viewGroup.getContext();
        }
        WindowManager.LayoutParams windowLayoutParams = ctx.getWindow().getAttributes();
        appendRow("DisplayModeId", windowLayoutParams.preferredDisplayModeId);
    }

    private void appendRow(String name, boolean val) {
        column1.addView(createTextView(name));
        column2.addView(createTextView(val));
    }

    private void appendRow(String name, String val) {
        column1.addView(createTextView(name));
        column2.addView(createTextView(val));
    }

    private void appendRow(String name, int val) {
        column1.addView(createTextView(name));
        column2.addView(createTextView(val));
    }

    private TextView createTextView(String name) {
        TextView textView = new TextView(context);
        textView.setText(name);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_SP);
        return textView;
    }

    private TextView createTextView(boolean val) {
        TextView textView = new TextView(context);
        textView.setText(String.valueOf(val));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_SP);
        return textView;
    }

    private TextView createTextView(int val) {
        TextView textView = new TextView(context);
        textView.setText(String.valueOf(val));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_SP);
        return textView;
    }

    private String toHumanReadable(int bitrate) {
        float mbit = ((float) bitrate) / 1_000_000;
        return String.format(Locale.ENGLISH, "%.2fMbit", mbit);
    }
}
