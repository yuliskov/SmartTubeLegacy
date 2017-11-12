package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
}
