package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.sharedutils.okhttp.OkHttpHelpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class OtfSegmentParser {
    private static final String TAG = OtfSegmentParser.class.getSimpleName();
    private static final Pattern SEGMENT_PATTERN = Pattern.compile("Segment-Durations-Ms: (.*)");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");
    private final boolean mCached;
    private List<OtfSegment> mCachedSegments;
    private boolean mAlreadyParsed;

    public OtfSegmentParser(boolean cached) {
        mCached = cached;
    }

    public List<OtfSegment> parse(String url) {
        return parseInt(() -> parseInt(url));
    }

    public List<OtfSegment> parse(Reader stream) {
        return parseInt(() -> parseInt(stream));
    }

    public List<OtfSegment> parseInt(Callable<List<OtfSegment>> callable) {
        List<OtfSegment> result;

        try {
            if (mCached) {
                if (mCachedSegments == null && !mAlreadyParsed) {
                    mCachedSegments = callable.call();
                    mAlreadyParsed = true;
                }

                result = mCachedSegments;
            } else {
                result = callable.call();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    private List<OtfSegment> parseInt(String url) {
        List<OtfSegment> result = null;

        if (url != null) {
            Response response = OkHttpHelpers.doGetOkHttpRequest(url);

            if (response != null && response.body() != null) {
                result = parseInt(response.body().charStream());
            } else {
                Log.e(TAG, "Can't parse url " + url + ". Response is " + response);
            }
        } else {
            Log.e(TAG, "Can't parse url. Url is empty.");
        }

        return result;
    }

    private List<OtfSegment> parseInt(Reader stream) {
        List<OtfSegment> result = null;

        BufferedReader bufferedReader = new BufferedReader(stream);

        try {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                Matcher segmentPatternMatcher = SEGMENT_PATTERN.matcher(currentLine);
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
            for (String item : split) {
                Matcher segmentPatternMatcher = DIGIT_PATTERN.matcher(item);

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
