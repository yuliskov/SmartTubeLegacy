package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main;

import android.net.Uri;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyPathQueryString;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp.CipherUtils;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Parses input (get_video_info) to {@link MediaItem}
 */
public class YouTubeMediaParser {
    private static final String TAG = YouTubeMediaParser.class.getSimpleName();
    private static final String DASH_MPD_URL = "dashmpd";
    private static final String HLS_URL = "hlsvp";
    private static final String DASH_FORMATS = "adaptive_fmts";
    private static final String REGULAR_FORMATS = "url_encoded_fmt_stream_map";
    private static final String FORMATS_DELIM = ","; // %2C
    private static final String JSON_INFO = "player_response";
    private static final String JSON_INFO_REGULAR_FORMATS = "$.streamingData.formats";
    private static final String JSON_INFO_DASH_FORMATS = "$.streamingData.adaptiveFormats";
    private static final String JSON_INFO_DASH_URL = "$.streamingData.dashManifestUrl";
    private static final String JSON_INFO_HLS_URL = "$.streamingData.hlsManifestUrl";
    private static final String JSON_INFO_TRACKING_URL = "$.playbackTracking.videostatsWatchtimeUrl.baseUrl";
    private static final int COMMON_SIGNATURE_LENGTH = 81;

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
    private DocumentContext mParser;

    public YouTubeMediaParser(String content) {
        mContent = content;
        mId = new Random().nextInt();

        initJsonParser();
    }

    private void initJsonParser() {
        String jsonInfo = extractParam(mContent, JSON_INFO);

        if (jsonInfo == null) {
            return;
        }

        Configuration conf = Configuration
                .builder()
                .mappingProvider(new GsonMappingProvider())
                .jsonProvider(new GsonJsonProvider())
                .build();

        mParser = JsonPath
                .using(conf)
                .parse(jsonInfo);
    }

    public GenericInfo extractGenericInfo() {
        GenericInfo info = new SimpleYouTubeGenericInfo();
        Uri videoInfo = parseUri(mContent);
        info.setLengthSeconds(videoInfo.getQueryParameter(GenericInfo.LENGTH_SECONDS));
        info.setTitle(videoInfo.getQueryParameter(GenericInfo.TITLE));
        info.setAuthor(videoInfo.getQueryParameter(GenericInfo.AUTHOR));
        info.setViewCount(videoInfo.getQueryParameter(GenericInfo.VIEW_COUNT));
        info.setTimestamp(videoInfo.getQueryParameter(GenericInfo.TIMESTAMP));
        return info;
    }

    private void extractHlsUrl() {
        String url = extractParam(mContent, HLS_URL);

        if (url == null) {
            url = extractJson(mParser, JSON_INFO_HLS_URL);
        }

        // link overview: http://mysite.com/key/value/key2/value2/s/122343435535
        mHlsUrl = MyQueryStringFactory.parse(url);
    }

    private void extractTrackingUrls() {
        String url = extractJson(mParser, JSON_INFO_TRACKING_URL);

        mTrackingUrl = MyQueryStringFactory.parse(url);
    }

    private void extractDashMPDUrl() {
        String url = extractParam(mContent, DASH_MPD_URL);

        if (url == null) {
            url = extractJson(mParser, JSON_INFO_DASH_URL);
        }

        // dash mpd link overview: http://mysite.com/key/value/key2/value2/s/122343435535
        mDashMPDUrl = new MyPathQueryString(url);
    }

    private List<MediaItem> extractUrlEncodedMediaItems(String content, String queryParam) {
        List<MediaItem> list = new ArrayList<>();
        List<String> items = new ArrayList<>();

        String formats = extractParam(content, queryParam);

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
            // list.addAll(extractJsonList(mParser, JSON_INFO_REGULAR_FORMATS));
            list.addAll(extractJsonList(mParser, JSON_INFO_DASH_FORMATS));
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
        Uri mediaUrl = parseUri(content);
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
            result.add(getStrangeSignature(item));
        }

        result.add(getStrangeSignature(mDashMPDUrl));
        result.add(getStrangeSignature(mHlsUrl));

