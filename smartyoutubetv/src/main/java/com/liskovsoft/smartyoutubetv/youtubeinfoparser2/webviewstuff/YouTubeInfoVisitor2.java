package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

import java.io.InputStream;

public abstract class YouTubeInfoVisitor2 {
    public void onMediaItem(YouTubeMediaItem mediaItem){}
    public void doneVisiting(){}
    public void onGenericInfo(YouTubeGenericInfo info){}
    public void onRawMPD(InputStream rawMPD){}
}
