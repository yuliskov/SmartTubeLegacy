package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import android.util.Xml;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.SimpleYouTubeMediaItem;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyMPDParser implements MPDParser {
    // We don't use namespaces
    private static final String ns = null;
    private static final String TAG_MPD = "MPD";
    private static final String TAG_MEDIA_ITEM = "Representation";
    private static final String TAG_BASE_URL = "BaseURL";
    private static final String TAG_MEDIA_GROUP = "AdaptationSet";
    private static final String TAG_SEGMENT_BASE = "SegmentBase";
    private static final String TAG_INITIALIZATION = "Initialization";
    private InputStream mMpdContent;
    private XmlPullParser mParser;

    public MyMPDParser(InputStream mpdContent) {
        try {
            initParser(mpdContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initParser(InputStream mpdContent) throws XmlPullParserException, IOException {
        mMpdContent = mpdContent;
        mParser = Xml.newPullParser();
        mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        mParser.setInput(mpdContent, null);
        mParser.nextTag();
    }

    @Override
    public List<YouTubeMediaItem> parse() {
        List<YouTubeMediaItem> result = null;
        try {
            result = readDashMPD();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<YouTubeMediaItem> readDashMPD() throws IOException, XmlPullParserException {
        List<YouTubeMediaItem> mediaItems = new ArrayList<>();

        mParser.require(XmlPullParser.START_TAG, ns, TAG_MPD);
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = mParser.getName();
            // Starts by looking for the entry tag
            if (name.equals(TAG_MEDIA_GROUP)) {
                mediaItems.addAll(readMediaGroup(mParser));
            } else {
                skip(mParser);
            }
        }
        return mediaItems;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private List<YouTubeMediaItem> readMediaGroup(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_MEDIA_GROUP);
        List<YouTubeMediaItem> mediaItems = new ArrayList<>();
        String mimeType = parser.getAttributeValue(ns, "mimeType");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_MEDIA_ITEM)) {
                mediaItems.add(readMediaItem(parser, mimeType));
            } else {
                skip(parser);
            }
        }
        return mediaItems;
    }

    private YouTubeMediaItem readMediaItem(XmlPullParser parser, String mimeType) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_MEDIA_ITEM);
        YouTubeMediaItem item = new SimpleYouTubeMediaItem();
        item.setITag(parser.getAttributeValue(ns, "id"));
        item.setBitrate(parser.getAttributeValue(ns, "bandwidth"));
        String size = String.format("%sx%s", parser.getAttributeValue(ns, "width"), parser.getAttributeValue(ns, "height"));
        item.setSize(size);
        item.setFps(parser.getAttributeValue(ns, "frameRate"));
        item.setType(String.format("%s;+codecs=\"%s\"", mimeType, parser.getAttributeValue(ns, "codecs")));
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_BASE_URL)) {
                readUrl(parser, item);
            } else if (name.equals(TAG_SEGMENT_BASE)) {
                readSegmentBase(parser, item);
            } else {
                skip(parser);
            }
        }
        return item;
    }

    private void readSegmentBase(XmlPullParser parser, YouTubeMediaItem item) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_SEGMENT_BASE);
        item.setIndex(parser.getAttributeValue(ns, "indexRange"));
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_INITIALIZATION)) {
                readInitialization(parser, item);
            } else {
                skip(parser);
            }
        }
    }

    private void readInitialization(XmlPullParser parser, YouTubeMediaItem item) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_INITIALIZATION);
        item.setInit(parser.getAttributeValue(ns, "range"));
        parser.require(XmlPullParser.END_TAG, ns, TAG_INITIALIZATION);
    }

    private void readUrl(XmlPullParser parser, YouTubeMediaItem item) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_BASE_URL);
        String url = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG_BASE_URL);
        item.setUrl(url);
        item.setClen(parser.getAttributeValue(ns, "yt:contentLength"));
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
