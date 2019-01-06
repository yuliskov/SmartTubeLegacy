package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.hls;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main.YouTubeMediaParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.MediaItemComparator;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.MediaItemUtils;

import java.util.Set;
import java.util.TreeSet;

public class SimpleHlsBuilder implements HlsBuilder {
    private final GenericInfo mInfo;
    private final Set<MediaItem> mVideos;

    public SimpleHlsBuilder(GenericInfo info) {
        mInfo = info;
        MediaItemComparator comp = new MediaItemComparator();
        mVideos = new TreeSet<>(comp);
    }

    @Override
    public void append(MediaItem mediaItem) {
        if (MediaItemUtils.notDASH(mediaItem)) {
            mVideos.add(mediaItem);
        }
    }

    @Override
    public boolean isEmpty() {
        return mVideos.size() == 0;
    }

    @Override
    public Uri buildUri() {
        return null;
    }
}
