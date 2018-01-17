package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser;

import android.net.Uri;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.SimpleYouTubeInfoParser;

import java.util.List;

/**
 * Parses input (get_video_info) to {@link Subtitle}
 */
public class YouTubeSubParser {
    private static final String TAG = YouTubeSubParser.class.getSimpleName();
    private static final String PLAYER_RESPONSE_KEY = "player_response";
    private static final String ALL_SUBS_PATH = "$.captions.playerCaptionsTracklistRenderer.captionTracks";
    private static final String SINGLE_SUB_URL_PATH = "$.captions.playerCaptionsTracklistRenderer.captionTracks[0].baseUrl";
    private static final String VTT_MIME_TYPE = "text/vtt";
    private static final String VTT_PARAM = "vtt";
    private static final String VTT_CODECS = "wvtt";
    private DocumentContext mParser;

    /**
     * Extracts subtitle, images and other objects from the <em>get_video_info</em> file
     * <br/>
     * For video object parsing use {@link SimpleYouTubeInfoParser}
     * @param content get_video_info file content
     */
    public YouTubeSubParser(String content) {
        if (content == null) {
            throw new IllegalStateException("content cannot be null");
        }

        Uri videoInfo = Uri.parse("http://empty.url?" + content);
        String playerResponse = videoInfo.getQueryParameter(PLAYER_RESPONSE_KEY);

        if (playerResponse == null)
            return;

        Configuration conf = Configuration
                .builder()
                .mappingProvider(new GsonMappingProvider())
                .jsonProvider(new GsonJsonProvider())
                .build();

        mParser = JsonPath
                .using(conf)
                .parse(playerResponse);

    }

    public List<Subtitle> getAllSubs() {
        if (mParser == null)
            return null;

        TypeRef<List<Subtitle>> typeRef = new TypeRef<List<Subtitle>>() {};
        try {
            List<Subtitle> subs = mParser.read(ALL_SUBS_PATH, typeRef);
            addMimeTypes(subs);
            return subs;
        } catch (PathNotFoundException e) {
            String msg = "It is ok. Video does not have a subtitles";
            Log.i(TAG, msg);
        }
        return null;
    }

    /**
     * To show subtitles in <code>vtt</code> format just add <code>fmt=vtt</code> at the end of url
     * <br/>
     * Example: <code>https://www.youtube.com/api/timedtext?lang=en&v=MhQKe-aERsU&fmt=vtt</code>
     * @param subs list to process
     */
    private void addMimeTypes(List<Subtitle> subs) {
        if (subs == null)
            return;
        for (Subtitle sub : subs) {
            sub.setBaseUrl(String.format("%s&fmt=%s", sub.getBaseUrl(), VTT_PARAM));
            sub.setMimeType(VTT_MIME_TYPE);
            sub.setCodecs(VTT_CODECS);
        }
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
        private Name mName;
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

        public Name getName() {
            return mName;
        }

        public void setName(Name name) {
            mName = name;
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
    }
}
