package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeSubParser;

import java.io.InputStream;

public abstract class YouTubeInfoVisitor {
    public void onMediaItem(YouTubeMediaParser.MediaItem mediaItem){}
    public void onSubItem(YouTubeSubParser.Subtitle mediaItem){}
    public void onLiveItem(Uri hlsUrl) {}
    public void doneVisiting(){}
    public void onGenericInfo(GenericInfo info){}
    public void onDashMPDItem(InputStream dash){}
}