        return result;
    }

    private String getStrangeSignature(MyQueryString query) {
        return getStrangeSignature(query.get(MediaItem.S), query.get(MediaItem.SIGNATURE));
    }

    private String getStrangeSignature(MediaItem item) {
        MyQueryString query = MyQueryStringFactory.parse(item.getUrl());
        return getStrangeSignature(item.getS(), item.getSignature(), query.get(MediaItem.S), query.get(MediaItem.SIGNATURE));
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
        if (!isEmpty(mTrackingUrl)) {
            mListener.onTrackingUrl(Uri.parse(mTrackingUrl.toString()));
        }
    }

    private void doCallbackOnHlsUrl() {
        if (!isEmpty(mHlsUrl)) {
            mListener.onHlsUrl(Uri.parse(mHlsUrl.toString()));
        }
    }

    private void doCallbackOnDashMPDUrl(String signature) {
        if (!mDashMPDUrl.isEmpty() && signature != null && signature.length() == COMMON_SIGNATURE_LENGTH) {
            mDashMPDUrl.remove(MediaItem.S);
            mDashMPDUrl.set(MediaItem.SIGNATURE, signature);
        } else {
            Log.d(TAG, "Video signature is wrong: " + signature);
        }

        // Looking for qhd formats for live streams. They're here.
        if (!isEmpty(mDashMPDUrl)) {
            mListener.onDashUrl(Uri.parse(mDashMPDUrl.toString()));
        }

        // NOTE: parser not working properly here, use url
        // NOTE: raw live format could crash exoplayer

        // parser don't work as expected on this moment
        //SimpleMPDParser parser = new SimpleMPDParser(dashContent);
        //mNewMediaItems = parser.parse();
    }

    private void doCallbackOnMediaItems(List<String> signatures) {
        if (signatures.size() < mMediaItems.size()) {
            throw new IllegalStateException("Signatures and media items aren't match");
        }

        for (int i = 0; i < mMediaItems.size(); i++) {
            String signature = signatures.get(i);

            if (signature == null) {
                continue;
            }

            MediaItem item = mMediaItems.get(i);
            MyQueryString url = MyQueryStringFactory.parse(item.getUrl());
            url.set(MediaItem.SIGNATURE, signature);
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

    public interface MediaItem extends Comparable<MediaItem> {
        // Common params
        String URL = "url";
        String TYPE = "type";
        String ITAG = "itag";
        String S = "s";
        String SIGNATURE = "signature";
        // End Common params

        // DASH params
        String CLEN = "clen";
        String BITRATE = "bitrate";
        String PROJECTION_TYPE = "projection_type";
        String XTAGS = "xtags";
        String SIZE = "size";
        String INDEX = "index";
        String FPS = "fps";
        String LMT = "lmt";
        String QUALITY_LABEL = "quality_label";
        String INIT = "init";
        // End DASH params

        // Regular video params
        String QUALITY = "quality";
        // End Regular params

        // Common
        String getUrl();
        void setUrl(String url);
        String getS();
        void setS(String s);
        String getType();
        void setType(String type);
        String getITag();
        void setITag(String itag);

        // DASH
        String getClen();
        void setClen(String clen);
        String getBitrate();
        void setBitrate(String bitrate);
        String getProjectionType();
        void setProjectionType(String projectionType);
        String getXtags();
        void setXtags(String xtags);
        String getSize();
        void setSize(String size);
        String getIndex();
        void setIndex(String index);
        String getInit();
        void setInit(String init);
        String getFps();
        void setFps(String fps);
        String getLmt();
        void setLmt(String lmt);
        String getQualityLabel();
        void setQualityLabel(String qualityLabel);

        // Other/Regular
        String getQuality();
        void setQuality(String quality);
        boolean belongsToType(String type);
        void setSignature(String signature);
        String getSignature();
        void setAudioSamplingRate(String audioSamplingRate);
        String getAudioSamplingRate();
        void setSourceURL(String sourceURL);
        String getSourceURL();
        List<String> getSegmentUrlList();
        void setSegmentUrlList(List<String> urls);
        List<String> getGlobalSegmentList();
        void setGlobalSegmentList(List<String> segments);
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

    // Utils

    private static List<MediaItem> extractJsonList(DocumentContext parser, String jsonPath) {
        TypeRef<List<SimpleYouTubeMediaItem>> typeRef = new TypeRef<List<SimpleYouTubeMediaItem>>() {};

        List<MediaItem> list = new ArrayList<>();

        try {
            list.addAll(parser.read(jsonPath, typeRef));
        } catch (PathNotFoundException e) {
            String msg = "It is ok. JSON content doesn't contains param: " + jsonPath;
            Log.d(TAG, msg);
        }

        return list;
    }

    private static String extractJson(DocumentContext parser, String jsonPath) {
        TypeRef<String> typeRef = new TypeRef<String>() {};

        String result = null;

        try {
            result = parser.read(jsonPath, typeRef);
        } catch (PathNotFoundException e) {
            String msg = "It is ok. JSON content doesn't contains param: " + jsonPath;
            Log.d(TAG, msg);
        }

        return result;
    }

    private static String extractParam(String content, String queryParam) {
        Uri videoInfo = parseUri(content);
        String value = videoInfo.getQueryParameter(queryParam);

        if (value != null && value.isEmpty()) {
            return null;
        }

        return value;
    }

    private static Uri parseUri(String content) {
        if (content.startsWith("http")) {
            return Uri.parse(content);
        }

        return Uri.parse("http://example.com?" + content);
    }

    /**
     * Returns first non-normal signature
     * @param signatures list
     * @return first non-normal signature or null
     */
    private String getStrangeSignature(String... signatures) {
        for (String signature : signatures) {
            if (signature == null) {
                continue;
            }

            if (!isValidSignature(signature)) {
                return signature;
            }
        }

        return null;
    }

    private static boolean isValidSignature(String signature) {
        if (signature == null) {
            return false;
        }

        return signature.length() == COMMON_SIGNATURE_LENGTH;
    }

    private static boolean isEmpty(MyQueryString queryString) {
        if (queryString == null) {
            return true;
        }

        return queryString.isEmpty();
    }
}
