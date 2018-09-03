package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeMediaParser.GenericInfo;

import java.io.InputStream;

public abstract class YouTubeInfoVisitor {
    public void onMediaItem(YouTubeMediaParser.MediaItem mediaItem){}
    public void onSubItem(YouTubeSubParser.Subtitle mediaItem){}
    public void onLiveItem(Uri hlsUrl) {}
    public void doneVisiting(){}
    public void onGenericInfo(GenericInfo info){}
    public void onDashMPDItem(InputStream dash){}
}
