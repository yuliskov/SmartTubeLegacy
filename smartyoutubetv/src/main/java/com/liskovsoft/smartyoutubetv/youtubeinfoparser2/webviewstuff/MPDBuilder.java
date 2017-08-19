package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.io.InputStream;

public interface MPDBuilder {
    void append(YouTubeMediaItem mediaItem);
    InputStream build();
}
