package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.content.Context;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;

import java.util.HashSet;
import java.util.Set;

public class PlayerStateManagerBase {
    private static final String CODEC_AVC = "avc";
    private static final String CODEC_VP9 = "vp9";
    private static final String CODEC_VP9_HDR = "vp9.2";
    private static final int HEIGHT_PRECISION_PX = 10; // ten-pixel precision
    private static final float FPS_PRECISION = 10; // fps precision
    private final ExoPreferences mPrefs;

    public PlayerStateManagerBase(Context context) {
        mPrefs = new ExoPreferences(context);
    }

    /**
     * Try to find a candidate(s) that match preferred settings
     * @return one or more matched video tracks as {@link MyFormat}
     */
    protected Set<MyFormat> findProperVideos(TrackGroupArray groupArray) {
        String trackId = mPrefs.getSelectedTrackId();
        int trackHeight = mPrefs.getSelectedTrackHeight();
        float trackFps = mPrefs.getSelectedTrackFps();
        String trackCodecs = mPrefs.getSelectedTrackCodecs();
        boolean preferHdr = PlayerUtil.isHdrCodec(trackCodecs);

        Set<MyFormat> result = new HashSet<>();
        Set<MyFormat> backed = new HashSet<>();

        String preferredFormat = mPrefs.getPreferredFormat();

        // search the same tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);

                MyFormat myFormat = new MyFormat(format, new Pair<>(j, i));

                if (PlayerUtil.isFormatRestricted(preferredFormat)) {
                    if (PlayerUtil.isFormatMatch(preferredFormat, myFormat)) {
                        result.add(myFormat);
                    }

                    continue;
                }

                if (tracksEquals(format.id, trackId)) { // strict match found, stop search
                    result.clear();
                    result.add(myFormat);
                    break;
                }

                boolean codecMatch = trackCodecs == null || codecEquals(format.codecs, trackCodecs);
                boolean heightMatch = heightEquals(format.height, trackHeight) || format.height <= trackHeight;
                boolean fpsMatch = fpsEquals(format.frameRate, trackFps);
                boolean hdrMatch = PlayerUtil.isHdrCodec(format.codecs) == preferHdr;

                if (codecMatch && heightMatch && fpsMatch && hdrMatch) {
                    result.add(myFormat);
                    continue;
                }

                if (heightMatch) {
                    backed.add(myFormat);
                }
            }
        }

        return result.isEmpty() ? backed : result;
    }

    /**
     * Select format with same codec and highest bitrate. All track already have the same height.
     * @param fmts source (cannot be null)
     * @return best format (cannot be null)
     */
    protected MyFormat filterHighestVideo(Set<MyFormat> fmts) {
        MyFormat result = null;

        // select format with same codec and highest bitrate
        for (MyFormat fmt : fmts) {
            if (result == null) {
                result = fmt;
                continue;
            }

            // there is two levels of preference
            // by codec (e.g. avc), height, fps, bitrate (with restrictions)

            // don't relay on bitrate, since some video have improper bitrate measurement
            // because of this, I've add frameRate comparision
            if (result.height < fmt.height) {
                result = fmt;
                continue;
            }

            // don't relay on bitrate, since some video have improper bitrate measurement
            // because of this, I've add frameRate comparision
            if ((result.frameRate < fmt.frameRate) && (result.height == fmt.height)) {
                result = fmt;
                continue;
            }

            // don't relay on bitrate, since some video have improper bitrate measurement
            // because of this, I've add frameRate comparision
            if ((result.bitrate < fmt.bitrate)  &&
                    heightEquals(result.height, fmt.height) &&
                    codecEquals(result.codecs, fmt.codecs)) {
                result = fmt;
            }
        }
        return result;
    }

    private boolean heightEquals(int leftHeight, int rightHeight) {
        return Math.abs(leftHeight - rightHeight) <= HEIGHT_PRECISION_PX;
    }

    private boolean fpsEquals(float leftFps, float rightFps) {
        return Math.abs(leftFps - rightFps) <= FPS_PRECISION;
    }

    private boolean tracksEquals(String leftTrackId, String rightTrackId) {
        if (leftTrackId == null || rightTrackId == null) {
            return false;
        }

        return leftTrackId.equals(rightTrackId);
    }

    private boolean codecEquals(String codec1, String codec2) {
        return Helpers.equals(codecShort(codec1), codecShort(codec2));
    }

    private String codecShort(String codecName) {
        if (codecName == null) {
            return null;
        }

        // simplify codec name: use avc instead of avc.111333
        if (codecName.contains(CODEC_AVC)) {
            codecName = CODEC_AVC;
        } else if (codecName.contains(CODEC_VP9)) {
            codecName = CODEC_VP9;
        }

        return codecName;
    }

    /**
     * Simple wrapper around {@link Format} class
     */
    public class MyFormat {
        public final String id;
        public final int bitrate;
        public final float frameRate;
        public final String codecs;
        public final int height;
        public final int width;
        public final Pair<Integer, Integer> pair;

        public MyFormat(Format format, Pair<Integer, Integer> pair) {
            id = format.id;
            bitrate = format.bitrate;
            frameRate = format.frameRate;
            codecs = format.codecs;
            width = format.width;
            height = format.height;
            this.pair = pair;
        }
    }
}
