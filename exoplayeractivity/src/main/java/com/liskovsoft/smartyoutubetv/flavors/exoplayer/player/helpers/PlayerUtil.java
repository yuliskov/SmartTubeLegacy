/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.util.MimeTypes;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

import java.util.Locale;

/**
 * Utility methods for demo application.
 */
/*package*/ public final class PlayerUtil {
    private static final int VIDEO_RENDERER_INDEX = 0;
    private static final String MIME_SEPARATOR = "/";

    private PlayerUtil() {
    }

    /**
     * Builds a track name for display.
     *
     * @param format {@link Format} of the track.
     * @return a generated name specific to the track.
     */
    public static CharSequence buildTrackName(Format format) {
        String trackName;
        if (MimeTypes.isVideo(format.sampleMimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(joinWithSeparator(joinWithSeparator(buildResolutionString(format),
                    buildFPSString(format)), buildBitrateString(format)), buildTrackIdString(format)), buildCodecTypeString(format)), buildHDRString(format));
        } else if (MimeTypes.isAudio(format.sampleMimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format),
                    buildAudioPropertyString(format)), buildBitrateString(format)), buildTrackIdString(format)), buildCodecTypeString(format));
        } else {
            trackName = joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildBitrateString(format)), buildSampleMimeTypeString(format));
        }
        return trackName.length() == 0 ? "unknown" : trackName;
    }

    /**
     * Builds a track name for display.
     *
     * @param format {@link Format} of the track.
     * @return a generated name specific to the track.
     */
    public static CharSequence buildTrackNameShort(Format format) {
        String trackName;
        if (MimeTypes.isVideo(format.sampleMimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(joinWithSeparator(buildResolutionString(format),
                    buildFPSString(format)), buildBitrateString(format)), buildCodecTypeStringShort(format)), buildHDRString(format));
        } else if (MimeTypes.isAudio(format.sampleMimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format),
                    buildAudioPropertyString(format)), buildBitrateString(format)), buildCodecTypeStringShort(format));
        } else {
            trackName = joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildBitrateString(format)), buildCodecTypeStringShort(format));
        }
        return trackName.length() == 0 ? "unknown" : trackName;
    }

    private static String buildCodecTypeString(Format format) {
        if (format.sampleMimeType == null ||
            format.codecs == null)
            return "";
        String prefix = format.sampleMimeType.split(MIME_SEPARATOR)[0];
        return String.format("%s/%s", prefix, format.codecs);
    }

    private static String buildCodecTypeStringShort(Format format) {
        String codecs = format.codecs;

        if (codecs == null)
            return "";

        String[] codecNames = {"avc", "vp9", "mp4a", "vorbis"};

        for (String codecName : codecNames) {
            if (codecs.contains(codecName)) {
                return codecName;
            }
        }

        return codecs;
    }

    private static String buildHDRString(Format format) {
        return format.codecs.equals("vp9.2") ? "HDR" : "";
    }

    private static String buildFPSString(Format format) {
        return format.frameRate == Format.NO_VALUE ? "" : Helpers.formatFloat(format.frameRate, 1) + "fps";
    }

    private static String buildResolutionString(Format format) {
        return format.width == Format.NO_VALUE || format.height == Format.NO_VALUE ? "" : align(format.width + "x" + format.height, 9);
    }

    private static String buildAudioPropertyString(Format format) {
        return format.channelCount == Format.NO_VALUE || format.sampleRate == Format.NO_VALUE ? "" : format.channelCount + "ch, " + format
                .sampleRate + "Hz";
    }

    private static String buildLanguageString(Format format) {
        return TextUtils.isEmpty(format.language) || "und".equals(format.language) ? "" : format.language;
    }

    private static String buildBitrateString(Format format) {
        double bitrateMB = Helpers.round(format.bitrate / 1_000_000f, 2);
        return format.bitrate == Format.NO_VALUE || bitrateMB == 0 ? "" : align(String.format(Locale.US, "%sMbit", Helpers.formatFloat(bitrateMB, 2)), 9);
    }

    private static String joinWithSeparator(String first, String second) {
        return first.length() == 0 ? second : (second.length() == 0 ? first : first + "    " + second);
    }

    private static String buildTrackIdString(Format format) {
        return format.id == null ? "" : ("id:" + format.id);
    }

    private static String buildSampleMimeTypeString(Format format) {
        return format.sampleMimeType == null ? "" : format.sampleMimeType;
    }

    /**
     * Test format against user preferred one (selected in bootstrap)
     * @param format format
     * @return is test passed
     */
    public static boolean isPreferredFormat(Context ctx, Format format) {
        if (notAVideo(format)) {
            return true;
        }

        ExoPreferences prefs = ExoPreferences.instance(ctx);
        String codecAndHeight = prefs.getPreferredCodec();
        if (codecAndHeight.isEmpty()) { // all formats are preferred
            return true;
        }

        String[] split = codecAndHeight.split("\\|");
        String height = split[0];
        String fps = split[1];
        String codec = split[2];
        return format.height <= Integer.parseInt(height) &&
               format.frameRate <= Integer.parseInt(fps) &&
               format.codecs.contains(codec);

    }

    private static boolean notAVideo(Format format) {
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

    public static Format getCurrentlyPlayingTrack(DefaultTrackSelector trackSelector) {
        MappedTrackInfo trackInfo = trackSelector.getCurrentMappedTrackInfo();
        TrackGroupArray groups = trackInfo.getTrackGroups(VIDEO_RENDERER_INDEX);
        SelectionOverride override = trackSelector.getSelectionOverride(VIDEO_RENDERER_INDEX, groups);
        if (override == null || override.tracks.length == 0)
            return null;
        return groups.get(override.groupIndex).getFormat(override.tracks[0]);
    }

    public static String extractQualityLabel(Format format) {
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

    public static String extractCodec(Format format) {
        String codec = "";

        if (format.codecs.contains("vp9")) {
            codec = "VP9";
        } else if (format.codecs.contains("avc")) {
            codec = "AVC";
        }

        return codec.toLowerCase();
    }

    public static int extractFps(Format format) {
        return format.frameRate == -1 ? 0 : Math.round(format.frameRate);
    }

    /**
     * Prepend spaces to make needed length
     */
    private static String align2(String input, int totalLen) {
        int strLen = input.length();
        if (strLen == totalLen)
            return input;

        String fmt = "%-" + totalLen + "s";

        return String.format(fmt, input);
    }

    /**
     * Prepend spaces to make needed length
     */
    private static String align(String input, int totalLen) {
        int strLen = input.length();
        int addLen = totalLen - strLen;
        StringBuilder inputBuilder = new StringBuilder(input);
        for (int i = 0; i < addLen; i++) {
            inputBuilder.append("  "); // two spaces as one digit
        }
        input = inputBuilder.toString();
        return input;
    }

    /**
     * Add html color tag
     */
    private static String color(String input, String color) {
        return String.format("<font color=\"%s\">%s</font>", color, input);
    }
}
