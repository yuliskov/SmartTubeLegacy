package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff;

import android.net.Uri;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp.CipherUtils;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.events.DecipherSignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.events.DecipherSignaturesEvent;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

        Uri uri = mParser.extractHLSUrl();

        if (uri != null) {
            mVisitor.onLiveItem(uri);
            // this is live so other items is useless
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
