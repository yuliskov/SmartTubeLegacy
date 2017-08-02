package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

public interface YouTubeInfoVisitor {
    /**
     * Called when DASH/Regular media block is received
     * @param mediaItem that represents stream
     */
    void visitMediaItem(YouTubeMediaItem mediaItem);
}
