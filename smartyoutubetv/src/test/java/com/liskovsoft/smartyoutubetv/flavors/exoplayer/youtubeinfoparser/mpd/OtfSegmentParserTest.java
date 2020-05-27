package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpd.OtfSegmentParser.OtfSegment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(RobolectricTestRunner.class)
public class OtfSegmentParserTest {

    @Test
    public void parseTest() {
        InputStream inputStream = TestHelpers.openResource("videoplayback.mp4");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        OtfSegmentParser otfSegmentParser = new OtfSegmentParser(true);
        List<OtfSegment> list = otfSegmentParser.parse(bufferedReader);
        assertThat(list).isNotEmpty().hasSize(8);
        assertThat(list.get(0).getRepeatCount()).isEqualTo("2");
    }
}