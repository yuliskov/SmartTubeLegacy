package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

public interface YouTubeInfoVisitor {
    /**
     * Called when DASH/Regular media block is received
     * @param mediaItem that represents stream
     */
    void visitMediaItem(MediaItem mediaItem);
}
