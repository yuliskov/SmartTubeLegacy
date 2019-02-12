package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;

import java.io.InputStream;
import java.util.List;

public abstract class OnMediaFoundCallback {
    public void onDashMPDFound(InputStream mpdContent){}
    public void onHLSFound(Uri hlsUrl){}
    public void onUrlListFound(List<String> uriList) {}
    public void onDashUrlFound(Uri dashUrl) {}
    public void onTrackingUrlsFound(List<Uri> urls) {}
    public void onInfoFound(GenericInfo info){}
    public abstract void onDone();
}
