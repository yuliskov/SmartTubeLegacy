package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import com.google.gson.annotations.SerializedName;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.TypeRef;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeMediaItem;

import java.util.ArrayList;
import java.util.List;

public class JsonInfoParser {
    private static final String TAG = JsonInfoParser.class.getSimpleName();
    private static final String JSON_INFO = "player_response";
    private static final String JSON_INFO_REGULAR_FORMATS = "$.streamingData.formats";
    private static final String JSON_INFO_DASH_FORMATS = "$.streamingData.adaptiveFormats";
    private static final String JSON_INFO_DASH_URL = "$.streamingData.dashManifestUrl";
    private static final String JSON_INFO_HLS_URL = "$.streamingData.hlsManifestUrl";
    private static final String JSON_INFO_TRACKING_URL = "$.playbackTracking.videostatsWatchtimeUrl.baseUrl";
    private static final String JSON_INFO_ALL_SUBS = "$.captions.playerCaptionsTracklistRenderer.captionTracks";
    private static final String JSON_INFO_SINGLE_SUB_URL = "$.captions.playerCaptionsTracklistRenderer.captionTracks[0].baseUrl";
    private static final String JSON_INFO_STORY_SPEC = "$.storyboards.playerStoryboardSpecRenderer.spec";
    private static final String JSON_INFO_VIDEO_LENGTH = "$.videoDetails.lengthSeconds";
    private final DocumentContext mParser;

    public JsonInfoParser(String content) {
        String jsonInfo = ParserUtils.extractParam(JSON_INFO, content);
        mParser = ParserUtils.createJsonInfoParser(jsonInfo);
    }

    private List<MediaItem> extractMediaItems(String jsonPath) {
        TypeRef<List<SimpleYouTubeMediaItem>> typeRef = new TypeRef<List<SimpleYouTubeMediaItem>>() {};

        List<MediaItem> list = new ArrayList<>();

        try {
            list.addAll(mParser.read(jsonPath, typeRef));
        } catch (PathNotFoundException e) {
            String msg = "It is ok. JSON content doesn't contains param: " + jsonPath;
            Log.d(TAG, msg);
        }

        return list;
    }

    public List<Subtitle> extractAllSubs() {
        if (mParser == null) {
            return null;
        }

        TypeRef<List<Subtitle>> typeRef = new TypeRef<List<Subtitle>>() {};

        try {
            List<Subtitle> subs = mParser.read(JSON_INFO_ALL_SUBS, typeRef);
            return subs;
        } catch (PathNotFoundException e) {
            String msg = "It is ok. Video does not have a subtitles";
            Log.i(TAG, msg);
        }

        return null;
    }

    public String extractHlsUrl() {
        return ParserUtils.extractString(JSON_INFO_HLS_URL, mParser);
    }

    public String extractTrackingUrl() {
        return ParserUtils.extractString(JSON_INFO_TRACKING_URL, mParser);
    }

    public String extractDashUrl() {
        return ParserUtils.extractString(JSON_INFO_DASH_URL, mParser);
    }

    public List<MediaItem> extractDashMediaItems() {
        if (mParser == null) {
            return null;
        }

        return extractMediaItems(JSON_INFO_DASH_FORMATS);
    }

    public String extractStorySpec() {
        return ParserUtils.extractString(JSON_INFO_STORY_SPEC, mParser);
    }

    public List<MediaItem> extractLowQualityFormats() {
        if (mParser == null) {
            return null;
        }

        return extractMediaItems(JSON_INFO_REGULAR_FORMATS);
    }

    public String extractDurationMs() {
        return ParserUtils.extractString(JSON_INFO_VIDEO_LENGTH, mParser);
    }

    public class Subtitle {
        /**
         * Example: "https://www.youtube.com/api/timedtext?caps=&key=yt…&sparams=caps%2Cv%2Cxorp%2Cexpire&lang=en&name=en"
         */
        @SerializedName("baseUrl")
        private String mBaseUrl;
        /**
         * Example: true
         */
        @SerializedName("isTranslatable")
        private boolean mIsTranslatable;
        /**
         * Example: "en"
         */
        @SerializedName("languageCode")
        private String mLanguageCode;
        /**
         * Example: ".en.nP7-2PuUl7o"
         */
        @SerializedName("vssId")
        private String mVssId;
        /**
         * Example: see {@link Name} class
         */
        @SerializedName("name")
        private Name2 mName;
        private String mMimeType;
        private String mCodecs;

        public String getBaseUrl() {
            return mBaseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            mBaseUrl = baseUrl;
        }

        public boolean isTranslatable() {
            return mIsTranslatable;
        }

        public void setTranslatable(boolean translatable) {
            mIsTranslatable = translatable;
        }

        public String getLanguageCode() {
            return mLanguageCode;
        }

        public void setLanguageCode(String languageCode) {
            mLanguageCode = languageCode;
        }

        public String getVssId() {
            return mVssId;
        }

        public void setVssId(String vssId) {
            mVssId = vssId;
        }

        public String getName() {
            if (mName == null || mName.getTitles() == null) {
                return null;
            }

            return mName.getTitles()[0].getText();
        }

        public void setName(String name) {
            if (mName == null || mName.getTitles() == null) {
                return;
            }

            mName.getTitles()[0].setText(name);
        }

        public String getMimeType() {
            return mMimeType;
        }

        public void setMimeType(String mimeType) {
            mMimeType = mimeType;
        }

        public String getCodecs() {
            return mCodecs;
        }

        public void setCodecs(String codecs) {
            mCodecs = codecs;
        }

        public class Name {
            /**
             * Example: "English+-+en"
             */
            @SerializedName("simpleText")
            private String mSimpleText;

            public String getSimpleText() {
                return mSimpleText;
            }

            public void setSimpleText(String simpleText) {
                mSimpleText = simpleText;
            }
        }

        public class Name2 {
            @SerializedName("runs")
            private Title[] mTitles;

            public Title[] getTitles() {
                return mTitles;
            }

            private class Title {
                @SerializedName("text")
                private String mText;

                public String getText() {
                    return mText;
                }

                public void setText(String text) {
                    mText = text;
                }
            }
        }
    }

    public interface MediaItem extends Comparable<MediaItem> {
        // Common params
        String URL = "url";
        String CIPHER = "cipher";
        String TYPE = "type";
        String ITAG = "itag";
        String S = "s";
        String SIGNATURE = "signature";
        String SIGNATURE2 = "sig";
        String SIGNATURE2_MARK = "lsig";
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
        // music videos only
        String getCipher();
        void setCipher(String url);
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
        String getFormat();
        boolean isOTF();

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
}
