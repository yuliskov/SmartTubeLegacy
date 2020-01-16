package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp.CipherUtils;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyPathQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Parses input (get_video_info) to {@link MediaItem}<br/>
 * For more info see {@link JsonInfoParser}
 */
public class YouTubeMediaParser {
    private static final String TAG = YouTubeMediaParser.class.getSimpleName();
    private static final String DASH_MPD_URL = "dashmpd";
    private static final String HLS_URL = "hlsvp";
    private static final String DASH_FORMATS = "adaptive_fmts";
    private static final String REGULAR_FORMATS = "url_encoded_fmt_stream_map";
    private static final String FORMATS_DELIM = ","; // %2C
    private static final int DECIPHERED_SIGNATURE_LENGTH = 81;

    private final String mContent;
    private final int mId;
    private ParserListener mListener;
    private List<MediaItem> mMediaItems;
    /**
     * Path to *.mpd playlist
     */
    private MyQueryString mDashMPDUrl;
    private MyQueryString mHlsUrl;
    private MyQueryString mTrackingUrl;
    private List<MediaItem> mNewMediaItems;
    private JsonInfoParser mParser;

    public YouTubeMediaParser(String content, JsonInfoParser parser) {
        mContent = content;
        mId = new Random().nextInt();

        if (Log.getLogType().equals(Log.LOG_TYPE_FILE)) {
            Log.d(TAG, content);
        }

        mParser = parser;
    }

    public GenericInfo extractGenericInfo() {
        GenericInfo info = new SimpleYouTubeGenericInfo();
        Uri videoInfo = ParserUtils.parseUri(mContent);
        info.setLengthSeconds(getDuration(videoInfo));
        info.setTitle(videoInfo.getQueryParameter(GenericInfo.TITLE));
        info.setAuthor(videoInfo.getQueryParameter(GenericInfo.AUTHOR));
        info.setViewCount(videoInfo.getQueryParameter(GenericInfo.VIEW_COUNT));
        info.setTimestamp(videoInfo.getQueryParameter(GenericInfo.TIMESTAMP));
        return info;
    }

    private String getDuration(Uri videoInfo) {
        String duration = videoInfo.getQueryParameter(GenericInfo.LENGTH_SECONDS);
        // new video_info format: video len moved to the nested JSON object
        return duration == null ? mParser.extractDurationMs() : duration;
    }

    private void extractHlsUrl() {
        String url = ParserUtils.extractParam(HLS_URL, mContent);

        if (url == null) {
            url = mParser.extractHlsUrl();
        }

        // link overview: http://mysite.com/key/value/key2/value2/s/122343435535
        mHlsUrl = MyQueryStringFactory.parse(url);
    }

    private void extractTrackingUrls() {
        String url = mParser.extractTrackingUrl();

        mTrackingUrl = MyQueryStringFactory.parse(url);
    }

    private void extractDashMPDUrl() {
        String url = ParserUtils.extractParam(DASH_MPD_URL, mContent);

        if (url == null) {
            url = mParser.extractDashUrl();
        }

        // dash mpd link overview: http://mysite.com/key/value/key2/value2/s/122343435535
        mDashMPDUrl = new MyPathQueryString(url);
    }

    private List<MediaItem> extractUrlEncodedMediaItems(String content, String queryParam) {
        List<MediaItem> list = new ArrayList<>();
        List<String> items = new ArrayList<>();

        String formats = ParserUtils.extractParam(queryParam, content);

        // stream may not contain formats
        if (formats != null) {
            String[] fmts = formats.split(FORMATS_DELIM);
            items.addAll(Arrays.asList(fmts));
        }

        for (String item : items) {
            list.add(createMediaItem(item));
        }

        return list;
    }

    private List<MediaItem> extractDashMediaItems(String content) {
        return extractUrlEncodedMediaItems(content, DASH_FORMATS);
    }

    private List<MediaItem> extractRegularMediaItems(String content) {
        return extractUrlEncodedMediaItems(content, REGULAR_FORMATS);
    }

