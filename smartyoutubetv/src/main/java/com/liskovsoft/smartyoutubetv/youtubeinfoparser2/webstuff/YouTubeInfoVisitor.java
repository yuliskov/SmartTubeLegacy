package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;

public abstract class YouTubeInfoVisitor {
    public void onMediaItem(YouTubeMediaItem mediaItem){}
    public void onLiveItem(Uri hlsUrl) {}
    public void doneVisiting(){}
    public void onGenericInfo(YouTubeGenericInfo info){}
}
