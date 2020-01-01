package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.util.Comparator;

public class MediaItemComparator implements Comparator<MediaItem> {
    public static final int ORDER_DESCENDANT = 0;
    public static final int ORDER_ASCENDANT = 1;
    private int mOrderType = ORDER_DESCENDANT;

    public MediaItemComparator() {
        
    }

    public MediaItemComparator(int orderType) {
        mOrderType = orderType;
    }

    /**
     * NOTE: Descendant sorting (better on top). High quality playback on external player.
     */
    @Override
    public int compare(MediaItem leftItem, MediaItem rightItem) {
        if (leftItem.getGlobalSegmentList() != null ||
            rightItem.getGlobalSegmentList() != null) {
            return 1;
        }

        if (mOrderType == ORDER_ASCENDANT) {
            MediaItem tmpItem = leftItem;
            leftItem = rightItem;
            rightItem = tmpItem;
        }

        int leftItemBitrate = leftItem.getBitrate() == null ? 0 : parseInt(leftItem.getBitrate());
        int rightItemBitrate = rightItem.getBitrate() == null ? 0 : parseInt(rightItem.getBitrate());

        int leftItemHeight = leftItem.getSize() == null ? 0 : parseInt(MediaItemUtils.getHeight(leftItem));
        int rightItemHeight = rightItem.getSize() == null ? 0 : parseInt(MediaItemUtils.getHeight(rightItem));

        int delta = rightItemHeight - leftItemHeight;

        if (delta == 0) {
            delta = rightItemBitrate - leftItemBitrate;
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
