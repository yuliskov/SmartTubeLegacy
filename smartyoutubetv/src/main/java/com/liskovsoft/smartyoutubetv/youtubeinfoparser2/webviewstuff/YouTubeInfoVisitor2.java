package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

public interface YouTubeInfoVisitor2 {
    void visitMediaItem(YouTubeMediaItem mediaItem);
    void doneVisiting();
}
