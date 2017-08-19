package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import android.net.Uri;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.CipherUtils;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeInfoVisitable;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.YouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.events.DecipherSignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.events.DecipherSignaturesEvent;
import com.squareup.otto.Subscribe;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SimpleYouTubeInfoVisitable2 implements YouTubeInfoVisitable2 {
    private final String mContent;
    private YouTubeInfoVisitor2 mVisitor;
    private List<YouTubeMediaItem> mMediaItems;

    public SimpleYouTubeInfoVisitable2(String content) {
        mContent = content;
        Browser.getBus().register(this);
    }

    @Override
    public void accept(YouTubeInfoVisitor2 visitor) {
        mVisitor = visitor;

        YouTubeGenericInfo info = extractInfo(mContent);
        mVisitor.visitGenericInfo(info);

        List<YouTubeMediaItem> items = parseToMediaItems();
        assert items != null;
        mMediaItems = items;

        decipherSignatures();
    }

    private void decipherSignatures() {
        Browser.getBus().post(new DecipherSignaturesEvent(mMediaItems));
    }

    @Subscribe
    public void decipherSignaturesDone(DecipherSignaturesDoneEvent doneEvent) {
        List<YouTubeMediaItem> items = doneEvent.getMediaItems();
        for (YouTubeMediaItem item : items) {
            mVisitor.visitMediaItem(item);
        }
        mVisitor.doneVisiting();
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
        mediaItem.setBitrate(mediaUrl.getQueryParameter(YouTubeMediaItem.BITRATE));
        mediaItem.setUrl(mediaUrl.getQueryParameter(YouTubeMediaItem.URL));
        mediaItem.setITag(mediaUrl.getQueryParameter(YouTubeMediaItem.ITAG));
        mediaItem.setType(mediaUrl.getQueryParameter(YouTubeMediaItem.TYPE));
        mediaItem.setS(mediaUrl.getQueryParameter(YouTubeMediaItem.S));
        mediaItem.setClen(mediaUrl.getQueryParameter(YouTubeMediaItem.CLEN));
        mediaItem.setFps(mediaUrl.getQueryParameter(YouTubeMediaItem.FPS));
        mediaItem.setIndex(mediaUrl.getQueryParameter(YouTubeMediaItem.INDEX));
        mediaItem.setInit(mediaUrl.getQueryParameter(YouTubeMediaItem.INIT));
        mediaItem.setSize(mediaUrl.getQueryParameter(YouTubeMediaItem.SIZE));
        //decipherSignature(mediaItem);
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

    private YouTubeGenericInfo extractInfo(String content) {
        YouTubeGenericInfo info = new SimpleYouTubeGenericInfo();
        Uri videoInfo = Uri.parse("http://example.com?" + content);
        info.setLengthSeconds(videoInfo.getQueryParameter(YouTubeGenericInfo.LENGTH_SECONDS));
        info.setTitle(videoInfo.getQueryParameter(YouTubeGenericInfo.TITLE));
        info.setAuthor(videoInfo.getQueryParameter(YouTubeGenericInfo.AUTHOR));
        return info;
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
}
