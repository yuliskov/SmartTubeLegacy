package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryStringFactory;

import java.util.List;

/**
 * New item format:<br/>
 * <pre>
 *           {
 *         "itag": 18,
 *         "mimeType": "video/mp4;+codecs=\"avc1.42001E,+mp4a.40.2\"",
 *         "bitrate": 695556,
 *         "width": 640,
 *         "height": 360,
 *         "lastModified": "1588783240548499",
 *         "contentLength": "78853547",
 *         "quality": "medium",
 *         "qualityLabel": "360p",
 *         "projectionType": "RECTANGULAR",
 *         "averageBitrate": 695497,
 *         "audioQuality": "AUDIO_QUALITY_LOW",
 *         "approxDurationMs": "907017",
 *         "audioSampleRate": "44100",
 *         "audioChannels": 2,
 *         "signatureCipher": "s=9%3DwsGH8_Uw-5bYJmQ6GgJxKZFI1yJiZOAEhhRAYpwdulLDQ%3DCguM8KE9AgYBxlWX4KIAnjXNUufq7Yplk4vzMh-7ktmTgIQRwsLlPpJhh&sp=sig&url=https://r7---sn-8ph2xajvh-axqe.googlevideo.com/videoplayback%3Fexpire%3D1588805188%26ei%3D5OmyXtXOEobyyQWDi7fQBw%26ip%3D83.102.135.63%26id%3Do-AO5sk2GQV-aO5PZQVvs5Idm9ASTADVWHwNeuIZOFukkr%26itag%3D18%26source%3Dyoutube%26requiressl%3Dyes%26mh%3Dqe%26mm%3D31%252C29%26mn%3Dsn-8ph2xajvh-axqe%252Csn-n8v7znsl%26ms%3Dau%252Crdu%26mv%3Dm%26mvi%3D6%26pl%3D24%26initcwndbps%3D1173750%26vprv%3D1%26mime%3Dvideo%252Fmp4%26gir%3Dyes%26clen%3D78853547%26ratebypass%3Dyes%26dur%3D907.017%26lmt%3D1588783240548499%26mt%3D1588783475%26fvip%3D7%26beids%3D23886202%26c%3DTVHTML5%26txp%3D5531432%26sparams%3Dexpire%252Cei%252Cip%252Cid%252Citag%252Csource%252Crequiressl%252Cvprv%252Cmime%252Cgir%252Cclen%252Cratebypass%252Cdur%252Clmt%26lsparams%3Dmh%252Cmm%252Cmn%252Cms%252Cmv%252Cmvi%252Cpl%252Cinitcwndbps%26lsig%3DALrAebAwRQIgU-PciFKP-ssB4J6-WryCHbWb7kOQRQjkoN3QVQMm314CIQCNcc5j_Hf7SCQJUWM3GJsk0sLsdL9IMkpF0zVbxVfaiQ%253D%253D"
 *       }
 * </pre>
 */
public class SimpleYouTubeMediaItem implements MediaItem {
    private static final String FORMAT_STREAM_TYPE_OTF = "FORMAT_STREAM_TYPE_OTF";

    private class Range {
        @SerializedName("start")
        String start;
        @SerializedName("end")
        String end;

        @NonNull
        @Override
        public String toString() {
            if (start == null || end == null) {
                return "";
            }

            return String.format("%s-%s", start, end);
        }
    }
    @SerializedName("itag")
    private String mITag;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("cipher")
    private String mCipher;
    private String mSignatureCiphered;
    @SerializedName("mimeType")
    private String mType;
    @SerializedName("contentLength")
    private String mClen;
    @SerializedName("bitrate")
    private String mBitrate;
    private String mProjectionType;
    private String mXtags;
    @SerializedName("width")
    private String mWidth;
    @SerializedName("height")
    private String mHeight;
    private String mSize;
    @SerializedName("indexRange")
    private Range mIndexRange;
    @SerializedName("initRange")
    private Range mInitRange;
    private String mIndex;
    private String mInit;
    @SerializedName("fps")
    private String mFps;
    private String mLmt;
    @SerializedName("qualityLabel")
    private String mQualityLabel;
    @SerializedName("quality")
    private String mQuality;
    private String mRealSignature;
    @SerializedName("audioSampleRate")
    private String mAudioSamplingRate;
    private String mSourceURL;
    private List<String> mSegmentUrlList;
    private List<String> mGlobalSegmentList;
    /**
     * New format type FORMAT_STREAM_TYPE_OTF or null
     */
    @SerializedName("type")
    private String mFormat;
    @SerializedName("signatureCipher")
    private String mSignatureCipher;
    private String mTemplateMetadataUrl;
    private String mTemplateSegmentUrl;

    public SimpleYouTubeMediaItem(String ITag) {
        mITag = ITag;
    }

