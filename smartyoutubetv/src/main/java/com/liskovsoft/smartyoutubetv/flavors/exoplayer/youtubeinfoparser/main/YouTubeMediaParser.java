package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.main;

import android.net.Uri;
import android.util.Log;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.common.okhttp.OkHttpHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesDoneEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.events.DecipherOnlySignaturesEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.WeirdUrl;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd.SimpleMPDParser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp.CipherUtils;
import com.squareup.otto.Subscribe;
import okhttp3.Response;

import java.io.InputStream;
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
    private static final String DASH_FORMATS_JSON = "player_response";
    private static final String DASH_FORMATS = "adaptive_fmts";
    private static final String REGULAR_FORMATS = "url_encoded_fmt_stream_map";
    private static final String FORMATS_DELIM = ","; // %2C
    private static final String STREAM_DATA_JSON_KEY = "streamingData";
    private static final String FORMATS_JSON_KEY = "formats";
    private int COMMON_SIGNATURE_LENGTH = 81;

    private final String mContent;
    private final int mId;
    private ParserListener mListener;
    private List<MediaItem> mMediaItems;
    /**
     * Path to *.mpd playlist
     */
    private WeirdUrl mDashMPDUrl;
    private List<MediaItem> mNewMediaItems;

    public YouTubeMediaParser(String content) {
        mContent = content;
        mId = new Random().nextInt();
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

    public Uri extractHLSUrl() {
        Uri videoInfo = parseUri(mContent);
        String hlsUrl = videoInfo.getQueryParameter(HLS_URL);
        if (hlsUrl != null) {
            return Uri.parse(hlsUrl);
        }
        return null;
    }

    private void extractDashMPDUrl() {
        String url = extractParam(mContent, DASH_MPD_URL);
        // dash mpd link overview: http://mysite.com/key/value/key2/value2/s/122343435535
        mDashMPDUrl = new WeirdUrl(url);
    }

    private String extractParam(String content, String queryParam) {
        Uri videoInfo = parseUri(content);
        String value = videoInfo.getQueryParameter(queryParam);

        if (value != null && value.isEmpty()) {
            return null;
        }

        return value;
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

    private List<MediaItem> extractSimpleMediaItems(String content) {
        return extractUrlEncodedMediaItems(content, REGULAR_FORMATS);
    }

    /**
     * player_response={streamingData: {formats: [{itag: 17, ...}]}
     */
    private List<MediaItem> extractJsonMediaItems(String content) {
        List<MediaItem> list = new ArrayList<>();

        String formats = extractParam(content, DASH_FORMATS_JSON);

        if (formats != null) {
            Map<String, Map<String, List<Map<String, Object>>>> objectMap = Helpers.convertToObj(formats);
            Map<String, List<Map<String, Object>>> streamData = objectMap.get(STREAM_DATA_JSON_KEY);
            if (streamData != null) {
                List<Map<String, Object>> fmts = streamData.get(FORMATS_JSON_KEY);

                for (Map<String, Object> fmt : fmts) {
                    list.add(createMediaItem(fmt));
                }
            }
        }

        return list;
    }

    private void extractMediaItems() {
        if (mMediaItems != null) {
            return;
        }

        mMediaItems = new ArrayList<>();
        mMediaItems.addAll(extractDashMediaItems(mContent));
        mMediaItems.addAll(extractJsonMediaItems(mContent));
        mMediaItems.addAll(extractSimpleMediaItems(mContent));
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

    private InputStream extractDashMPDContent() {
        String dashmpdUrl = mDashMPDUrl.toString();
        if (dashmpdUrl != null) {
            // handle null response: 403 (Auth error)
            Response response = OkHttpHelpers.doOkHttpRequest(dashmpdUrl);
            return response == null ? null : response.body().byteStream();
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
        for (MediaItem item : mMediaItems) {
            result.add(item.getS());
        }
        String rawSignature = mDashMPDUrl.getParam(MediaItem.S);
        result.add(rawSignature);
        return result;
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
        applySignatureAndParseDashMPDUrl(lastSignature);
        applySignaturesToMediaItems(signatures);
        mergeMediaItems();
        mListener.onExtractMediaItemsAndDecipher(mMediaItems);
    }

    private void mergeMediaItems() {
        if (mNewMediaItems != null) { // NOTE: NPE here
            mMediaItems.addAll(mNewMediaItems);
        }
    }

    private void applySignatureAndParseDashMPDUrl(String signature) {
        if (mDashMPDUrl.isEmpty()) {
            return;
        }

        if (signature != null && signature.length() == COMMON_SIGNATURE_LENGTH) {
            mDashMPDUrl.removeParam(MediaItem.S);
            mDashMPDUrl.setParam(MediaItem.SIGNATURE, signature);
        } else {
            Log.d(TAG, "Video signature is wrong: " + signature);
        }

        InputStream inputStream = extractDashMPDContent();
        SimpleMPDParser parser = new SimpleMPDParser(inputStream);
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
            MediaItem item = mMediaItems.get(i);
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

    private Uri parseUri(String content) {
        return Uri.parse("http://example.com?" + content);
    }

    public interface ParserListener {
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
}
