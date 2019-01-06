package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeSubParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;

import java.io.InputStream;
import java.util.List;

public interface MPDBuilder {
    InputStream build();
    boolean isEmpty();
    void append(MediaItem mediaItem);
    void append(List<Subtitle> subs);
    void append(Subtitle sub);
}
