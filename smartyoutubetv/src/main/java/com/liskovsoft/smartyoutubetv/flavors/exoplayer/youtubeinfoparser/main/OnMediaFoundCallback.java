package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;

import java.io.InputStream;

public abstract class OnMediaFoundCallback {
    public void onDashMPDFound(InputStream mpdContent){}
    public void onLiveUrlFound(Uri hlsUrl){}
    public void onInfoFound(GenericInfo info){}
    public abstract void onDone();
}
