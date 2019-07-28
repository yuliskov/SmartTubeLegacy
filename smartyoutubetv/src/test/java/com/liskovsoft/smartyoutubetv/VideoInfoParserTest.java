package com.liskovsoft.smartyoutubetv;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.VideoInfoParser;
import com.liskovsoft.smartyoutubetv.misc.oldyoutubeinfoparser.YouTubeVideoInfoParser;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class VideoInfoParserTest {
    private InputStream mVideoInfo;
    private InputStream mHDVideoLink;

    @Before
    public void setUp() throws Exception {
        mVideoInfo = TestHelpers.openResource("get_video_info_origin");
        mHDVideoLink = TestHelpers.openResource("extract_video_link_result");
    }

    @Test
    public void testGetHDVideoLink() throws IOException {
        VideoInfoParser videoInfoParser = new YouTubeVideoInfoParser(mVideoInfo);
        String result = videoInfoParser.getHDVideoLink();

        assertTrue(IOUtils.contentEquals(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8)), mHDVideoLink));
    }
}
