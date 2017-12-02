package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import android.util.Xml;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.ITag;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demos: https://github.com/Dash-Industry-Forum/dash-live-source-simulator/wiki/Test-URLs
 */
public class MyMPDBuilder implements MPDBuilder {
    private static final String MIME_WEBM_AUDIO = "audio/webm";
    private static final String MIME_WEBM_VIDEO = "video/webm";
    private static final String MIME_MP4_AUDIO = "audio/mp4";
    private static final String MIME_MP4_VIDEO = "video/mp4";
    private final YouTubeGenericInfo mInfo;
    private XmlSerializer mXmlSerializer;
    private StringWriter mWriter;
    private int mId;
    private List<YouTubeMediaItem> mMP4Audios;
    private List<YouTubeMediaItem> mMP4Videos;
    private List<YouTubeMediaItem> mWEBMAudios;
    private List<YouTubeMediaItem> mWEBMVideos;

    public MyMPDBuilder(YouTubeGenericInfo info) {
        mInfo = info;
        mMP4Audios = new ArrayList<>();
        mMP4Videos = new ArrayList<>();
        mWEBMAudios = new ArrayList<>();
        mWEBMVideos = new ArrayList<>();

        initXmlSerializer();
    }

    private void initXmlSerializer() {
        mXmlSerializer = Xml.newSerializer();
        mWriter = new StringWriter();

        setOutput(mXmlSerializer, mWriter);

        startDocument(mXmlSerializer);
        mXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
    }

