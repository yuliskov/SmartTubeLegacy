package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
}
