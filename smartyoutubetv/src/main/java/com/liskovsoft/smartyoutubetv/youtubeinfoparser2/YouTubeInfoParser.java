package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

import android.net.Uri;

import java.util.List;

public interface YouTubeInfoParser {
    String VIDEO_1080P_AVC = "137";
    String AUDIO_128K_AAC = "140";
    List<String> getAllVideoLinks();
    Uri getUrlByTag(String iTag);
}
