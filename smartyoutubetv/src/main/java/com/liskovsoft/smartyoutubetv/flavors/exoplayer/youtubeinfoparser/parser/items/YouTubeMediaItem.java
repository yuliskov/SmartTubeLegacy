package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.items;

public interface YouTubeMediaItem extends Comparable<YouTubeMediaItem> {
    // Common params
    String URL = "url";
    String TYPE = "type";
    String ITAG = "itag";
    String S = "s";
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
}
