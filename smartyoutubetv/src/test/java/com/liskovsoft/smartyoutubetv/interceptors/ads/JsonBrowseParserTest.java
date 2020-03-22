package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class JsonBrowseParserTest {
    private InputStream mOriginalInfo;
    private InputStream mResultInfo;
    private InputStream mOriginalInfo2;
    private InputStream mResultInfo2;

    @Before
    public void setUp() throws Exception {
        mOriginalInfo = TestHelpers.openResource("ads/browse_home_full.json");
        mResultInfo = TestHelpers.openResource("ads/browse_home_no_masthead_compact.json");

        mOriginalInfo2 = TestHelpers.openResource("ads/tv_masthead2_origin.json");
        mResultInfo2 = TestHelpers.openResource("ads/tv_masthead2_cleared.json");
    }

    @Test
    public void testRemoveMasthead() throws IOException {
        testRemoveMasthead(mOriginalInfo, mResultInfo);
    }

    @Test
    public void testRemoveMasthead2() throws IOException {
        testRemoveMasthead(mOriginalInfo2, mResultInfo2);
    }

    @Test
    public void testReturnValue() {
        JsonBrowseParser parser = JsonBrowseParser.parse(mResultInfo2);

        assertFalse(parser.removeMastHead());
    }

    private void testRemoveMasthead(InputStream origin, InputStream result) {
        JsonBrowseParser parser = JsonBrowseParser.parse(origin);
        if (parser.removeMastHead()) {
            InputStream build = parser.toStream();
            assertEquals(TestHelpers.unescapeJavaString(Helpers.toString(result)), TestHelpers.unescapeJavaString(Helpers.toString(build)));
        } else {
            throw new IllegalStateException();
        }
    }
}