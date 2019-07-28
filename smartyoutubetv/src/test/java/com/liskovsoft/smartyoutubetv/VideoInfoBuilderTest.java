package com.liskovsoft.smartyoutubetv;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.VideoFormat;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.VideoInfoBuilder;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.YouTubeVideoInfoBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class VideoInfoBuilderTest {
    private InputStream mOriginalInfo;
    private InputStream mFullHDRemovedInfo;
    private InputStream mFullHDInfo;

    @Before
    public void setUp() throws Exception {
        mOriginalInfo = TestHelpers.openResource("get_video_info_origin");
        mFullHDRemovedInfo = TestHelpers.openResource("get_video_info_full_hd_removed");
        mFullHDInfo = TestHelpers.openResource("get_video_info_full_hd");
    }

    @Test
    public void testFormatsRemoving() throws Exception {
        VideoInfoBuilder builder = new YouTubeVideoInfoBuilder(mOriginalInfo);
        //builder.removeFormat(248); // webm 1920x1080
        //builder.removeFormat(137); // mp4 1920x1080
        builder.removeFormat(VideoFormat._1080p_);
        InputStream result = builder.get();

        assertTrue(IOUtils.contentEquals(result, mFullHDRemovedInfo));
    }

    @Test
    public void testSelectFormat() throws IOException {
        VideoInfoBuilder builder = new YouTubeVideoInfoBuilder(mOriginalInfo);
        builder.selectFormat(VideoFormat._1080p_);
        InputStream result = builder.get();

        assertTrue(IOUtils.contentEquals(result, mFullHDInfo));
    }

    @Test
    public void testGetAllSupportedFormats() {
        Set<VideoFormat> testFormats = createTestFormats();

        VideoInfoBuilder builder = new YouTubeVideoInfoBuilder(mOriginalInfo);
        Set<VideoFormat> allSupportedFormats = builder.getSupportedFormats();
        testFormats.removeAll(allSupportedFormats);

        assertTrue(testFormats.isEmpty());
    }

    private Set<VideoFormat> createTestFormats() {
        Set<VideoFormat> testFormats = new HashSet<>();
        testFormats.add(VideoFormat._144p_);
        testFormats.add(VideoFormat._240p_);
        testFormats.add(VideoFormat._360p_);
        testFormats.add(VideoFormat._480p_);
        testFormats.add(VideoFormat._720p_);
        testFormats.add(VideoFormat._1080p_);
        return testFormats;
    }
}
