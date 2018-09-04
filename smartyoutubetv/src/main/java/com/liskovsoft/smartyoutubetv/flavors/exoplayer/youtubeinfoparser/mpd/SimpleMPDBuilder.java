package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import android.util.Xml;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.ITag;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeSubParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.toplevel.YouTubeMediaParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.smartyoutubetv.common.helpers.Helpers;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demos: https://github.com/Dash-Industry-Forum/dash-live-source-simulator/wiki/Test-URLs
 */
public class SimpleMPDBuilder implements MPDBuilder {
    private static final String MIME_WEBM_AUDIO = "audio/webm";
    private static final String MIME_WEBM_VIDEO = "video/webm";
    private static final String MIME_MP4_AUDIO = "audio/mp4";
    private static final String MIME_MP4_VIDEO = "video/mp4";
    private final GenericInfo mInfo;
    private XmlSerializer mXmlSerializer;
    private StringWriter mWriter;
    private int mId;
    private Set<MediaItem> mMP4Audios;
    private Set<MediaItem> mMP4Videos;
    private Set<MediaItem> mWEBMAudios;
    private Set<MediaItem> mWEBMVideos;
    private List<Subtitle> mSubs;

    private class SimpleComparator implements Comparator<MediaItem> {
        @Override
        public int compare(MediaItem leftItem, MediaItem rightItem) {
            if (leftItem.getGlobalSegmentList() != null ||
                rightItem.getGlobalSegmentList() != null) {
                return 1;
            }

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

    public SimpleMPDBuilder() {
        this(new SimpleYouTubeGenericInfo());
    }

    public SimpleMPDBuilder(GenericInfo info) {
        mInfo = info;
        SimpleComparator comp = new SimpleComparator();
        mMP4Audios = new TreeSet<>(comp);
        mMP4Videos = new TreeSet<>(comp);
        mWEBMAudios = new TreeSet<>(comp);
        mWEBMVideos = new TreeSet<>(comp);
        mSubs = new ArrayList<>();

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
        String duration = mInfo.getLengthSeconds();
        String durationParam = String.format("PT%sS", duration);

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
        writeMediaTagsForGroup(mSubs);
    }

    private void writeMediaTagsForGroup(List<Subtitle> subs) {
        if (subs.size() == 0) {
            return;
        }

        for (Subtitle sub : subs) {
            writeMediaListPrologue(sub);

            writeMediaItemTag(sub);

            writeMediaListEpilogue();
        }
    }

    private void writeMediaTagsForGroup(Set<MediaItem> items) {
        if (items.size() == 0) {
            return;
        }

        MediaItem firstItem = null;
        for (MediaItem item : items) {
            firstItem = item;
            break;
        }
        writeMediaListPrologue(String.valueOf(mId++), extractMimeType(firstItem));

        // Representation
        for (MediaItem item : items) {
            if (item.getGlobalSegmentList() != null) {
                writeGlobalSegmentList(item);
                continue;
            }
            writeMediaItemTag(item);
        }

        writeMediaListEpilogue();
    }

    private void writeGlobalSegmentList(MediaItem item) {
        startTag("", "SegmentList");

        attribute("", "startNumber", "0");
        attribute("", "timescale", "1000");

        startTag("", "SegmentTimeline");

        // SegmentURL tag
        for (String segment : item.getGlobalSegmentList()) {
            startTag("", "S");
            attribute("", "d", segment);
            endTag("", "S");
        }

        endTag("", "SegmentTimeline");

        endTag("", "SegmentList");
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
    
    private void writeMediaListPrologue(Subtitle sub) {
        String id = String.valueOf(mId++);

        startTag("", "AdaptationSet");
        attribute("", "id", id);
        attribute("", "mimeType", sub.getMimeType());
        attribute("", "lang", sub.getLanguageCode());

        startTag("", "Role");
        attribute("", "schemeIdUri", "urn:mpeg:DASH:role:2011");
        attribute("", "value", "subtitle");
        endTag("", "Role");
    }

    private void writeMediaListEpilogue() {
        endTag("", "AdaptationSet");
    }

    @Override
    public void append(MediaItem mediaItem) {
        if (notDASH(mediaItem)) {
            return;
        }

        Set<MediaItem> placeholder = null;
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
    }

    @Override
    public void append(List<Subtitle> subs) {
        mSubs.addAll(subs);
    }

    @Override
    public void append(Subtitle sub) {
        mSubs.add(sub);
    }

    private boolean notDASH(MediaItem mediaItem) {
        if (mediaItem.getGlobalSegmentList() != null) {
            return false;
        }

        int maxNoDashITag = 50;
        return mediaItem.getITag() == null || Integer.parseInt(mediaItem.getITag()) < maxNoDashITag;
    }

    private String extractMimeType(MediaItem item) {
        if (item.getGlobalSegmentList() != null) {
            return item.getType();
        }

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

    private void writeMediaItemTag(MediaItem item) {
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

        // SegmentList tag
        if (item.getSegmentUrlList() != null) {
            startTag("", "SegmentList");

            // Initialization tag
            if (item.getSourceURL() != null) {
                startTag("", "Initialization");
                attribute("", "sourceURL", item.getSourceURL());
                endTag("", "Initialization");
            }

            // SegmentURL tag
            for (String url : item.getSegmentUrlList()) {
                startTag("", "SegmentURL");
                attribute("", "media", url);
                endTag("", "SegmentURL");
            }

            endTag("", "SegmentList");
        } else {
            // SegmentBase
            startTag("", "SegmentBase");

            if (item.getIndex() != null) {
                attribute("", "indexRange", item.getIndex());
                attribute("", "indexRangeExact", "true");
            }

            startTag("", "Initialization");

            attribute("", "range", item.getInit());

            endTag("", "Initialization");

            endTag("", "SegmentBase");
        }

        endTag("", "Representation");
    }

    private void writeMediaItemTag(Subtitle sub) {
        String bandwidth = "268";

        startTag("", "Representation");

        attribute("", "id", String.valueOf(mId));

        attribute("", "bandwidth", bandwidth);

        attribute("", "codecs", sub.getCodecs());

        startTag("", "BaseURL");

        text(sub.getBaseUrl());

        endTag("", "BaseURL");

        endTag("", "Representation");
    }

    private boolean isVideo(MediaItem item) {
        return item.getSize() != null;
    }

    private XmlSerializer text(String url) {
        try {
            return mXmlSerializer.text(url);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getHeight(MediaItem item) {
        String size = item.getSize();
        if (size == null) {
            return "";
        }
        return size.split("x")[1];
    }

    private String getWidth(MediaItem item) {
        String size = item.getSize();
        if (size == null) {
            return "";
        }
        return size.split("x")[0];
    }

    private String extractCodecs(MediaItem item) {
        // input example: video/mp4;+codecs="avc1.640033"
        Pattern pattern = Pattern.compile(".*codecs=\\\"(.*)\\\"");
        Matcher matcher = pattern.matcher(item.getType());
        matcher.find();
        return matcher.group(1);
    }

    /**
     * Extracts time from video url (if present).
     * Url examples:
     * <br/>
     * "http://example.com?dur=544.99&key=val&key2=val2"
     * <br/>
     * "http://example.com/dur/544.99/key/val/key2/val2"
     * @return duration as string
     */
    private String extractDurationFromTrack() {
        String url = null;
        for (MediaItem item : mMP4Videos) {
            url = item.getUrl();
            break; // get first item
        }
        String res = Helpers.runMultiMatcher(url, "dur=([^&]*)", "/dur/([^/]*)");
        return res;
    }

    /**
     * Ensures that required fields are set. If no, initialize them or throw an exception.
     * <br/>
     * Required fields are:
     * <br/>
     * {@link GenericInfo#getLengthSeconds() GenericInfo#getLengthSeconds()}
     */
    private void ensureRequiredFieldsAreSet() {
        ensureLengthIsSet();
    }

    /**
     * MPD file is not valid without duration
     */
    private void ensureLengthIsSet() {
        if (mInfo == null) {
            throw new IllegalStateException("GenericInfo not initialized");
        }

        if (mInfo.getLengthSeconds() != null) {
            return;
        }

        // try to get duration from video url
        String len = extractDurationFromTrack();

        if (len == null) {
            throw new IllegalStateException("Video doesn't have a duration. Size of the video list: " + mMP4Videos.size());
        }

        mInfo.setLengthSeconds(len);
    }

    @Override
    public InputStream build() {
        ensureRequiredFieldsAreSet();

        writePrologue();

        writeMediaTags();

        writeEpilogue();

        return Helpers.toStream(mWriter.toString());
    }

    @Override
    public boolean isEmpty() {
        return mMP4Videos.size() == 0 && mWEBMVideos.size() == 0
                && mMP4Audios.size() == 0 && mWEBMAudios.size() == 0;
    }

}