    /**
     * player_response={streamingData: {formats: [{itag: 17, ...}]}
     */
    private List<MediaItem> extractJsonMediaItems() {
        List<MediaItem> list = new ArrayList<>();

        if (mParser != null) {
            List<MediaItem> items = mParser.extractDashMediaItems();

            if (items != null) {
                list.addAll(items);
            }

            // Simple formats with integrated video and audio (360p for music, 720p for other content).
            List<MediaItem> items2 = mParser.extractLowQualityFormats();

            if (items2 != null) {
                list.addAll(items2);
            }
        }

        return list;
    }

    private void extractMediaItems() {
        if (mMediaItems != null) {
            return;
        }

        mMediaItems = new ArrayList<>();

        mMediaItems.addAll(extractJsonMediaItems());

        // TODO: signature bug on the VEVO videos
        if (mMediaItems.isEmpty()) {
            Log.d(TAG, "JSON section doesn't contain dash formats... Trying to get ones from url-encoded section...");
            mMediaItems.addAll(extractDashMediaItems(mContent));
        }

        mMediaItems.addAll(extractRegularMediaItems(mContent));
    }

    private MediaItem createMediaItem(Map<String, Object> content) {
        SimpleYouTubeMediaItem mediaItem = new SimpleYouTubeMediaItem();
        mediaItem.setBitrate(Helpers.toIntString(content.get(MediaItem.BITRATE)));
        mediaItem.setUrl(String.valueOf(content.get(MediaItem.URL)));
        mediaItem.setITag(Helpers.toIntString(content.get(MediaItem.ITAG)));
        mediaItem.setType(String.valueOf(content.get(MediaItem.TYPE)));
        mediaItem.setS(String.valueOf(content.get(MediaItem.S)));
        mediaItem.setClen(String.valueOf(content.get(MediaItem.CLEN)));
        mediaItem.setFps(String.valueOf(content.get(MediaItem.FPS)));
        mediaItem.setIndex(String.valueOf(content.get(MediaItem.INDEX)));
        mediaItem.setInit(String.valueOf(content.get(MediaItem.INIT)));
        mediaItem.setSize(String.valueOf(content.get(MediaItem.SIZE)));
        return mediaItem;
    }

    private MediaItem createMediaItem(String content) {
        Uri mediaUrl = ParserUtils.parseUri(content);
        SimpleYouTubeMediaItem mediaItem = new SimpleYouTubeMediaItem();
        mediaItem.setBitrate(mediaUrl.getQueryParameter(MediaItem.BITRATE));
        mediaItem.setUrl(mediaUrl.getQueryParameter(MediaItem.URL));
        mediaItem.setITag(mediaUrl.getQueryParameter(MediaItem.ITAG));
        mediaItem.setType(mediaUrl.getQueryParameter(MediaItem.TYPE));
        mediaItem.setS(mediaUrl.getQueryParameter(MediaItem.S));
        mediaItem.setClen(mediaUrl.getQueryParameter(MediaItem.CLEN));
        mediaItem.setFps(mediaUrl.getQueryParameter(MediaItem.FPS));
        mediaItem.setIndex(mediaUrl.getQueryParameter(MediaItem.INDEX));
        mediaItem.setInit(mediaUrl.getQueryParameter(MediaItem.INIT));
        mediaItem.setSize(mediaUrl.getQueryParameter(MediaItem.SIZE));
        return mediaItem;
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

        for (MediaItem item : mMediaItems) {
            prepareForDecipher(item);
            result.add(findStrangeSignature(item));
        }

        result.add(findStrangeSignature(mDashMPDUrl));
        result.add(findStrangeSignature(mHlsUrl));

        return result;
    }

    private String findStrangeSignature(MyQueryString query) {
        return findStrangeSignature(
                query.get(MediaItem.S));
    }

    private String findStrangeSignature(MediaItem item) {
        MyQueryString query = MyQueryStringFactory.parse(item.getUrl());
        return findStrangeSignature(
                item.getS(),
                query.get(MediaItem.S));
    }

    // NOTE: don't delete
    @Subscribe
    public void decipherSignaturesDone(DecipherOnlySignaturesDoneEvent doneEvent) {
        if (doneEvent.getId() != mId) {
            return;
        }

        Browser.getBus().unregister(this);

        List<String> signatures = doneEvent.getSignatures();
        String lastSignature = signatures.get(signatures.size() - 1);

        doCallbackOnDashMPDUrl(lastSignature);
        doCallbackOnHlsUrl();
        doCallbackOnTrackingUrls();
        doCallbackOnMediaItems(signatures);
    }

