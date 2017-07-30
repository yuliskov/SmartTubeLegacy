package com.liskovsoft.smartyoutubetv.youtubeinfoparser;

import android.net.Uri;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SimpleYouTubeInfoVisitable implements YouTubeInfoVisitable {
    private final String mContent;
    private InputStream mResultStream;
    private YouTubeInfoVisitor mVisitor;

    public SimpleYouTubeInfoVisitable(String content) {
        mContent = content;
    }

    private String readToString(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    @Override
    public void accept(YouTubeInfoVisitor visitor) {
        mVisitor = visitor;

        List<YouTubeMediaItem> items = parseToMediaItems();
        assert items != null;
        for (YouTubeMediaItem item : items) {
            mVisitor.visitMediaItem(item);
        }
    }

    private List<YouTubeMediaItem> parseToMediaItems() {
        List<YouTubeMediaItem> list = new ArrayList<>();
        List<String> items = splitContent(mContent);
        for (String item : items) {
            list.add(createMediaItem(item));
        }
        return list;
    }

    private YouTubeMediaItem createMediaItem(String content) {
        Uri mediaUrl = Uri.parse("http://example.com?" + content);
        SimpleYouTubeMediaItem mediaItem = new SimpleYouTubeMediaItem();
        mediaItem.setBitrate(mediaUrl.getQueryParameter(YouTubeInfoVisitable.BITRATE));
        mediaItem.setUrl(mediaUrl.getQueryParameter(YouTubeInfoVisitable.URL));
        mediaItem.setITag(mediaUrl.getQueryParameter(YouTubeInfoVisitable.ITAG));
        mediaItem.setType(mediaUrl.getQueryParameter(YouTubeInfoVisitable.TYPE));
        return mediaItem;
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
