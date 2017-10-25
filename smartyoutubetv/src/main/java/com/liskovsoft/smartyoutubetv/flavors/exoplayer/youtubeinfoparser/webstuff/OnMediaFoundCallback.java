package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeGenericInfo;

import java.io.InputStream;

public abstract class OnMediaFoundCallback {
    public void onVideoFound(InputStream mpdContent){}
    public void onLiveFound(Uri hlsUrl){}
    public void onInfoFound(YouTubeGenericInfo info){}
}
