package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser;

import android.net.Uri;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder.MyMPDParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events.DecipherOnlySignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.events.DecipherOnlySignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.WeirdUrl;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp.CipherUtils;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ConcreteYouTubeInfoParser {
    private static final String DASH_MPD_URL = "dashmpd";
    private static final String HLS_URL = "hlsvp";
    private static final String DASH_FORMATS = "adaptive_fmts";
    private static final String REGULAR_FORMATS = "url_encoded_fmt_stream_map";
    private static final String FORMATS_DELIM = ","; // %2C
    private final String mContent;
    private final int mId;
    private ParserListener mListener;
    private List<YouTubeMediaItem> mMediaItems;
    private WeirdUrl mDashMPDUrl;
    private List<YouTubeMediaItem> mNewMediaItems;

    public ConcreteYouTubeInfoParser(String content) {
        mContent = content;
        mId = new Random().nextInt();
    }

    public YouTubeGenericInfo extractGenericInfo() {
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
        String hlsUrl = videoInfo.getQueryParameter(HLS_URL);
        if (hlsUrl != null) {
            return Uri.parse(hlsUrl);
        }
        return null;
    }

    private void extractDashMPDUrl() {
        String url = extractParam(DASH_MPD_URL);
        // dash mpd link overview: http://mysite.com/key/value/key2/value2/s/122343435535
        mDashMPDUrl = new WeirdUrl(url);
    }

    private String extractParam(String param) {
        Uri videoInfo = Uri.parse("http://example.com?" + mContent);
        return videoInfo.getQueryParameter(param);
    }

    private void extractMediaItems() {
        if (mMediaItems != null) {
            return;
        }
        List<YouTubeMediaItem> list = new ArrayList<>();
        List<String> items = splitContent(mContent);
        for (String item : items) {
            list.add(createMediaItem(item));
        }
        mMediaItems = list;
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
        return mediaItem;
    }

    private List<String> splitContent(String content) {
        List<String> list = new ArrayList<>();
        Uri videoInfo = Uri.parse("http://example.com?" + content);
        String adaptiveFormats = videoInfo.getQueryParameter(DASH_FORMATS);
        // stream may not contain dash formats
        if (adaptiveFormats != null) {
            String[] fmts = adaptiveFormats.split(FORMATS_DELIM);
            list.addAll(Arrays.asList(fmts));
        }

        String regularFormats = videoInfo.getQueryParameter(REGULAR_FORMATS);
        if (regularFormats != null) {
            String[] fmts = regularFormats.split(",");
            list.addAll(Arrays.asList(fmts));
        }
        return list;
    }

    private InputStream extractDashMPDContent() {
        String dashmpdUrl = mDashMPDUrl.toString();
        if (dashmpdUrl != null) {
            Response response = Helpers.doOkHttpRequest(dashmpdUrl);
            return response.body().byteStream();
        }
        return null;
    }

    private void decipherSignatures() {
        if (mMediaItems == null) {
            throw new IllegalStateException("No media items found!");
        }

        Browser.getBus().register(this);
        Browser.getBus().post(new DecipherOnlySignaturesEvent(extractSignatures(), mId));
    }

    private List<String> extractSignatures() {
        List<String> result = new ArrayList<>();
        for (YouTubeMediaItem item : mMediaItems) {
            result.add(item.getS());
        }
        String rawSignature = mDashMPDUrl.getParam(YouTubeMediaItem.S);
        result.add(rawSignature);
        return result;
    }

    @Subscribe
    public void decipherSignaturesDone(DecipherOnlySignaturesDoneEvent doneEvent) {
        if (doneEvent.getId() != mId) {
            return;
        }
        Browser.getBus().unregister(this);

        if (mMediaItems.size() == 0) {
            mListener.onExtractMediaItemsAndDecipher(mMediaItems);
        }

        List<String> signatures = doneEvent.getSignatures();
        String lastSignature = signatures.get(signatures.size() - 1);
        applySignatureToDashMPDUrl(lastSignature);
        applySignaturesToMediaItems(signatures);
        mergeMediaItems();
        mListener.onExtractMediaItemsAndDecipher(mMediaItems);
    }

    private void mergeMediaItems() {
        if (mNewMediaItems != null) { // NOTE: NPE here
            mMediaItems.addAll(mNewMediaItems);
        }
    }

    private void applySignatureToDashMPDUrl(String signature) {
        if (mDashMPDUrl.isEmpty()) {
            return;
        }

        mDashMPDUrl.removeParam(YouTubeMediaItem.S);
        mDashMPDUrl.setParam(YouTubeMediaItem.SIGNATURE, signature);

        InputStream inputStream = extractDashMPDContent();
        MyMPDParser parser = new MyMPDParser(inputStream);
        mNewMediaItems = parser.parse();
    }

    private void applySignaturesToMediaItems(List<String> signatures) {
        if (signatures.size() < mMediaItems.size()) {
            throw new IllegalStateException("Signatures and media items aren't match");
        }

        for (int i = 0; i < mMediaItems.size(); i++) {
            String signature = signatures.get(i);
            if (signature == null) {
                continue;
            }
            YouTubeMediaItem item = mMediaItems.get(i);
            String url = item.getUrl();
            item.setUrl(String.format("%s&signature=%s", url, signature));
            item.setSignature(signature);
            item.setS(null);
        }
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

    public void extractMediaItemsAndDecipher(ParserListener parserListener) {
        if (parserListener == null) {
            throw new IllegalStateException("You must supply a parser listener");
        }
        mListener = parserListener;

        extractMediaItems();
        extractDashMPDUrl();
        decipherSignatures();
    }

    public interface ParserListener {
        void onExtractMediaItemsAndDecipher(List<YouTubeMediaItem> items);
    }
}
