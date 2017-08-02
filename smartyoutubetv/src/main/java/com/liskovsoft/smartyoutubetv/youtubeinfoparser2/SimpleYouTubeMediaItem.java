package com.liskovsoft.smartyoutubetv.youtubeinfoparser2;

public class SimpleYouTubeMediaItem implements YouTubeMediaItem {
    private String mITag;
    private String mUrl;
    private String mSignature;
    private String mType;
    private String mClen;
    private String mBitrate;

    public SimpleYouTubeMediaItem(String ITag) {
        mITag = ITag;
    }

    public SimpleYouTubeMediaItem() {
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
        return null;
    }

    @Override
    public void setProjectionType(String projectionType) {

    }

    @Override
    public String getXtags() {
        return null;
    }

    @Override
    public void setXtags(String xtags) {

    }

    @Override
    public String getSize() {
        return null;
    }

    @Override
    public void setSize(String size) {

    }

    @Override
    public String getIndex() {
        return null;
    }

    @Override
    public void setIndex(String index) {

    }

    @Override
    public String getInit() {
        return null;
    }

    @Override
    public void setInit(String init) {

    }

    @Override
    public String getFps() {
        return null;
    }

    @Override
    public void setFps(String fps) {

    }

    @Override
    public String getLmt() {
        return null;
    }

    @Override
    public void setLmt(String lmt) {

    }

    @Override
    public String getQualityLabel() {
        return null;
    }

    @Override
    public void setQualityLabel(String qualityLabel) {

    }

    @Override
    public String getQuality() {
        return null;
    }

    @Override
    public void setQuality(String quality) {

    }

    @Override
    public int compareTo(YouTubeMediaItem item) {
        if (item == null) {
            return 1;
        }

        int thisIndex = ITag.getIndex(getITag());
        int otherIndex = ITag.getIndex(item.getITag());

        return thisIndex - otherIndex;
    }
}
