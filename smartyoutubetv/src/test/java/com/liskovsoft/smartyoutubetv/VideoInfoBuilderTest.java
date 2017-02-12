package com.liskovsoft.smartyoutubetv;

import com.liskovsoft.smartyoutubetv.helpers.VideoInfoBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static junit.framework.Assert.assertTrue;

public class VideoInfoBuilderTest {
    private InputStream mOriginalInfo;
    private InputStream mResultedInfo;

    @Before
    public void setUp() throws Exception {
        mOriginalInfo = TestHelpers.openResource("get_video_info_origin");
        mResultedInfo = TestHelpers.openResource("get_video_info_modified");
    }

    @Test
    public void testThatVideoInfoBuilderCanRemoveFormats() throws Exception {
        VideoInfoBuilder builder = new VideoInfoBuilder(mOriginalInfo);
        builder.removeFormat(248); // webm 1920x1080
        builder.removeFormat(137); // mp4 1920x1080
        InputStream result = builder.get();
        assertTrue(IOUtils.contentEquals(result, mResultedInfo));
    }
}