    private void doCallbackOnTrackingUrls() {
        if (!ParserUtils.isEmpty(mTrackingUrl)) {
            mListener.onTrackingUrl(Uri.parse(mTrackingUrl.toString()));
        }
    }

    private void doCallbackOnHlsUrl() {
        if (!ParserUtils.isEmpty(mHlsUrl)) {
            mListener.onHlsUrl(Uri.parse(mHlsUrl.toString()));
        }
    }

    private void doCallbackOnDashMPDUrl(String signature) {
        if (!mDashMPDUrl.isEmpty() && signature != null && signature.length() == DECIPHERED_SIGNATURE_LENGTH) {
            mDashMPDUrl.remove(MediaItem.S);
            mDashMPDUrl.set(MediaItem.SIGNATURE, signature);
        } else {
            Log.d(TAG, "Video signature is wrong: " + signature);
        }

        // Looking for qhd formats for live streams. They're here.
        if (!ParserUtils.isEmpty(mDashMPDUrl)) {
            mListener.onDashUrl(Uri.parse(mDashMPDUrl.toString()));
        }

        // NOTE: parser not working properly here, use url
        // NOTE: raw live format could crash exoplayer

        //// parser don't work as expected on this moment
        //SimpleMPDParser parser = new SimpleMPDParser(dashContent);
        //mNewMediaItems = parser.parse();
    }

    /**
     * There are two types of the signatures:<br/>
     * 1) Long signature. It could be in the form ...lsig=xxxx&sig=xxxx...<br/>
     * 2) Short signature. It could be in the form ...signature=xxxxxx...<br/>
     * @param signatures deciphered signatures or nulls if videos not need to be deciphered
     */
    private void doCallbackOnMediaItems(List<String> signatures) {
        if (signatures.size() < mMediaItems.size()) {
            throw new IllegalStateException("Signatures and media items aren't match");
        }

        for (int i = 0; i < mMediaItems.size(); i++) {
            String signature = signatures.get(i);

            MediaItem item = mMediaItems.get(i);

            if (signature == null) {
                continue;
            }

            MyQueryString url = MyQueryStringFactory.parse(item.getUrl());

            if (url.contains(MediaItem.SIGNATURE2_MARK)) {
                url.set(MediaItem.SIGNATURE2, signature);
            } else {
                url.set(MediaItem.SIGNATURE, signature);
            }

            item.setUrl(url.toString());
            item.setSignature(signature);
            item.setS(null);
        }

        if (mNewMediaItems != null) { // NOTE: NPE here
            mMediaItems.addAll(mNewMediaItems);
        }

        mListener.onExtractMediaItemsAndDecipher(mMediaItems);
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
        extractHlsUrl();
        extractTrackingUrls();
        decipherSignatures();
    }

    public interface ParserListener {
        void onHlsUrl(Uri url);
        void onDashUrl(Uri url);
        void onTrackingUrl(Uri url);
        void onExtractMediaItemsAndDecipher(List<MediaItem> items);
    }

    public interface GenericInfo {
        String LENGTH_SECONDS = "length_seconds";
        String TITLE = "title";
        String AUTHOR = "author";
        String VIEW_COUNT = "view_count";
        String TIMESTAMP = "timestamp";
        String getLengthSeconds();
        void setLengthSeconds(String lengthSeconds);
        String getTitle();
        void setTitle(String title);
        String getAuthor();
        void setAuthor(String author);
        String getViewCount();
        void setViewCount(String viewCount);
        String getTimestamp();
        void setTimestamp(String timestamp);
    }

    /**
     * Returns first non-normal signature
     * @param signatures list
     * @return first non-normal signature or null
     */
    private String findStrangeSignature(String... signatures) {
        for (String signature : signatures) {
            if (signature == null) {
                continue;
            }

            return signature;
        }

        return null;
    }

    private void prepareForDecipher(MediaItem item) {
        if (item.getCipher() == null) { // regular video
            return;
        }

        // music video
        Uri parseUri = ParserUtils.parseUri(item.getCipher());
        String cipher = parseUri.getQueryParameter(MediaItem.S);
        String url = parseUri.getQueryParameter(MediaItem.URL);
        item.setS(cipher);
        item.setUrl(url);
    }
}
