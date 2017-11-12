package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeMediaItem;

public interface YouTubeInfoVisitor {
    /**
     * Called when DASH/Regular media block is received
     * @param mediaItem that represents stream
     */
    void visitMediaItem(YouTubeMediaItem mediaItem);
}
