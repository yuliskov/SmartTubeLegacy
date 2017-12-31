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
package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Display.Mode;
import android.view.Gravity;
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
    private String NOT_AVAILABLE = "none";

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
        // appendPlayerWindowIndex();
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

        String currentRes = getCurrentResString();

        appendRow("Current/Optimal Resolution",
                    video.width + "x" + video.height + "@" + ((int)video.frameRate)
                        + "/" +
                        currentRes);
        appendRow("Video/Audio Codecs", String.format(
                "%s(%s)/%s(%s)",
                video.sampleMimeType.replace("video/", ""),
                video.id,
                audio.sampleMimeType.replace("audio/", ""),
                audio.id
        ));
        appendRow("Video/Audio Bitrate", String.format(
                "%s/%s",
                toHumanReadable(video.bitrate),
                toHumanReadable(audio.bitrate)
        ));
        String par = video.pixelWidthHeightRatio == Format.NO_VALUE ||
                video.pixelWidthHeightRatio == 1f ?
                NOT_AVAILABLE : String.format(Locale.US, "%.02f", video.pixelWidthHeightRatio);
        appendRow("Aspect Ratio", par);
    }

    private void appendOtherInfo() {
        DecoderCounters counters = player.getVideoDecoderCounters();
        if (counters == null)
            return;

        counters.ensureUpdated();
        appendRow("Dropped/Rendered Frames",
                counters.droppedOutputBufferCount + counters.skippedOutputBufferCount
                    + "/" +
                    counters.renderedOutputBufferCount);
    }

    private void appendPlayerState() {
        appendRow("Player Paused", !player.getPlayWhenReady());

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
        appendRow("Playback State", text);
    }

    private void appendPreferredDisplayModeId() {
        String title = "Display Mode ID";
        if (Util.SDK_INT < 23) {
            appendRow(title, NOT_AVAILABLE);
        } else {
            Activity ctx = this.context;
            if (ctx == null) {
                ctx = (PlayerActivity) viewGroup.getContext();
            }
            WindowManager.LayoutParams windowLayoutParams = ctx.getWindow().getAttributes();
            int displayModeId = windowLayoutParams.preferredDisplayModeId;
            appendRow(title, displayModeId);
        }
    }

    private void appendPlayerWindowIndex() {
        appendRow("Window Index", player.getCurrentWindowIndex());
    }

    private void appendRow(String name, boolean val) {
        appendNameColumn(createTextView(name));
        appendValueColumn(createTextView(val));
    }

    private void appendRow(String name, String val) {
        appendNameColumn(createTextView(name));
        appendValueColumn(createTextView(val));
    }

    private void appendRow(String name, int val) {
        appendNameColumn(createTextView(name));
        appendValueColumn(createTextView(val));
    }

    private void appendNameColumn(TextView content) {
        content.setGravity(Gravity.END);
        column1.addView(content);
    }

    private void appendValueColumn(TextView content) {
        column2.addView(content);
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

    private String getCurrentResString() {
        if (Util.SDK_INT < 23) {
            return NOT_AVAILABLE;
        }

        Display display = getCurrentDisplay();
        if (display == null) {
            return NOT_AVAILABLE;
        }

        Mode mode = display.getMode();
        int physicalWidth = mode.getPhysicalWidth();
        int physicalHeight = mode.getPhysicalHeight();
        float refreshRate = mode.getRefreshRate();

        return physicalWidth + "x" + physicalHeight + "@" + ((int)refreshRate);
    }

    @TargetApi(17)
    private android.view.Display getCurrentDisplay() {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager == null)
            return null;
        android.view.Display[] displays = displayManager.getDisplays();
        if (displays == null || displays.length == 0) {
            return null;
        }
        //assuming the 1st display is the actual display.
        return displays[0];
    }
}
