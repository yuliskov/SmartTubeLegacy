package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
}
