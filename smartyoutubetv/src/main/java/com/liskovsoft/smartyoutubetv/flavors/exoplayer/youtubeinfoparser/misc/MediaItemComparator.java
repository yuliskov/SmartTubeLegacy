package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;

import java.util.Comparator;

public class MediaItemComparator implements Comparator<MediaItem> {
    @Override
    public int compare(MediaItem leftItem, MediaItem rightItem) {
        if (leftItem.getGlobalSegmentList() != null ||
            rightItem.getGlobalSegmentList() != null) {
            return 1;
        }

        int leftItemBitrate = leftItem.getBitrate() == null ? 0 : parseInt(leftItem.getBitrate());
        int rightItemBitrate = rightItem.getBitrate() == null ? 0 : parseInt(rightItem.getBitrate());

        int leftItemHeight = leftItem.getSize() == null ? 0 : parseInt(MediaItemUtils.getHeight(leftItem));
        int rightItemHeight = rightItem.getSize() == null ? 0 : parseInt(MediaItemUtils.getHeight(rightItem));

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
