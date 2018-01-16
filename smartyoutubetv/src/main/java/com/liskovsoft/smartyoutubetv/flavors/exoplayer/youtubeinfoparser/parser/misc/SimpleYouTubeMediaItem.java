package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.ITag;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.YouTubeMediaParser.MediaItem;

public class SimpleYouTubeMediaItem implements MediaItem {
    private String mITag;
    private String mUrl;
    private String mSignature;
    private String mType;
    private String mClen;
    private String mBitrate;
    private String mProjectionType;
    private String mXtags;
    private String mSize;
    private String mIndex;
    private String mInit;
    private String mFps;
    private String mLmt;
    private String mQualityLabel;
    private String mQuality;
    private String mRealSignature;
    private String mAudioSamplingRate;

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
        return mSize;
    }

    @Override
    public void setSize(String size) {
        mSize = size;
    }

    @Override
    public String getIndex() {
        return mIndex;
    }

    @Override
    public void setIndex(String index) {
        mIndex = index;
    }

    @Override
    public String getInit() {
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
    public int compareTo(MediaItem item) {
        if (item == null) {
            return 1;
        }

        return ITag.compare(getITag(), item.getITag());
    }
}
