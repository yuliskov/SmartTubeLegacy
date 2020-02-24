package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.trackstate;

import android.content.Context;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

import java.util.HashSet;
import java.util.Set;

public class PlayerStateManagerBase {
    private static final String TAG = PlayerStateManagerBase.class.getSimpleName();
    private static final int HEIGHT_PRECISION_PX = 10; // ten-pixel precision
    private static final float FPS_PRECISION = 10; // fps precision
    private final ExoPreferences mPrefs;
    private String mDefaultTrackId;
    private String mDefaultAudioTrackId;
    private String mDefaultSubtitleLang;

    public PlayerStateManagerBase(Context context) {
        mPrefs = ExoPreferences.instance(context);
    }

    public MyFormat findProperAudioFormat(TrackGroupArray groupArray) {
        Set<MyFormat> fmts = findProperAudio(groupArray);

        MyFormat fmt = filterHighestFormat(fmts);

        if (fmt == null) {
            mDefaultAudioTrackId = null;
        } else {
            mDefaultAudioTrackId = fmt.id;
            Log.d(TAG, "Proper format found: " + fmt);
        }

        return fmt;
    }

    private Set<MyFormat> findProperAudio(TrackGroupArray groupArray) {
        String trackCodecs = mPrefs.getSelectedAudioTrackCodecs();
        int bitrate = mPrefs.getSelectedAudioTrackBitrate();

        Set<MyFormat> result = new HashSet<>();

        // search the same tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);

                MyFormat myFormat = new MyFormat(format, new Pair<>(j, i));

                if (bitrate != 0 && myFormat.bitrate > bitrate + 10_000) {
                    continue;
                }

                if (codecEquals(myFormat.codecs, trackCodecs)) {
                    result.add(myFormat);
                }
            }
        }

        return result;
    }

    /**
     * Restore state before video starts playing.
     * @param groupArray
     * @return
     */
    public MyFormat findPreferredVideoFormat(TrackGroupArray groupArray) {
        Set<MyFormat> fmts = findProperVideos(groupArray);

        MyFormat fmt = filterHighestFormat(fmts);

        if (fmt == null) {
            mDefaultTrackId = null;
        } else {
            mDefaultTrackId = fmt.id;
            Log.d(TAG, "Proper format found: " + fmt);
        }

        return fmt;
    }

    /**
     * Try to find a candidate(s) that match preferred settings
     * @return one or more matched video tracks as {@link MyFormat}
     */
    private Set<MyFormat> findProperVideos(TrackGroupArray groupArray) {
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

                Log.d(TAG, "Is video profile enabled: " + PlayerUtil.isFormatRestricted(preferredFormat) + ", format: " + preferredFormat);

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
    private MyFormat filterHighestFormat(Set<MyFormat> fmts) {
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
                    fpsEquals(result.frameRate, fmt.frameRate)) {
                result = fmt;
            }
        }
        return result;
    }

    public MyFormat findProperSubtitleFormat(TrackGroupArray groupArray) {
        String subName = mPrefs.getSubtitleLang();

        if (subName == null) { // default track selected
            return null;
        }

        // search the same tracks
        for (int j = 0; j < groupArray.length; j++) {
            TrackGroup trackGroup = groupArray.get(j);
            for (int i = 0; i < trackGroup.length; i++) {
                Format format = trackGroup.getFormat(i);

                if (subName.equals(format.language)) {
                    mDefaultSubtitleLang = format.language;
                    return new MyFormat(format, new Pair<>(j, i));
                }
            }
        }

        mDefaultSubtitleLang = null;

        return null;
    }

    public long findProperVideoPosition(String key) {
        return mPrefs.getPosition(key);
        //return CommonApplication.getPreferences().getCurrentVideoPosition() * 1000;
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
        return Helpers.equals(PlayerUtil.codecNameShort(codec1), PlayerUtil.codecNameShort(codec2));
    }

    protected void persistAudioParams(MyFormat format) {
        String codec = format == null ? null : format.codecs;
        int bitrate = format == null ? 0 : format.bitrate;

        // mDefaultTrackId: usually this happens when video does not contain preferred format
        boolean isTrackChanged = !Helpers.equals(format != null ? format.id : null, mDefaultAudioTrackId);

        if (isTrackChanged) {
            mPrefs.setSelectedAudioTrackCodecs(codec == null ? "" : codec);
            mPrefs.setSelectedAudioTrackBitrate(bitrate);
        }
    }

    protected void persistVideoParams(MyFormat format) {
        String trackId = extractCurrentTrackId(format);
        int height = extractCurrentTrackHeight(format);
        String codecs = extractCurrentTrackCodecs(format); // there is a bug (null codecs) on some Live formats (strange id == "1/27")
        float fps = extractCurrentTrackFps(format);

        // mDefaultTrackId: usually this happens when video does not contain preferred format
        boolean isTrackChanged = !Helpers.equals(trackId, mDefaultTrackId);

        // There is a bug (null codecs) on some Live formats (strange id == "1/27")
        if (isTrackChanged && (Helpers.isDash(trackId) || (trackId == null))) {
            mPrefs.setSelectedTrackId(trackId);
            mPrefs.setSelectedTrackHeight(height);
            mPrefs.setSelectedTrackCodecs(codecs);
            mPrefs.setSelectedTrackFps(fps);
        }
    }

    protected void persistVideoTrackPosition(String key, long position) {
        if (position == 0) {
            mPrefs.resetPosition(key);
        } else {
            mPrefs.setPosition(key, position);
        }
    }

    protected void persistSubtitleTrack(MyFormat format) {
        if (format == null) {
            mPrefs.setSubtitleLang(null);
            return;
        }

        boolean trackChanged = !Helpers.equals(format.language, mDefaultSubtitleLang);

        if (trackChanged) {
            mPrefs.setSubtitleLang(format.language);
        }
    }

    private String extractCurrentTrackId(MyFormat format) {
        if (format == null) {
            return null;
        }

        return format.id;
    }

    private int extractCurrentTrackHeight(MyFormat format) {
        if (format == null) {
            return 0;
        }

        return format.height;
    }

    private String extractCurrentTrackCodecs(MyFormat format) {
        if (format == null) {
            return null;
        }

        return format.codecs;
    }

    private float extractCurrentTrackFps(MyFormat format) {
        if (format == null) {
            return 0;
        }

        return format.frameRate;
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
        public final String language;
        public final Pair<Integer, Integer> pair;

        public MyFormat(Format format) {
            this(format, null);
        }

        public MyFormat(Format format, Pair<Integer, Integer> pair) {
            id = format.id;
            bitrate = format.bitrate;
            frameRate = format.frameRate;
            codecs = format.codecs;
            width = format.width;
            height = format.height;
            language = format.language;
            this.pair = pair;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(
                    "id: %s, bitrate: %s, frameRate: %s, codecs: %s, height: %s, width: %s, language: %s, pair: %s",
                    id, bitrate, frameRate, codecs, height, width, language, pair
            );
        }
    }
}
