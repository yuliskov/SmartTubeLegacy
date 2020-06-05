package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.io.InputStream;
import java.util.List;

public interface MPDBuilder {
    String VIDEO_MP4 = "video/mp4";
    String AUDIO_MP4 = "audio/mp4";
    InputStream build();
    boolean isEmpty();
    void append(MediaItem mediaItem);
    void append(List<Subtitle> subs);
    void append(Subtitle sub);
    boolean isDynamic();
    void limitVideoCodec(String codec);
    void limitAudioCodec(String codec);
}
