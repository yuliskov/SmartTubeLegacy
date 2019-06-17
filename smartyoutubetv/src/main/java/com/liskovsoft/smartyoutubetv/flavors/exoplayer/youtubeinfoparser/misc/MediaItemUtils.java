package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

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

    public static boolean isDash(MediaItem mediaItem) {
        if (mediaItem.getITag() == null) {
            return false;
        }

        if (mediaItem.getGlobalSegmentList() != null) {
            return true;
        }

        String id = mediaItem.getITag();

        return Helpers.isDash(id);
    }
}
