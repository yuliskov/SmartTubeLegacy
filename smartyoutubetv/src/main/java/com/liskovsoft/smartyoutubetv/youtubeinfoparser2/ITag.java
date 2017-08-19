package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

import java.util.Arrays;
import java.util.List;

public final class ITag {
    public final static String AUDIO_115K_WEBM = "251";
    public final static String AUDIO_128K_AAC_LQ = "139";
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

    public final static String MUXED_360P_WEBM = "43";
    public final static String MUXED_360P_AVC = "18";
    public final static String MUXED_720P_AVC = "22";

    private final static List<String> sOrderedITagsAVCAudio = Arrays.asList(
            AUDIO_128K_AAC_LQ, AUDIO_128K_AAC);

    private final static List<String> sOrderedITagsAVCVideo = Arrays.asList(
            MUXED_360P_AVC, MUXED_720P_AVC,
            AUDIO_128K_AAC, VIDEO_144P_AVC, VIDEO_240P_AVC,
            VIDEO_360P_AVC, VIDEO_480P_AVC, VIDEO_720P_AVC,
            VIDEO_1080P_AVC, VIDEO_1440P_AVC, VIDEO_2160P_AVC, VIDEO_2160P_AVC_HQ);

    private final static List<String> sOrderedITagsWEBM = Arrays.asList(
            MUXED_360P_WEBM,
            AUDIO_115K_WEBM, VIDEO_144P_WEBM, VIDEO_240P_WEBM,
            VIDEO_360P_WEBM, VIDEO_480P_WEBM, VIDEO_720P_WEBM,
            VIDEO_1080P_WEBM, VIDEO_1440P_WEBM, VIDEO_2160P_WEBM);

    private final static List<List<String>> sITagsContainer = Arrays.asList(sOrderedITagsAVCAudio, sOrderedITagsAVCVideo, sOrderedITagsWEBM);
    public static final String AVC = "AVC";
    public static final String WEBM = "VP9";
    public static final String AVC_VIDEO = "AVC_VIDEO";
    public static final String AVC_AUDIO = "AVC_AUDIO";
    public static final String WEBM_VIDEO = "WEBM_VIDEO";

    public static int compare(String leftITag, String rightITag) {
        for (List<String> iTags : sITagsContainer) {
            int left = iTags.indexOf(leftITag);
            int right = iTags.indexOf(rightITag);
            if (left != -1 && right != -1) {
                return left - right;
            }
        }

        // we can't be here
        return 99;
    }

    public static boolean belongsToType(String type, String iTag) {
        String realType = getRealType(iTag);
        return type.equals(realType);
    }

    private static String getRealType(String iTag) {
        if (sOrderedITagsAVCVideo.contains(iTag)) {
            return AVC_VIDEO;
        }
        if (sOrderedITagsAVCAudio.contains(iTag)) {
            return AVC_AUDIO;
        }
        return null;
    }
}
