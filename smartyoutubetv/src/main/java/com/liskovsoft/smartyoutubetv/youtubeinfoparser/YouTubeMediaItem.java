package com.liskovsoft.smartyoutubetv.youtubeinfoparser;

public interface YouTubeMediaItem {
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
}
