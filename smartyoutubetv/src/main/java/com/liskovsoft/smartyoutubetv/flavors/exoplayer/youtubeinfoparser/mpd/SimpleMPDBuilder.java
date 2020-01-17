package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import android.util.Xml;
import com.liskovsoft.sharedutils.helpers.FileHelpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.ITag;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.Subtitle;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.YouTubeMediaParser.GenericInfo;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.JsonInfoParser.MediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.MediaItemComparator;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.MediaItemUtils;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc.SimpleYouTubeGenericInfo;
import com.liskovsoft.sharedutils.helpers.Helpers;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
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
    private static final String NULL_INDEX_RANGE = "0-0";
    private static final String NULL_CONTENT_LENGTH = "0";
    private static final String TAG = SimpleMPDBuilder.class.getSimpleName();
    private final GenericInfo mInfo;
    private final boolean mVLCFix;
    private XmlSerializer mXmlSerializer;
    private StringWriter mWriter;
    private int mId;
    private Set<MediaItem> mMP4Audios;
    private Set<MediaItem> mMP4Videos;
    private Set<MediaItem> mWEBMAudios;
    private Set<MediaItem> mWEBMVideos;
    private List<Subtitle> mSubs;

    public SimpleMPDBuilder() {
        this(new SimpleYouTubeGenericInfo());
    }

    public SimpleMPDBuilder(GenericInfo info) {
        this(info, false);
    }

    public SimpleMPDBuilder(GenericInfo info, boolean VLCFix) {
        mVLCFix = VLCFix;

        mInfo = info;
        MediaItemComparator comp = new MediaItemComparator();
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

        if (isLive()) {
            attribute("", "profiles", "urn:mpeg:dash:profile:isoff-main:2011");
            attribute("", "type", "dynamic");

            attribute("", "minBufferTime", "PT1.500S");
            attribute("", "timeShiftBufferDepth", "PT14400.000S");
            attribute("", "minimumUpdatePeriod", "PT5.000S");
            // availabilityStartTime="2019-01-06T17:04:49"
        } else {
            attribute("", "profiles", "urn:mpeg:dash:profile:isoff-on-demand:2011");
            attribute("", "type", "static");
            attribute("", "mediaPresentationDuration", durationParam);
        }


        startTag("", "Period");

        if (isLive()) {
            // yt:segmentIngestTime="2019-01-06T17:55:24.836"
            attribute("", "start", "PT3050.000S");
        } else {
            attribute("", "duration", durationParam);
        }
    }

    private void writeEpilogue() {
        endTag("", "Period");
        endTag("", "MPD");
        endDocument();
    }

    private void writeMediaTags() {
        if (isLive()) {
            writeLiveHeaderSegmentList();
        }

        if (mVLCFix) {
            // VLC can't play VP9
            writeMediaTagsForGroup(mMP4Videos);
            writeMediaTagsForGroup(mMP4Audios);
            writeMediaTagsForGroup(mWEBMVideos);
            writeMediaTagsForGroup(mWEBMAudios);
            writeMediaTagsForGroup(mSubs);
        } else {
            // MXPlayer fix: write high quality formats first
            writeMediaTagsForGroup(mWEBMVideos);
            writeMediaTagsForGroup(mWEBMAudios);
            writeMediaTagsForGroup(mMP4Videos);
            writeMediaTagsForGroup(mMP4Audios);
            writeMediaTagsForGroup(mSubs);
        }
    }

    private void writeLiveHeaderSegmentList() {
        startTag("", "SegmentList");

        attribute("", "presentationTimeOffset", "3050000");
        attribute("", "startNumber", "610");
        attribute("", "timescale", "1000");

        startTag("", "SegmentTimeline");

        for (int i = 0; i < 3; i++) {
            startTag("", "S");

            attribute("", "d", "5000");

            endTag("", "S");
        }

        endTag("", "SegmentTimeline");

        endTag("", "SegmentList");
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

            // VLC fix: limit to max quality
            if (mVLCFix) {
                break;
            }
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
        attribute("", "lang", sub.getName() == null ? sub.getLanguageCode() : sub.getName());

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
        // NOTE: FORMAT_STREAM_TYPE_OTF not supported
        if (!MediaItemUtils.isDash(mediaItem) || mediaItem.isOTF()) {
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
            attribute("", "width", MediaItemUtils.getWidth(item));
            attribute("", "height", MediaItemUtils.getHeight(item));
            attribute("", "maxPlayoutRate", "1");
            attribute("", "frameRate", item.getFps());
        } else {
            // audio attrs
            attribute("", "audioSamplingRate", ITag.getAudioRateByTag(item.getITag()));
        }

        startTag("", "BaseURL");

        if (item.getClen() != null && !item.getClen().equals(NULL_CONTENT_LENGTH)) {
            attribute("", "yt:contentLength", item.getClen());
        }

        text(item.getUrl());

        endTag("", "BaseURL");

        // SegmentList tag
        if (isLive()) {
            writeLiveMediaSegmentList();
        } else if (item.getSegmentUrlList() != null) {
            writeSegmentList(item);
        } else if (item.getIndex() != null &&
                   !item.getIndex().equals(NULL_INDEX_RANGE)) { // json format fix: index is null
            writeSegmentBase(item);
        }

        endTag("", "Representation");
    }

    private void writeSegmentBase(MediaItem item) {
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

    private void writeSegmentList(MediaItem item) {
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
    }

    private void writeLiveMediaSegmentList() {
        startTag("", "SegmentList");

        for (String mediaDesc : new String[]{
                "sq/610/lmt/1546797364563137",
                "sq/611/lmt/1546797365000899",
                "sq/612/lmt/1546797369574434"}) {
            startTag("", "SegmentURL");

            attribute("", "media", mediaDesc);

            endTag("", "SegmentURL");
        }

        endTag("", "SegmentList");
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
    private boolean ensureRequiredFieldsAreSet() {
        return ensureLengthIsSet();
    }

    /**
     * MPD file is not valid without duration
     */
    private boolean ensureLengthIsSet() {
        if (mInfo == null) {
            //throw new IllegalStateException("GenericInfo not initialized");
            Log.e(TAG, "GenericInfo not initialized");
            return false;
        }

        if (mInfo.getLengthSeconds() != null) {
            return true;
        }

        // try to get duration from video url
        String len = extractDurationFromTrack();

        if (len == null) {
            //throw new IllegalStateException("Videos in the list doesn't have a duration. Content: " + mMP4Videos);
            Log.e(TAG, "Videos in the list doesn't have a duration. Content: " + mMP4Videos);
            return false;
        }

        mInfo.setLengthSeconds(len);
        return true;
    }

    @Override
    public InputStream build() {
        if (ensureRequiredFieldsAreSet()) {
            writePrologue();

            writeMediaTags();

            writeEpilogue();

            return FileHelpers.toStream(mWriter.toString());
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return (mMP4Videos.size() == 0 && mWEBMVideos.size() == 0
                && mMP4Audios.size() == 0 && mWEBMAudios.size() == 0) || !ensureRequiredFieldsAreSet();
    }

    private boolean isLive() {
        for (MediaItem item : mMP4Videos) {
            return isLiveMedia(item);
        }

        for (MediaItem item : mWEBMVideos) {
            return isLiveMedia(item);
        }

        return false;
    }

    private boolean isLiveMedia(MediaItem item) {
        boolean isLive =
                item.getUrl().contains("live=1") ||
                item.getUrl().contains("yt_live_broadcast");

        //if (isLive) {
        //    throw new IllegalStateException("Live item shouldn't be there");
        //}

        return isLive;
    }
}