    public SimpleYouTubeMediaItem() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem rightItem = (MediaItem) obj;
        return getITag().equals(rightItem.getITag());
    }



    @Override
    public String getUrl() {
        parseCipher();

        return mUrl;
    }

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String getSignatureCipher() {
        parseCipher();

        return mSignatureCiphered;
    }

    @Override
    public void setSignatureCipher(String s) {
        mSignatureCiphered = s;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void setType(String type) {
        mType = type;
    }

    @Override
    public String getITag() {
        return mITag;
    }

    @Override
    public void setITag(String itag) {
        mITag = itag;
    }

    @Override
    public String getClen() {
        return mClen;
    }

    @Override
    public void setClen(String clen) {
        mClen = clen;
    }

    @Override
    public String getBitrate() {
        return mBitrate;
    }

    @Override
    public void setBitrate(String bitrate) {
        mBitrate = bitrate;
    }

    @Override
    public String getProjectionType() {
        return mProjectionType;
    }

    @Override
    public void setProjectionType(String projectionType) {
        mProjectionType = projectionType;
    }

    @Override
    public String getXtags() {
        return mXtags;
    }

    @Override
    public void setXtags(String xtags) {
        mXtags = xtags;
    }

    @Override
    public String getSize() {
        if (mSize == null && mWidth != null && mHeight != null) {
            return String.format("%sx%s", mWidth, mHeight);
        }

        return mSize;
    }

    @Override
    public void setSize(String size) {
        mSize = size;
    }

    @Override
    public String getIndex() {
        if (mIndex == null && mIndexRange != null) {
            mIndex = mIndexRange.toString();
        }

        return mIndex;
    }

    @Override
    public void setIndex(String index) {
        mIndex = index;
    }

    @Override
    public String getInit() {
        if (mInit == null && mInitRange != null) {
            mInit = mInitRange.toString();
        }

        return mInit;
    }

    @Override
    public void setInit(String init) {
        mInit = init;
    }

    @Override
    public String getFps() {
        return mFps;
    }

    @Override
    public void setFps(String fps) {
        mFps = fps;
    }

    @Override
    public String getLmt() {
        return mLmt;
    }

    @Override
    public void setLmt(String lmt) {
        mLmt = lmt;
    }

    @Override
    public String getQualityLabel() {
        return mQualityLabel;
    }

    @Override
    public void setQualityLabel(String qualityLabel) {
        mQualityLabel = qualityLabel;
    }

    @Override
    public String getQuality() {
        return mQuality;
    }

    @Override
    public void setQuality(String quality) {
        mQuality = quality;
    }

    @Override
    public boolean belongsToType(String type) {
        return type == null || ITag.belongsToType(type, getITag());
    }

    @Override
    public void setSignature(String signature) {
        mRealSignature = signature;
    }

    @Override
    public String getSignature() {
        return mRealSignature;
    }

    @Override
    public void setAudioSamplingRate(String audioSamplingRate) {
        mAudioSamplingRate = audioSamplingRate;
    }

    @Override
    public String getAudioSamplingRate() {
        return mAudioSamplingRate;
    }

    @Override
    public String getSourceURL() {
        return mSourceURL;
    }

    @Override
    public void setSourceURL(String sourceURL) {
        mSourceURL = sourceURL;
    }

    @Override
    public List<String> getSegmentUrlList() {
        return mSegmentUrlList;
    }

    @Override
    public void setSegmentUrlList(List<String> urls) {
        mSegmentUrlList = urls;
    }

    @Override
    public List<String> getGlobalSegmentList() {
        return mGlobalSegmentList;
    }

    @Override
    public void setGlobalSegmentList(List<String> segments) {
        mGlobalSegmentList = segments;
    }

    @Override
    public String getFormat() {
        return mFormat;
    }

    @Override
    public boolean isOTF() {
        return mFormat != null && mFormat.equals(FORMAT_STREAM_TYPE_OTF);
    }

    /**
     * Contains segments list and durations (required for stream switch!!!)
     */
    @Override
    public String getOtfInitUrl() {
        if (mTemplateMetadataUrl == null && getUrl() != null) {
            mTemplateMetadataUrl = getUrl() + "&sq=0";
        }

        return mTemplateMetadataUrl;
    }

    @Override
    public String getOtfTemplateUrl() {
        if (mTemplateSegmentUrl == null && getUrl() != null) {
            mTemplateSegmentUrl = getUrl() + "&sq=$Number$";
        }

        return mTemplateSegmentUrl;
    }

    private void parseCipher() {
        if (mUrl == null && mSignatureCiphered == null) {
            String cipherUri = mCipher == null ? mSignatureCipher : mCipher;

            if (cipherUri != null) {
                MyQueryString queryString = MyQueryStringFactory.parse(cipherUri);
                mUrl = queryString.get(MediaItem.URL);
                mSignatureCiphered = queryString.get(MediaItem.S);
            }
        }
    }

    @Override
    public int compareTo(MediaItem item) {
        if (item == null) {
            return 1;
        }

        return ITag.compare(getITag(), item.getITag());
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(
                "{Url: %s, Source url: %s, Signature: %s, Clen: %s, Size: %s, ITag: %s}",
                getUrl(),
                getSourceURL(),
                getSignature(),
                getClen(),
                getSize(),
                getITag());
    }
}
