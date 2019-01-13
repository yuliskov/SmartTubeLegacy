package com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser;

import android.net.Uri;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 Get direct link to YouTube video (hd720, itag%3D22) from 'get_video_info' file:
 1. Get 'url_encoded_fmt_stream_map' param contents
 2. Split by %2C (,)
 3. Get first part
 4. Do decode
 5. Get contents of 'url' param
 6. Do decode
 */
public class YouTubeVideoInfoParser implements VideoInfoParser {
    private final MyUrlParser mMyUrlParser;

    private final String mVideoInfo;

    private final String mStreamMap;
    private static final String STREAM_MAP_DELIMETER = "%2C";

    private class MyUrlParser {
        private final String mUrl;

        public MyUrlParser(String url) {
            mUrl = url;
        }

        public String getParam(String param) {
            Pattern p = Pattern.compile(String.format("%s=([^&]+)", param));
            Matcher m = p.matcher(mUrl);
            String result = null;
            if (m.find())
                result = m.group(1);
            return result;
        }
    }

    public YouTubeVideoInfoParser(InputStream videoInfo) {
        Scanner s = new Scanner(videoInfo).useDelimiter("\\A");
        mVideoInfo = s.hasNext() ? s.next() : "";

        mMyUrlParser = new MyUrlParser(mVideoInfo);
        mStreamMap = mMyUrlParser.getParam("url_encoded_fmt_stream_map");
    }

    @Override
    public String getHDVideoLink() {
        return getLinkByIndex(0);
    }

    private String getLinkByIndex(int idx) {
        String[] result = splitStreamMap();
        String decodedPart = Uri.decode(result[idx]);
        String encodedUrl = new MyUrlParser(decodedPart).getParam("url");
        return Uri.decode(encodedUrl);
    }

    private String[] splitStreamMap() {
        return mStreamMap.split(STREAM_MAP_DELIMETER);
    }
}
