package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.ConcreteYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.PlayerResponseParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;

import java.util.List;

public class RootYouTubeInfoParser implements YouTubeInfoVisitable {
    private final ConcreteYouTubeInfoParser mParser;
    private final PlayerResponseParser mSubParser;
    private YouTubeInfoVisitor mVisitor;

    public RootYouTubeInfoParser(String content) {
        mParser = new ConcreteYouTubeInfoParser(content);
        mSubParser = new PlayerResponseParser(content);
    }

    @Override
    public void accept(YouTubeInfoVisitor visitor) {
        mVisitor = visitor;

        YouTubeGenericInfo info = mParser.extractGenericInfo();

        mVisitor.onGenericInfo(info);

        Uri hlsUri = mParser.extractHLSUrl();

        if (hlsUri != null) {
            mVisitor.onLiveItem(hlsUri);
            // this is live so other items is useless
            mVisitor.doneVisiting();
            return;
        }

        mParser.extractMediaItemsAndDecipher(new ConcreteYouTubeInfoParser.ParserListener() {
            @Override
            public void onExtractMediaItemsAndDecipher(List<YouTubeMediaItem> items) {
                for (YouTubeMediaItem item : items) {
                    mVisitor.onMediaItem(item);
                }
                mVisitor.doneVisiting();
            }
        });
    }
}
