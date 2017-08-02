package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

import java.util.Arrays;
import java.util.List;

public final class ITag {
    public final static String AUDIO_115K_WEBM = "251";
    public final static String AUDIO_128K_AAC = "140";
    public final static String VIDEO_144P_WEBM = "278";
    public final static String VIDEO_144P_AVC = "160";
    public final static String VIDEO_240P_WEBM = "242";
    public final static String VIDEO_240P_AVC = "133";
    public final static String VIDEO_360P_WEBM = "243";
    public final static String VIDEO_360P_AVC = "134";
    public final static String VIDEO_480P_WEBM = "244";
    public final static String VIDEO_480P_AVC = "135";
    public final static String VIDEO_720P_WEBM = "247";
    public final static String VIDEO_720P_AVC = "136";
    public final static String VIDEO_1080P_WEBM = "248";
    public final static String VIDEO_1080P_AVC = "137";
    public final static String VIDEO_1440P_WEBM = "271";
    public final static String VIDEO_1440P_AVC = "264";
    public final static String VIDEO_2160P_WEBM = "313";
    public final static String VIDEO_2160P_AVC = "266";
    public final static String VIDEO_2160P_AVC_HQ = "138";
    private final static List<String> sOrderedTags = Arrays.asList(
            AUDIO_115K_WEBM, AUDIO_128K_AAC, VIDEO_144P_WEBM, VIDEO_144P_AVC, VIDEO_240P_WEBM, VIDEO_240P_AVC,
            VIDEO_360P_WEBM, VIDEO_360P_AVC, VIDEO_480P_WEBM, VIDEO_480P_AVC, VIDEO_720P_WEBM, VIDEO_720P_AVC,
            VIDEO_1080P_WEBM, VIDEO_1080P_AVC, VIDEO_1440P_WEBM, VIDEO_1440P_AVC, VIDEO_2160P_WEBM, VIDEO_2160P_AVC, VIDEO_2160P_AVC_HQ);
    public static int getIndex(String iTag) {
        return sOrderedTags.indexOf(iTag);
    }
}
