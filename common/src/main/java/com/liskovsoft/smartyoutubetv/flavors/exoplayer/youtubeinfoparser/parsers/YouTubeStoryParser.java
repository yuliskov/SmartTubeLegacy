package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Storyboard parser</b><br/>
 * <em>Storyboard</em> is a collection of timeline thumbnails<br/>
 * Parses input (get_video_info) to {@link Storyboard}
 */
public class YouTubeStoryParser {
    private static final String TAG = YouTubeStoryParser.class.getSimpleName();
    private static final String SPEC_DELIM = "#";
    private static final String SECTION_DELIM = "\\|";
    private String mSpec;

    /**
     * Extracts storyboard (timeline thumbnails) from the <em>get_video_info</em> file
     * <br/>
     * @param spec specification e.g. <code>https:\/\/i.ytimg.com\/sb\/Pk2oW4SDDxY\/storyboard3_L$L\/$N.jpg|48#27#100#10#10#0#default#vpw4l5h3xmm2AkCT6nMZbvFIyJw|80#45#90#10#10#2000#M$M#hCWDvBSbgeV52mPYmOHjgdLFI1o|160#90#90#5#5#2000#M$M#ys1MKEnwYXA1QAcFiugAA_cZ81Q</code>
     */
    public YouTubeStoryParser(String spec) {
        mSpec = spec;
    }

    public Storyboard extractStory() {
        if (mSpec == null) {
            return null;
        }

        return parseStoryboardSpec(mSpec);
    }

    private Storyboard parseStoryboardSpec(String spec) {
        // EX: https:\/\/i.ytimg.com\/sb\/Pk2oW4SDDxY\/storyboard3_L$L\/$N.jpg|48#27#100#10#10#0#default#vpw4l5h3xmm2AkCT6nMZbvFIyJw|80#45#90#10#10#2000#M$M#hCWDvBSbgeV52mPYmOHjgdLFI1o|160#90#90#5#5#2000#M$M#ys1MKEnwYXA1QAcFiugAA_cZ81Q
        String[] sections = spec.split(SECTION_DELIM);
        String baseUrl = sections[0];
        Storyboard storyboard = new Storyboard();
        storyboard.mBaseUrl = baseUrl;

        for (int i = 1; i < sections.length; i++) {
            String[] sizes = sections[i].split(SPEC_DELIM);
            if (sizes.length != 8) {
                Log.e(TAG, "Error inside spec: " + spec);
                return null;
            }

            Size size = new Size();
            size.mWidth = Integer.valueOf(sizes[0]);
            size.mHeight = Integer.valueOf(sizes[1]);
            size.mQuality = Integer.valueOf(sizes[2]);
            size.mColsCount = Integer.valueOf(sizes[3]);
            size.mRowsCount = Integer.valueOf(sizes[4]);
            size.mDurationEachMS = Integer.valueOf(sizes[5]);
            size.mImageName = sizes[6];
            size.mSignature = sizes[7];

            storyboard.mSizes.add(size);
        }

        return storyboard;
    }

    public class Storyboard {
        private static final int MIN_WIDTH = 4;
        private static final String INDEX_VAR = "$L";
        private static final String IMG_NAME_VAR = "$N";
        private static final String IMG_NUM_VAR = "$M";
        private static final String SIGNATURE_PARAM = "sigh";
        private String mBaseUrl;
        private List<Size> mSizes = new ArrayList<>();
        private int mCachedIdx = -1;
        private int mCachedLenMS = -1;

        public String getGroupUrl(int imgNum) {
            // EX: https:\/\/i.ytimg.com\/sb\/2XY3AvVgDns\/storyboard3_L$L\/$N.jpg
            // EX: https://i.ytimg.com/sb/k4YRWT_Aldo/storyboard3_L2/M0.jpg?sigh=RVdv4fMsE-eDcsCUzIy-iCQNteI

            String link = mBaseUrl.replace("\\", "");
            int bestIdx = chooseBestSizeIdx();
            Size bestSize = mSizes.get(bestIdx);

            link = link.replace(INDEX_VAR, String.valueOf(bestIdx));
            link = link.replace(IMG_NAME_VAR, bestSize.mImageName);
            link = link.replace(IMG_NUM_VAR, String.valueOf(imgNum));

            if (link.contains("?")) {
                link += "&";
            } else {
                link += "?";
            }

            link += SIGNATURE_PARAM + "=" + bestSize.mSignature;

            return link;
        }

        /**
         * Get duration of all thumbnails group
         * @return duration
         */
        public int getGroupDurationMS() {
            if (mCachedLenMS != -1) {
                return mCachedLenMS;
            }

            Size size = mSizes.get(chooseBestSizeIdx());

            int lenMS = size.mRowsCount * size.mColsCount * size.mDurationEachMS;

            mCachedLenMS = lenMS;

            return lenMS;
        }

        public Size getGroupSize() {
            int bestIdx = chooseBestSizeIdx();
            return mSizes.get(bestIdx);
        }

        private int chooseBestSizeIdx() {
            if (mCachedIdx != -1) {
                return mCachedIdx;
            }

            int widest = 0;
            int widestIdx = -1;

            for (Size size : mSizes) {
                if (widest < size.mWidth  || (widest == size.mWidth && mSizes.get(widestIdx).mColsCount < size.mColsCount)) {
                    if (size.mColsCount >= MIN_WIDTH) {
                        widest = size.mWidth;
                        widestIdx = mSizes.indexOf(size);
                    }
                }
            }

            if (widestIdx == -1) {
                widestIdx = 0;
            }

            mCachedIdx = widestIdx;

            return widestIdx;
        }
    }

    public class Size {
        private int mWidth;
        private int mHeight;
        private int mQuality;
        private int mColsCount;
        private int mRowsCount;
        private int mDurationEachMS; // duration of each thumbnail
        private String mImageName;
        private String mSignature;

        /**
         * Duration of each thumbnail in ms
         * @return thumbnail duration
         */
        public int getDurationEachMS() {
            return mDurationEachMS;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getHeight() {
            return mHeight;
        }

        public int getRowCount() {
            return mRowsCount;
        }

        public int getColCount() {
            return mColsCount;
        }
    }
}
