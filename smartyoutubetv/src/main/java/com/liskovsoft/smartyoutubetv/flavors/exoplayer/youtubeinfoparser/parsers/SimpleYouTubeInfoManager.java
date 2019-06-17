package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeStoryParser.Storyboard;

import java.util.List;

public class SimpleYouTubeInfoManager implements YouTubeInfoVisitable {
    private final YouTubeMediaParser mMediaParser;
    private final YouTubeSubParser mSubParser;
    private final YouTubeStoryParser mStoryParer;
    private YouTubeInfoVisitor mVisitor;

    public SimpleYouTubeInfoManager(String content) {
        JsonInfoParser parser = new JsonInfoParser(content);
        mMediaParser = new YouTubeMediaParser(content, parser);
        mSubParser = new YouTubeSubParser(content, parser);
        mStoryParer = new YouTubeStoryParser(content, parser);
    }

    @Override
    public void accept(YouTubeInfoVisitor visitor) {
        mVisitor = visitor;

        GenericInfo info = mMediaParser.extractGenericInfo();
        mVisitor.onGenericInfo(info);

        List<Subtitle> subs = mSubParser.extractAllSubs();
        if (subs != null) {
            for (Subtitle sub : subs) {
                mVisitor.onSubItem(sub);
            }
        }

        Storyboard story = mStoryParer.extractStory();
        if (story != null) {
            mVisitor.onStory(story);
        }

        mMediaParser.extractMediaItemsAndDecipher(new YouTubeMediaParser.ParserListener() {
            @Override
            public void onHlsUrl(Uri url) {
                // NOTE: serious bug there
                // NOTE: exo can play live stream in hls only
                // NOTE: dash live isn't playable (infinite loading)
                mVisitor.onHlsUrl(url);
            }

            @Override
            public void onDashUrl(Uri url) {
                mVisitor.onDashUrl(url);
            }

            @Override
            public void onTrackingUrl(Uri url) {
                mVisitor.onTrackingUrl(url);
            }

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
