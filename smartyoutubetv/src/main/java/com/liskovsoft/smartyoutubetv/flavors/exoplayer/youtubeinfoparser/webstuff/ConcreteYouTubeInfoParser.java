package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff;

import android.net.Uri;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.YouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp.CipherUtils;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.events.DecipherSignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.webstuff.events.DecipherSignaturesEvent;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConcreteYouTubeInfoParser {
    private final String mContent;
    private ParserListener mListener;
    private List<YouTubeMediaItem> mDecipheredItems;
    private List<YouTubeMediaItem> mItems;

    public ConcreteYouTubeInfoParser(String content) {
        mContent = content;
    }

    public YouTubeGenericInfo obtainGenericInfo() {
        YouTubeGenericInfo info = new SimpleYouTubeGenericInfo();
        Uri videoInfo = Uri.parse("http://example.com?" + mContent);
        info.setLengthSeconds(videoInfo.getQueryParameter(YouTubeGenericInfo.LENGTH_SECONDS));
        info.setTitle(videoInfo.getQueryParameter(YouTubeGenericInfo.TITLE));
        info.setAuthor(videoInfo.getQueryParameter(YouTubeGenericInfo.AUTHOR));
        info.setViewCount(videoInfo.getQueryParameter(YouTubeGenericInfo.VIEW_COUNT));
        info.setTimestamp(videoInfo.getQueryParameter(YouTubeGenericInfo.TIMESTAMP));
        return info;
    }

    public Uri extractHLSUrl() {
        Uri videoInfo = Uri.parse("http://example.com?" + mContent);
        String hlsUrl = videoInfo.getQueryParameter("hlsvp");
        if (hlsUrl != null) {
            return Uri.parse(hlsUrl);
        }
        return null;
    }

    public List<YouTubeMediaItem> parseToMediaItems() {
        if (mItems != null) {
            return mItems;
        }
        List<YouTubeMediaItem> list = new ArrayList<>();
        List<String> items = splitContent(mContent);
        for (String item : items) {
            list.add(createMediaItem(item));
        }
        mItems = list;
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

    private InputStream extractRawMPD() {
        Uri videoInfo = Uri.parse("http://example.com?" + mContent);
        String dashmpdUrl = videoInfo.getQueryParameter("dashmpd");
        if (dashmpdUrl != null) {
            Response response = Helpers.doOkHttpRequest(dashmpdUrl);
            return response.body().byteStream();
        }
        return null;
    }

    private void decipherSignaturesAndDoCallback(List<YouTubeMediaItem> items) {
        if (items == null) {
            throw new IllegalStateException("No media items found!");
        }

        if (mDecipheredItems != null) {
            mListener.onParseToMediaItemsAndDecipher(mDecipheredItems);
            return;
        }

        Browser.getBus().register(this);
        Browser.getBus().post(new DecipherSignaturesEvent(items));
    }

    @Subscribe
    public void decipherSignaturesDone(DecipherSignaturesDoneEvent doneEvent) {
        Browser.getBus().unregister(this);

        List<YouTubeMediaItem> items = doneEvent.getMediaItems();
        mDecipheredItems = items;
        mListener.onParseToMediaItemsAndDecipher(items);
    }

    // not used code
    private void decipherSignature(SimpleYouTubeMediaItem mediaItem) {
        String sig = mediaItem.getS();
        if (sig != null) {
            String url = mediaItem.getUrl();
            String newSig = CipherUtils.decipherSignature(sig);
            mediaItem.setUrl(String.format("%s&signature=%s", url, newSig));
        }
    }

    public void parseToMediaItemsAndDecipher(ParserListener parserListener) {
        if (parserListener == null) {
            throw new IllegalStateException("You must supply a parser listener");
        }
        mListener = parserListener;

        decipherSignaturesAndDoCallback(parseToMediaItems());
    }

    public interface ParserListener {
        void onParseToMediaItemsAndDecipher(List<YouTubeMediaItem> items);
    }
}
