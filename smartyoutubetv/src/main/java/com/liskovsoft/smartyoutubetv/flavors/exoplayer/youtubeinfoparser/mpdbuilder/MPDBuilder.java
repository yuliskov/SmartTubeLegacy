package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeSubParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

import java.io.InputStream;
import java.util.List;

public interface MPDBuilder {
    InputStream build();
    boolean isEmpty();
    void append(MediaItem mediaItem);
    void append(List<Subtitle> subs);
    void append(Subtitle sub);
}
