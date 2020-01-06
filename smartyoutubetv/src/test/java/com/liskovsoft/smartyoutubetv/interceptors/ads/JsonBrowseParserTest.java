package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class JsonBrowseParserTest {
    private InputStream mOriginalInfo;
    private InputStream mResultInfo;

    @Before
    public void setUp() throws Exception {
        mOriginalInfo = TestHelpers.openResource("ads/browse_home_full.json");
        mResultInfo = TestHelpers.openResource("ads/browse_home_no_masthead_compact.json");
    }

    @Test
    public void testRemoveMasthead() throws IOException {
        InputStream build = JsonBrowseParser.parse(mOriginalInfo).removeMustHead().build();
        assertEquals(TestHelpers.unescapeJavaString(Helpers.toString(mResultInfo)), TestHelpers.unescapeJavaString(Helpers.toString(build)));
    }
}