package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;

public abstract class YouTubeInfoVisitor {
    public void onMediaItem(MediaItem mediaItem){}
    public void onSubItem(Subtitle mediaItem){}
    public void onStorySpec(String spec){}
    public void onHlsUrl(Uri url) {}
    public void doneVisiting(){}
    public void onGenericInfo(GenericInfo info){}
    public void onDashUrl(Uri url) {}
    public void onTrackingUrl(Uri url) {}
}
