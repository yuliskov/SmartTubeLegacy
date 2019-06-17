package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
}
