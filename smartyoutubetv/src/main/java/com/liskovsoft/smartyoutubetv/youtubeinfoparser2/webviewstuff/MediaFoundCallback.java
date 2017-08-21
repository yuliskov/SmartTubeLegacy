package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.net.Uri;

import java.io.InputStream;

public abstract class MediaFoundCallback {
    public abstract void onFound(InputStream mpdContent);
    public abstract void onLiveFound(Uri hlsUrl);
}
