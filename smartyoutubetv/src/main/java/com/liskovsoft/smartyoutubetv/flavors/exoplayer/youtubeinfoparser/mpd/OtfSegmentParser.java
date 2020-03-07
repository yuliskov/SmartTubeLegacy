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

    public static List<OtfSegment> parse(String url) {
        Response response = OkHttpHelpers.doGetOkHttpRequest(url);

        if (response.body() != null) {
            return parse(response.body().charStream());
        }

        return null;
    }

    public static List<OtfSegment> parse(Reader stream) {
        BufferedReader bufferedReader = new BufferedReader(stream);

        Pattern segmentPattern = Pattern.compile("Segment-Durations-Ms: (.*)");

        try {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                Matcher segmentPatternMatcher = segmentPattern.matcher(currentLine);
                if (segmentPatternMatcher.matches()) {
                    return splitToSegments(segmentPatternMatcher.group(1));
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RegEx test: https://www.freeformatter.com/java-regex-tester.html#ad-output
     */
    private static List<OtfSegment> splitToSegments(String rawString) {
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

                while (segmentPatternMatcher.find()) {
                    findNum++;
                    if (findNum == 1) {
                        segment.setDuration(segmentPatternMatcher.group(0));
                        list.add(segment); // at least duration should be present
                    } else if (findNum == 2) {
                        segment.setRepeatCount(segmentPatternMatcher.group(0));
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
