package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import android.media.browse.MediaBrowser.MediaItem;
import android.util.Xml;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.ITag;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
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
    private Set<YouTubeMediaItem> mMP4Audios;
    private Set<YouTubeMediaItem> mMP4Videos;
    private Set<YouTubeMediaItem> mWEBMAudios;
    private Set<YouTubeMediaItem> mWEBMVideos;
    private int mCounter;

    private class MyComparator implements Comparator<YouTubeMediaItem> {
        @Override
        public int compare(YouTubeMediaItem leftItem, YouTubeMediaItem rightItem) {
            if (leftItem.getSize() == null || rightItem.getSize() == null) {
                return 0;
            }
            if (leftItem.getBitrate() == null || rightItem.getBitrate() == null) {
                return 0;
            }

            int leftItemBitrate = Integer.parseInt(leftItem.getBitrate());
            int rightItemBitrate = Integer.parseInt(rightItem.getBitrate());

            int leftItemHeight = Integer.parseInt(getHeight(leftItem));
            int rightItemHeight = Integer.parseInt(getHeight(rightItem));

            int delta = leftItemHeight - rightItemHeight;
            if (delta == 0) {
                delta = leftItemBitrate - rightItemBitrate;
            }
            return delta;
        }
    }

    public MyMPDBuilder(YouTubeGenericInfo info) {
        mInfo = info;
        MyComparator comp = new MyComparator();
        mMP4Audios = new TreeSet<>(comp);
        mMP4Videos = new TreeSet<>(comp);
        mWEBMAudios = new TreeSet<>(comp);
        mWEBMVideos = new TreeSet<>(comp);

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
        String durationParam = null;
        String duration = null;
        // MPD file is not valid without duration
        if (mInfo != null && mInfo.getLengthSeconds() != null) {
            duration = mInfo.getLengthSeconds();
        } else { // try to use some work around
            duration = extractDurationFromTrack();
        }

        if (duration != null) {
            durationParam = String.format("PT%sS", duration);
        }

        startTag("", "MPD");
        attribute("", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        attribute("", "xmlns", "urn:mpeg:DASH:schema:MPD:2011");
        attribute("", "xmlns:yt", "http://youtube.com/yt/2012/10/10");
        attribute("", "xsi:schemaLocation", "urn:mpeg:DASH:schema:MPD:2011 DASH-MPD.xsd");
        attribute("", "minBufferTime", "PT1.500S");
        attribute("", "profiles", "urn:mpeg:dash:profile:isoff-on-demand:2011");
        attribute("", "type", "static");
        attribute("", "mediaPresentationDuration", durationParam);

        startTag("", "Period");
        attribute("", "duration", durationParam);
    }

    private String extractDurationFromTrack() {
        String url = null;
        for (YouTubeMediaItem item : mMP4Videos) {
            url = item.getUrl();
            break; // get first item
        }
        String res = Helpers.runMultiMatcher(url, "dur=(\\w*)");
        if (res == null) {
            throw new IllegalStateException("Video doesn't have a duration. Size of the video list: " + mMP4Videos.size());
        }
        return res;
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

    private void writeMediaTagsForGroup(Set<YouTubeMediaItem> items) {
        if (items.size() == 0) {
            return;
        }

        YouTubeMediaItem firstItem = null;
        for (YouTubeMediaItem item : items) {
            firstItem = item;
            break;
        }
        writeMediaListPrologue(String.valueOf(mId++), extractMimeType(firstItem));

        // Representation
        for (YouTubeMediaItem item : items) {
            writeMediaItemTag(item);
        }

        endTag("", "AdaptationSet");
    }

    private XmlSerializer attribute(String namespace, String name, String value) {
        if (value == null) {
            return mXmlSerializer;
        }
        try {
            return mXmlSerializer.attribute(namespace, name, value);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private XmlSerializer startTag(String namespace, String name) {
        try {
            return mXmlSerializer.startTag(namespace, name);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void setOutput(XmlSerializer xmlSerializer, StringWriter writer) {
        try {
            xmlSerializer.setOutput(writer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void startDocument(XmlSerializer xmlSerializer) {
        try {
            xmlSerializer.startDocument("UTF-8", true);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void endDocument() {
        try {
            mXmlSerializer.endDocument();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void endTag(String namespace, String name) {
        try {
            mXmlSerializer.endTag(namespace, name);
        } catch (IOException e) {
            throw new IllegalStateException(e);
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

        Set<YouTubeMediaItem> placeholder = null;
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
            placeholder.add(mediaItem); // NOTE: reverse order
        }

        mCounter++;
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
            throw new IllegalStateException(e);
        }
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

    @Override
    public boolean isEmpty() {
        return mCounter == 0;
    }

}
