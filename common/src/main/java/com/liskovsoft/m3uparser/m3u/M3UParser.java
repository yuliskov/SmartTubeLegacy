package com.liskovsoft.m3uparser.m3u;

//import com.liskovsoft.m3uparser.core.uri.Uri;
import android.net.Uri;
import com.liskovsoft.m3uparser.core.utils.IOUtils;
import com.liskovsoft.m3uparser.core.utils.Strings;
import com.liskovsoft.m3uparser.m3u.models.*;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

//https://pypkg.com/pypi/pytoutv/src/toutv/m3u8.py
//https://developer.apple.com/library/content/technotes/tn2288/_index.html#//apple_ref/doc/uid/DTS40012238-CH1-ALTERNATE_MEDIA

public class M3UParser {
    private static final String TAG = M3UParser.class.getSimpleName();

    public Playlist parse(Uri uri) {
        try {
            if (uri.getScheme().startsWith("http")) {
                InputStream is = null;
                try {
                    URL url = new URL(uri.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    // optional default is GET
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        is = con.getInputStream();
                        return parse(is);
                    }
                } finally {
                    IOUtils.closeQuietly(is);
                }
            }

            else {
                InputStream is = null;
                try {
                    is = new FileInputStream(new File(uri.toString()));
                    return parse(is);

                } finally {
                    IOUtils.closeQuietly(is);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public Playlist parse(final InputStream inputStream) throws IOException {
        String[] lines = IOUtils.toStringArray(inputStream);
        if (lines.length <= 1) {
            throw new IllegalArgumentException("input stream is empty");
        }

        Playlist playlist = new Playlist();

        Key currentKey = null;

        //if (!M3U.SIGNATURE.equals(lines[0]))
        //    throw new IllegalArgumentException("Invalid M3U8 file");

        for (int i = 0; i < lines.length; i++) {
            if (!isTagLine(lines[i]))
                continue;

            List<String> attributes = getAttributes(lines[i]);

            switch (getTag(lines[i])) {
                case EXT_X_TARGETDURATION: {
                    double duration = getAttributeValueAsDouble(attributes, 0, -1);
                    playlist.setTargetDuration(duration);
                    break;
                }

                case EXT_X_MEDIA_SEQUENCE: {
                    int sequence = getAttributeValueAsInt(attributes, 0, -1);
                    playlist.setMediaSequence(sequence);
                    break;
                }

                case EXT_X_KEY: {
                    currentKey = new Key();
                    currentKey.setMethod(getAttributeValue(attributes, "METHOD"));
                    currentKey.setIV(getAttributeValue(attributes, "IV"));
                    currentKey.setUri(Uri.parse(getAttributeValue(attributes, "URI")));
                    break;
                }

                case EXT_X_ALLOW_CACHE:{
                    String value = attributes.size() > 0 ? attributes.get(0) : null;
                    boolean allowCache = (!Strings.isNullOrEmpty(value) && value.equalsIgnoreCase("YES"));
                    playlist.setAllowCache(allowCache);
                    break;
                }

                case EXT_X_PLAYLIST_TYPE:{
                    String playListType = attributes.size() > 0 ? attributes.get(0) : null;
                    playlist.setPlayListType(playListType);
                    break;
                }

                case EXT_X_MEDIA: {
                    playlist.getMedia().add(
                            new Media.Builder(getAttributeValue(attributes, "TYPE"))
                                    .withUri(getAttributeValue(attributes, "URI"))
                                    .withGroupId(getAttributeValue(attributes, "GROUP-ID"))
                                    .build());
                    break;
                }

                case EXT_X_STREAM_INF: {
                    String bandWidth = getAttributeValue(attributes, "BANDWIDTH");
                    playlist.getStreams().add(
                            new Stream.Builder(Integer.valueOf(bandWidth))
                                .withUri(Uri.parse(lines[i+1]))
                                .withProgramId(getAttributeValue(attributes, "PROGRAM-ID"))
                                .withResolution(getAttributeValue(attributes, "RESOLUTION"))
                                .withCodecs(getAttributeValue(attributes, "CODECS").split(","))
                                .withType(getAttributeValue(attributes, "TYPE"))
                                .build());
                    break;
                }

                case EXT_X_VERSION: {
                    playlist.setVersion(getAttribute(attributes, 0));
                    break;
                }

                case EXTINF: {
                    Segment segment = new Segment();
                    segment.setDuration(getAttributeValueAsDouble(attributes, 0, -1));
                    segment.setTitle(getAttributeValueAsString(attributes, 1, ""));
                    segment.setUri(Uri.parse(lines[i + 1]));
                    segment.setKey(currentKey);

                    playlist.getSegments().add(segment);
                    break;
                }
                default:
                    break;
            }
        }

        return playlist;
    }

    /**
     * If the line contains a tag return the name without the '#'
     * @param line
     * @return the tag name without the '#'
     */
    private M3U.Tag getTag(final String line) {
        int index = (!Strings.isNullOrEmpty(line) && (line.length() > 1)) ?
                line.indexOf(':') : -1;
        String tagName = (index != -1) ? line.substring(1, index) : "";
        return M3U.Tag.fromTagName(tagName);
    }

    private List<String> getAttributes(final String line) {
        List<String> attributes = new LinkedList<>();
        if (!Strings.isNullOrEmpty(line)) {

            int index = line.indexOf(':');
            if (index != -1) {
                String[] pairs = line.substring(index + 1).split(",");
                for (String pair : pairs) {
                    attributes.add(pair.trim());
                }
            }
        }

        return attributes;
    }

    private boolean isTagLine(final String line) {
        return (!Strings.isNullOrEmpty(line) && (line.length() > 3)) &&
                line.substring(0, 4).equals(M3U.EXT_PREFIX);
    }


    private int getAttributeValueAsInt(final List<String> attributes,
                                       final int position,
                                       final int defaultValue) {
        String attribute = null;
        try {
            attribute = getAttribute(attributes, position);
            return Strings.isNullOrEmpty(attribute) ? defaultValue : Integer.valueOf(attribute);
        } catch (Exception err) {
            throw new IllegalArgumentException("while coercing int value for attribute: " + attribute, err);
        }
    }

    private double getAttributeValueAsDouble(final List<String> attributes,
                                          final int position,
                                          final double defaultValue) {
        String attribute = null;
        try {
            attribute = getAttribute(attributes, position);
            return Strings.isNullOrEmpty(attribute) ? defaultValue : Double.valueOf(attribute);
        } catch (Exception err) {
            throw new IllegalArgumentException("while coercing int value for attribute: " + attribute, err);
        }
    }

    private String getAttributeValueAsString(final List<String> attributes,
                                             final int position,
                                             final String defaultValue) {
        String attribute = null;
        try {
            attribute = getAttribute(attributes, position);
            return Strings.isNullOrEmpty(attribute) ? defaultValue : attribute;
        } catch (Exception err) {
            throw new IllegalArgumentException("while coercing string value for attribute: " + attribute, err);
        }
    }

    private String getAttributeValue(final List<String> attributes, final String name) {
        if ((attributes == null) || Strings.isNullOrEmpty(name))
            return null;

        for (String attribute: attributes) {
            int index = attribute.indexOf('=');
            if (index != -1) {
                String key = attribute.substring(0, index).trim();
                String val = attribute.substring(index + 1).trim();
                if (key.equalsIgnoreCase(name)) {
                    return Strings.strip(val, "\"");
                }
            }
        }

        return "";
    }

    private String getAttribute(final List<String> attributes, final int position) {
        return  ((attributes != null) && (attributes.size() > position)) ?
                attributes.get(position) : null;
    }

}