    private void writePrologue() {
        String duration = "PT309S";
        if (mInfo != null) {
            duration = String.format("PT%sS", mInfo.getLengthSeconds());
        }

        startTag("", "MPD");
        attribute("", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        attribute("", "xmlns", "urn:mpeg:DASH:schema:MPD:2011");
        attribute("", "xmlns:yt", "http://youtube.com/yt/2012/10/10");
        attribute("", "xsi:schemaLocation", "urn:mpeg:DASH:schema:MPD:2011 DASH-MPD.xsd");
        attribute("", "minBufferTime", "PT1.500S");
        attribute("", "profiles", "urn:mpeg:dash:profile:isoff-on-demand:2011");
        attribute("", "type", "static");
        attribute("", "mediaPresentationDuration", duration);

        startTag("", "Period");
        attribute("", "duration", duration);
    }

    private void writeEpilogue() {
        endTag("", "Period");
        endTag("", "MPD");
        endDocument();
    }

    private void writeMediaTags() {
        writeMediaTagsForGroup(mMP4Audios);
        writeMediaTagsForGroup(mMP4Videos);
        writeMediaTagsForGroup(mWEBMAudios);
        writeMediaTagsForGroup(mWEBMVideos);
    }

    private void writeMediaTagsForGroup(List<YouTubeMediaItem> items) {
        if (items.size() == 0) {
            return;
        }

        writeMediaListPrologue(String.valueOf(mId++), extractMimeType(items.get(0)));

        // Representation
        for (YouTubeMediaItem item : items) {
            writeMediaItemTag(item);
        }

        endTag("", "AdaptationSet");
    }

    private XmlSerializer attribute(String namespace, String name, String value) {
        if (value == null) {
            value = "";
        }
        try {
            return mXmlSerializer.attribute(namespace, name, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private XmlSerializer startTag(String namespace, String name) {
        try {
            return mXmlSerializer.startTag(namespace, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setOutput(XmlSerializer xmlSerializer, StringWriter writer) {
        try {
            xmlSerializer.setOutput(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startDocument(XmlSerializer xmlSerializer) {
        try {
            xmlSerializer.startDocument("UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endDocument() {
        try {
            mXmlSerializer.endDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endTag(String namespace, String name) {
        try {
            mXmlSerializer.endTag(namespace, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMediaListPrologue(String id, String mimeType) {
        startTag("", "AdaptationSet");
        attribute("", "id", id);
        attribute("", "mimeType", mimeType);
        attribute("", "subsegmentAlignment", "true");

        startTag("", "Role");
        attribute("", "schemeIdUri", "urn:mpeg:DASH:role:2011");
        attribute("", "value", "main");
        endTag("", "Role");
    }

    @Override
    public void append(YouTubeMediaItem mediaItem) {
        if (notDASH(mediaItem)) {
            return;
        }

        List<YouTubeMediaItem> placeholder = null;
        String mimeType = extractMimeType(mediaItem);
        if (mimeType != null) {
            switch (mimeType) {
                case MIME_WEBM_AUDIO:
                    placeholder = mWEBMAudios;
                    break;
                case MIME_WEBM_VIDEO:
                    placeholder = mWEBMVideos;
                    break;
                case MIME_MP4_AUDIO:
                    placeholder = mMP4Audios;
                    break;
                case MIME_MP4_VIDEO:
                    placeholder = mMP4Videos;
                    break;
            }
        }
        if (placeholder != null) {
            placeholder.add(0, mediaItem); // NOTE: reverse order
        }
    }

    private boolean notDASH(YouTubeMediaItem mediaItem) {
        return mediaItem.getInit() == null;
    }

    private String extractMimeType(YouTubeMediaItem item) {
        String codecs = extractCodecs(item);

        if (codecs.startsWith("vorbis") ||
            codecs.startsWith("opus")) {
            return MIME_WEBM_AUDIO;
        }

        if (codecs.startsWith("vp9")) {
            return MIME_WEBM_VIDEO;
        }

        if (codecs.startsWith("mp4a")) {
            return MIME_MP4_AUDIO;
        }

        if (codecs.startsWith("avc")) {
            return MIME_MP4_VIDEO;
        }

        return null;
    }

    private void writeMediaItemTag(YouTubeMediaItem item) {
        startTag("", "Representation");

        attribute("", "id", item.getITag());
        attribute("", "codecs", extractCodecs(item));
        attribute("", "startWithSAP", "1");
        attribute("", "bandwidth", item.getBitrate());

        if (isVideo(item)) {
            // video attrs
            attribute("", "width", getWidth(item));
            attribute("", "height", getHeight(item));
            attribute("", "maxPlayoutRate", "1");
            attribute("", "frameRate", item.getFps());
        } else {
            // audio attrs
            attribute("", "audioSamplingRate", ITag.getAudioRateByTag(item.getITag()));
        }

        startTag("", "BaseURL");

        attribute("", "yt:contentLength", item.getClen());
        text(item.getUrl());

        endTag("", "BaseURL");

        startTag("", "SegmentBase");

        if (item.getIndex() != null) {
            attribute("", "indexRange", item.getIndex());
            attribute("", "indexRangeExact", "true");
        }

        startTag("", "Initialization");

        attribute("", "range", item.getInit());

        endTag("", "Initialization");

        endTag("", "SegmentBase");

        endTag("", "Representation");
    }

    private boolean isVideo(YouTubeMediaItem item) {
        return item.getSize() != null;
    }

    private XmlSerializer text(String url) {
        try {
            return mXmlSerializer.text(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHeight(YouTubeMediaItem item) {
        String size = item.getSize();
        if (size == null) {
            return "";
        }
        return size.split("x")[1];
    }

    private String getWidth(YouTubeMediaItem item) {
        String size = item.getSize();
        if (size == null) {
            return "";
        }
        return size.split("x")[0];
    }

    private String extractCodecs(YouTubeMediaItem item) {
        // input example: video/mp4;+codecs="avc1.640033"
        Pattern pattern = Pattern.compile(".*codecs=\\\"(.*)\\\"");
        Matcher matcher = pattern.matcher(item.getType());
        matcher.find();
        return matcher.group(1);
    }

    @Override
    public InputStream build() {
        writePrologue();

        writeMediaTags();

        writeEpilogue();

        return Helpers.toStream(mWriter.toString());
    }


}
