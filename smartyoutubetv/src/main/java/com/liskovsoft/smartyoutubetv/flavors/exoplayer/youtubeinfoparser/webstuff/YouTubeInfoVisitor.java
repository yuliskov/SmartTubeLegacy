package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeMediaItem;

public abstract class YouTubeInfoVisitor {
    public void onMediaItem(YouTubeMediaItem mediaItem){}
    public void onLiveItem(Uri hlsUrl) {}
    public void doneVisiting(){}
    public void onGenericInfo(YouTubeGenericInfo info){}
}
