package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;

import java.util.Comparator;

public class MediaItemComparator implements Comparator<MediaItem> {
    @Override
    public int compare(MediaItem leftItem, MediaItem rightItem) {
        if (leftItem.getGlobalSegmentList() != null ||
                rightItem.getGlobalSegmentList() != null) {
            return 1;
        }

        if (leftItem.getSize() == null || rightItem.getSize() == null) {
            return 0;
        }
        if (leftItem.getBitrate() == null || rightItem.getBitrate() == null) {
            return 0;
        }

        int leftItemBitrate = parseInt(leftItem.getBitrate());
        int rightItemBitrate = parseInt(rightItem.getBitrate());

        int leftItemHeight = parseInt(MediaItemUtils.getHeight(leftItem));
        int rightItemHeight = parseInt(MediaItemUtils.getHeight(rightItem));

        int delta = leftItemHeight - rightItemHeight;
        if (delta == 0) {
            delta = leftItemBitrate - rightItemBitrate;
        }
        return delta;
    }

    private int parseInt(String num) {
        if (!Helpers.isNumeric(num)) {
            return 0;
        }

        return Integer.parseInt(num);
    }
}
