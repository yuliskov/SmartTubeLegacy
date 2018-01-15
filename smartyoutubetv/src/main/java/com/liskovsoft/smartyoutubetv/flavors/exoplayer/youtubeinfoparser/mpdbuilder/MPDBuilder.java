package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.PlayerResponseParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;

import java.io.InputStream;
import java.util.List;

public interface MPDBuilder {
    InputStream build();
    boolean isEmpty();
    void append(YouTubeMediaItem mediaItem);
    void append(List<Subtitle> subs);
}
