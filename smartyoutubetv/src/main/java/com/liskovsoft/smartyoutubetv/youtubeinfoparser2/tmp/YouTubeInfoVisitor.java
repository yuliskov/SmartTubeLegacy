package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.tmp;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

public interface YouTubeInfoVisitor {
    /**
     * Called when DASH/Regular media block is received
     * @param mediaItem that represents stream
     */
    void visitMediaItem(YouTubeMediaItem mediaItem);
}
