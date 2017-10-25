package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

import java.io.InputStream;

public interface MPDBuilder {
    void append(YouTubeMediaItem mediaItem);
    InputStream build();
}
