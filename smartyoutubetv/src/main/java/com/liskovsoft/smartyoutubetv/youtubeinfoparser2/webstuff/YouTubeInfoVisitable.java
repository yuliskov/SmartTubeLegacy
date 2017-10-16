package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff;

public interface YouTubeInfoVisitable {
    void accept(YouTubeInfoVisitor visitor);
}
