package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webstuff;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;

import java.io.InputStream;

public abstract class OnMediaFoundCallback {
    public void onVideoFound(InputStream mpdContent){}
    public void onLiveFound(Uri hlsUrl){}
    public void onInfoFound(YouTubeGenericInfo info){}
}
