package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import android.net.Uri;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleYouTubeInfoVisitable implements YouTubeInfoVisitable {
    private final String mContent;
    private InputStream mResultStream;
    private YouTubeInfoVisitor mVisitor;

    public SimpleYouTubeInfoVisitable(String content) {
        mContent = content;
    }

    @Override
    public void accept(YouTubeInfoVisitor visitor) {
        mVisitor = visitor;

        List<MediaItem> items = parseToMediaItems();
        assert items != null;
        for (MediaItem item : items) {
            mVisitor.visitMediaItem(item);
        }
    }

    private List<MediaItem> parseToMediaItems() {
        List<MediaItem> list = new ArrayList<>();
        List<String> items = splitContent(mContent);
        for (String item : items) {
            list.add(createMediaItem(item));
        }
        return list;
    }

    private MediaItem createMediaItem(String content) {
        Uri mediaUrl = Uri.parse("http://example.com?" + content);
        SimpleYouTubeMediaItem mediaItem = new SimpleYouTubeMediaItem();
        mediaItem.setBitrate(mediaUrl.getQueryParameter(MediaItem.BITRATE));
        mediaItem.setUrl(mediaUrl.getQueryParameter(MediaItem.URL));
        mediaItem.setITag(mediaUrl.getQueryParameter(MediaItem.ITAG));
        mediaItem.setType(mediaUrl.getQueryParameter(MediaItem.TYPE));
        mediaItem.setS(mediaUrl.getQueryParameter(MediaItem.S));
        decipherSignature(mediaItem);
        return mediaItem;
    }

    private void decipherSignature(SimpleYouTubeMediaItem mediaItem) {
        String sig = mediaItem.getS();
        if (sig != null) {
            String url = mediaItem.getUrl();
            String newSig = CipherUtils.decipherSignature(sig);
            mediaItem.setUrl(String.format("%s&signature=%s", url, newSig));
        }
    }

    private List<String> splitContent(String content) {
        List<String> list = new ArrayList<>();
        Uri videoInfo = Uri.parse("http://example.com?" + content);
        String adaptiveFormats = videoInfo.getQueryParameter("adaptive_fmts");
        // stream may not contain dash formats
        if (adaptiveFormats != null) {
            String[] fmts = adaptiveFormats.split(",");
            list.addAll(Arrays.asList(fmts));
        }

        String regularFormats = videoInfo.getQueryParameter("url_encoded_fmt_stream_map");
        if (regularFormats != null) {
            String[] fmts = regularFormats.split(",");
            list.addAll(Arrays.asList(fmts));
        }
        return list;
    }

    @Override
    public InputStream getResult() {
        return mResultStream;
    }
}
