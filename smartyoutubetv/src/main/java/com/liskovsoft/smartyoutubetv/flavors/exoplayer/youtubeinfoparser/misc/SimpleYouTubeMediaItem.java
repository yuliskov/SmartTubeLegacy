package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;

import java.util.List;

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
    private String mSignature;
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
        return mUrl;
    }

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String getCipher() {
        return mCipher;
    }

    @Override
    public void setCipher(String cipher) {
        mCipher = cipher;
    }

    @Override
    public String getS() {
        return mSignature;
    }

    @Override
    public void setS(String s) {
        mSignature = s;
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
