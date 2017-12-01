package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.ConcreteYouTubeInfoParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items.YouTubeMediaItem;

import java.io.InputStream;
import java.util.List;

public class SimpleYouTubeInfoVisitable implements YouTubeInfoVisitable {
    private final ConcreteYouTubeInfoParser mParser;
    private YouTubeInfoVisitor mVisitor;

    public SimpleYouTubeInfoVisitable(String content) {
        mParser = new ConcreteYouTubeInfoParser(content);
    }

    @Override
    public void accept(YouTubeInfoVisitor visitor) {
        mVisitor = visitor;

        YouTubeGenericInfo info = mParser.obtainGenericInfo();

        mVisitor.onGenericInfo(info);

        Uri hlsUri = mParser.extractHLSUrl();

        if (hlsUri != null) {
            mVisitor.onLiveItem(hlsUri);
            // this is live so other items is useless
            return;
        }

        InputStream dash = mParser.extractDashMPD();
        if (dash != null) {
            mVisitor.onDashMPDItem(dash);
            return;
        }

        mParser.parseToMediaItemsAndDecipher(new ConcreteYouTubeInfoParser.ParserListener() {
            @Override
            public void onParseToMediaItemsAndDecipher(List<YouTubeMediaItem> items) {
                assert items != null;
                for (YouTubeMediaItem item : items) {
                    mVisitor.onMediaItem(item);
                }
                mVisitor.doneVisiting();
            }
        });
    }
}
