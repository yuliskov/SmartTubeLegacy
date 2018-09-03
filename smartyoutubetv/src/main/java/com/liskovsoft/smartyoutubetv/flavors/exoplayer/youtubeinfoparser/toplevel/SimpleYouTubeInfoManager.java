package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeSubParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeMediaParser.MediaItem;

import java.util.List;

public class SimpleYouTubeInfoManager implements YouTubeInfoVisitable {
    private final YouTubeMediaParser mMediaParser;
    private final YouTubeSubParser mSubParser;
    private YouTubeInfoVisitor mVisitor;

    public SimpleYouTubeInfoManager(String content) {
        mMediaParser = new YouTubeMediaParser(content);
        mSubParser = new YouTubeSubParser(content);
    }

    @Override
    public void accept(YouTubeInfoVisitor visitor) {
        mVisitor = visitor;

        GenericInfo info = mMediaParser.extractGenericInfo();

        mVisitor.onGenericInfo(info);

        Uri hlsUri = mMediaParser.extractHLSUrl();

        if (hlsUri != null) {
            mVisitor.onLiveItem(hlsUri);
            // this is live so other items is useless
            mVisitor.doneVisiting();
            return;
        }

        List<Subtitle> subs = mSubParser.getAllSubs();
        if (subs != null) {
            for (Subtitle sub : subs) {
                mVisitor.onSubItem(sub);
            }
        }

        mMediaParser.extractMediaItemsAndDecipher(new YouTubeMediaParser.ParserListener() {
            @Override
            public void onExtractMediaItemsAndDecipher(List<MediaItem> items) {
                for (MediaItem item : items) {
                    mVisitor.onMediaItem(item);
                }
                mVisitor.doneVisiting();
            }
        });
    }
}
