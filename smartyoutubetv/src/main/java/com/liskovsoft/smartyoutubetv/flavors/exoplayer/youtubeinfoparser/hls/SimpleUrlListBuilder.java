package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.hls;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.ITag;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.MediaItemComparator;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.MediaItemUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SimpleUrlListBuilder implements UrlListBuilder {
    private final GenericInfo mInfo;
    private final Set<MediaItem> mVideos;

    public SimpleUrlListBuilder(GenericInfo info) {
        mInfo = info;
        MediaItemComparator comp = new MediaItemComparator();
        mVideos = new TreeSet<>(comp);
    }

    @Override
    public void append(MediaItem mediaItem) {
        // remain only one item as ExoPlayer doesn't support adaptive streaming for url list
        if (MediaItemUtils.notDASH(mediaItem) &&
            mediaItem.getITag().equals(ITag.MUXED_360P_AVC)) {
            mVideos.add(mediaItem);
        }
    }

    @Override
    public boolean isEmpty() {
        return mVideos.size() == 0;
    }

    @Override
    public List<String> buildUriList() {
        List<String> list = new ArrayList<>();

        for (MediaItem item : mVideos) {
            list.add(item.getUrl());
        }

        return list;
    }
}
