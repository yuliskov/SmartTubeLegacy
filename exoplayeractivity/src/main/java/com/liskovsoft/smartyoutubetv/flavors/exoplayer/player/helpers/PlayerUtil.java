package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.trackstate.PlayerStateManagerBase.MyFormat;

public final class PlayerUtil extends TrackSelectorUtil {
    private static final int VIDEO_RENDERER_INDEX = 0;
    private static final int HEIGHT_SHIFT = 100;
    private static final int FPS_SHIFT = 10;

    private PlayerUtil() {
    }

    /**
     * Test format against user preferred one (selected in bootstrap)
     * @param formatBoundary format specification e.g 1080|60|avc
     * @param format format
     * @return is test passed
     */
    public static boolean isFormatMatch(String formatBoundary, MyFormat format) {
        if (notAVideo(format) ||
            ExoPreferences.FORMAT_ANY.equals(formatBoundary)) {
            return false;
        }

        if (formatBoundary == null || formatBoundary.isEmpty()) {
            return false;
        }

        String[] split = formatBoundary.split("\\|");

        int height = split.length >= 1 ? Integer.parseInt(split[0]) : 0;
        int fps = split.length >= 2 ? Integer.parseInt(split[1]) : 0;
        String codec = split.length >= 3 ? split[2] : null;
        String hdr = split.length >= 4 ? split[3] : null;

        return format.height <= (height + HEIGHT_SHIFT) &&
               format.frameRate <= (fps + FPS_SHIFT) &&
               (format.codecs == null || codec == null || format.codecs.contains(codec)) &&
               (hdr != null || !TrackSelectorUtil.isHdrCodec(format.codecs));

    }

    public static boolean isFormatRestricted(String preferredFormat) {
        return !ExoPreferences.FORMAT_ANY.equals(preferredFormat);
    }

    private static boolean notAVideo(MyFormat format) {
        return format.height == -1;
    }

    public static Uri convertToFullUrl(String videoId) {
        String url = String.format("https://www.youtube.com/watch?v=%s", videoId);
        return Uri.parse(url);
    }

    @TargetApi(17)
    public static void showMultiChooser(Context context, Uri url) {
        Intent primaryIntent = new Intent(Intent.ACTION_VIEW);
        Intent secondaryIntent = new Intent(Intent.ACTION_SEND);
        primaryIntent.setData(url);
        secondaryIntent.putExtra(Intent.EXTRA_TEXT, url.toString());
        secondaryIntent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(primaryIntent, context.getResources().getText(R.string.send_to));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { secondaryIntent });
        chooserIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        context.startActivity(chooserIntent);
    }

    public static Format getCurrentVideoTrack(DefaultTrackSelector trackSelector) {
        MappedTrackInfo trackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (trackInfo == null) {
            return null;
        }
        TrackGroupArray groups = trackInfo.getTrackGroups(VIDEO_RENDERER_INDEX);
        SelectionOverride override = trackSelector.getParameters().getSelectionOverride(VIDEO_RENDERER_INDEX, groups);
        if (override == null || override.tracks.length == 0)
            return null;
        return groups.get(override.groupIndex).getFormat(override.tracks[0]);
    }

    public static Format getCurrentVideoTrack(SimpleExoPlayer player) {
        if (player == null) {
            return null;
        }

        return player.getVideoFormat();
    }

    public static Format getCurrentAudioTrack(SimpleExoPlayer player) {
        if (player == null) {
            return null;
        }

        return player.getAudioFormat();
    }

    public static String extractResolutionLabel(Format format) {
        String qualityLabel = "";

        if (format.height <= 480) {
            qualityLabel = "SD";
        } else if (format.height <= 720) {
            qualityLabel = "HD";
        } else if (format.height <= 1080) {
            qualityLabel = "FHD";
        } else if (format.height <= 1440) {
            qualityLabel = "QHD";
        } else if (format.height <= 2160) {
            qualityLabel = "4K";
        }

        return qualityLabel;
    }

    public static int extractFps(Format format) {
        return format.frameRate == Format.NO_VALUE ? 0 : Math.round(format.frameRate);
    }

    public static String getVideoQualityLabel(SimpleExoPlayer player) {
        Format format = PlayerUtil.getCurrentVideoTrack(player);

        if (format == null) {
            return "none";
        }

        String separator = "/";

        String resolution = PlayerUtil.extractResolutionLabel(format);

        int fpsNum = PlayerUtil.extractFps(format);
        String fps = fpsNum == 0 ? "" : String.valueOf(fpsNum);

        String codec = PlayerUtil.extractCodec(format);

        boolean isHdr = TrackSelectorUtil.isHdrCodec(format.codecs);
        String hdrStr = "";

        String qualityString;

        if (!fps.isEmpty()) {
            fps = separator + fps;
        }

        if (!codec.isEmpty()) {
            codec = separator + codec.toUpperCase();
        }

        if (isHdr) {
            hdrStr = separator + "HDR";
        }

        qualityString = String.format("%s%s%s%s", resolution, fps, codec, hdrStr);

        return qualityString;
    }

    public static String getAudioQualityLabel(SimpleExoPlayer player) {
        Format format = PlayerUtil.getCurrentAudioTrack(player);

        if (format == null) {
            return "none";
        }

        return PlayerUtil.extractCodec(format);
    }

    public static String getTrackQualityLabel(SimpleExoPlayer player, int rendererIndex) {
        switch (rendererIndex) {
            case ExoPlayerFragment.RENDERER_INDEX_VIDEO:
                return PlayerUtil.getVideoQualityLabel(player);
            case ExoPlayerFragment.RENDERER_INDEX_AUDIO:
                return PlayerUtil.getAudioQualityLabel(player);
        }

        return "";
    }
}
