package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonNextParser.VideoMetadata;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;

import java.io.InputStream;
import java.util.List;

public abstract class OnMediaFoundCallback {
    public void onStart(){}
    public void onDashMPDFound(InputStream mpdContent){}
    public void onHLSFound(Uri hlsUrl){}
    public void onUrlListFound(List<String> uriList) {}
    public void onDashUrlFound(Uri dashUrl) {}
    public void onTrackingUrlFound(Uri trackingUrl) {}
    public void onRealTrackingUrlFound(Uri trackingUrl) {}
    public void onStorySpecFound(String spec) {}
    public void onInfoFound(GenericInfo info){}
    public void onMetadata(VideoMetadata metadata){}
    public boolean getVLCFix() {
        return false;
    }

    public abstract void onDone(); // Required!!!
}
