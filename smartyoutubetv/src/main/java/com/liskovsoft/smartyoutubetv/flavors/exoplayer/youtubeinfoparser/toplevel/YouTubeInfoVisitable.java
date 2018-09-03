package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
}
