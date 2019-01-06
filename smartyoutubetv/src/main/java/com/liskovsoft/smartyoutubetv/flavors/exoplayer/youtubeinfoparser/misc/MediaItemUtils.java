package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;

public class MediaItemUtils {
    public static String getHeight(MediaItem item) {
        String size = item.getSize();

        if (size == null) {
            return "";
        }

        String[] widthHeight = size.split("x");

        if (widthHeight.length != 2) {
            return "";
        }

        return widthHeight[1];
    }

    public static String getWidth(MediaItem item) {
        String size = item.getSize();

        if (size == null) {
            return "";
        }

        String[] widthHeight = size.split("x");

        if (widthHeight.length != 2) {
            return "";
        }

        return widthHeight[0];
    }

    public static boolean notDASH(MediaItem mediaItem) {
        if (mediaItem.getITag() == null) {
            return true;
        }

        if (mediaItem.getGlobalSegmentList() != null) {
            return false;
        }

        int maxNoDashITag = 50;
        int itag = Integer.parseInt(mediaItem.getITag());

        return itag < maxNoDashITag;
    }
}
