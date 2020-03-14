package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtfSegmentParser {
    private static final String TAG = OtfSegmentParser.class.getSimpleName();
    private List<OtfSegment> mCachedSegments;
    private boolean mAlreadyParsed;

    public List<OtfSegment> parseCached(String url) {
        if (mCachedSegments == null && !mAlreadyParsed) {
            mCachedSegments = parse(url);
            mAlreadyParsed = true;
        }

        return mCachedSegments;
    }

    public List<OtfSegment> parseCached(Reader stream) {
        if (mCachedSegments == null && !mAlreadyParsed) {
            mCachedSegments = parse(stream);
            mAlreadyParsed = true;
        }

        return mCachedSegments;
    }

    public List<OtfSegment> parse(String url) {
        List<OtfSegment> result = null;

        if (url != null) {
            Response response = OkHttpHelpers.doGetOkHttpRequest(url);

            if (response != null && response.body() != null) {
                result = parse(response.body().charStream());
            } else {
                Log.e(TAG, "Can't parse url " + url + ". Response is empty.");
            }
        } else {
            Log.e(TAG, "Can't parse url. Url is empty.");
        }

        return result;
    }

    public List<OtfSegment> parse(Reader stream) {
        List<OtfSegment> result = null;

        BufferedReader bufferedReader = new BufferedReader(stream);

        Pattern segmentPattern = Pattern.compile("Segment-Durations-Ms: (.*)");

        try {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                Matcher segmentPatternMatcher = segmentPattern.matcher(currentLine);
                if (segmentPatternMatcher.matches()) {
                    result = splitToSegments(segmentPatternMatcher.group(1));
                    break;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * RegEx test: https://www.freeformatter.com/java-regex-tester.html#ad-output
     */
    private List<OtfSegment> splitToSegments(String rawString) {
        // Segment-Durations-Ms: 5120(r=192),5700,

        ArrayList<OtfSegment> list = new ArrayList<>();

        if (rawString != null) {
            String[] split = rawString.split(",");
            // Sample to match: '5120(r=192)' or '5120'
            Pattern segmentPattern = Pattern.compile("\\d+");

            for (String item : split) {
                Matcher segmentPatternMatcher = segmentPattern.matcher(item);

                int findNum = 0;
                OtfSegment segment = new OtfSegment();

                // example pattern: 5120(r=192)
                while (segmentPatternMatcher.find()) {
                    findNum++;
                    if (findNum == 1) { // 5120
                        segment.setDuration(segmentPatternMatcher.group(0));
                        list.add(segment); // at least duration should be present
                    } else if (findNum == 2) { // 192
                        segment.setRepeatCount(segmentPatternMatcher.group(0));
                    } else {
                        break;
                    }
                }
            }
        }

        return list;
    }

    public static class OtfSegment {
        private String mDuration;
        private String mRepeatCount = "0";

        public String getDuration() {
            return mDuration;
        }

        public String getRepeatCount() {
            return mRepeatCount;
        }

        public void setDuration(String duration) {
            mDuration = duration;
        }

        public void setRepeatCount(String repeatCount) {
            mRepeatCount = repeatCount;
        }
    }
}
