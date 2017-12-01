package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;

import java.io.InputStream;

public abstract class YouTubeInfoVisitor {
    public void onMediaItem(YouTubeMediaItem mediaItem){}
    public void onLiveItem(Uri hlsUrl) {}
    public void doneVisiting(){}
    public void onGenericInfo(YouTubeGenericInfo info){}
    public void onDashMPDItem(InputStream dash){}
}
