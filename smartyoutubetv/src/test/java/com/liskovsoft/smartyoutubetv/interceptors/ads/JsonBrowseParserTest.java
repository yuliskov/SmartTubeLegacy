package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class JsonBrowseParserTest {
    private InputStream mOriginalInfo;
    private InputStream mResultInfo;
    private InputStream mOriginalInfo2;
    private InputStream mResultInfo2;
    private InputStream mOriginalInfo3;
    private InputStream mResultInfo3;

    @Before
    public void setUp() {
        mOriginalInfo = TestHelpers.openResource("ads/browse_home_full.json");
        mResultInfo = TestHelpers.openResource("ads/browse_home_no_masthead_compact.json");

        mOriginalInfo2 = TestHelpers.openResource("ads/tv_masthead2_origin.json");
        mResultInfo2 = TestHelpers.openResource("ads/tv_masthead2_cleared.json");

        mOriginalInfo3 = TestHelpers.openResource("ads/tv_masthead3_origin.json");
        mResultInfo3 = TestHelpers.openResource("ads/tv_masthead3_cleared.json");
    }

    @Test
    public void testRemoveMasthead() {
        testRemoveMasthead(mOriginalInfo, mResultInfo);
    }

    @Test
    public void testRemoveMasthead2() {
        testRemoveMasthead(mOriginalInfo2, mResultInfo2);
    }

    @Test
    public void testRemoveMasthead3() {
        testRemoveMasthead(mOriginalInfo3, mResultInfo3);
    }

    @Test
    public void testReturnValue() {
        JsonBrowseAdParser parser = JsonBrowseAdParser.parse(mResultInfo2);

        assertFalse(parser.removeMastHead());
    }

    private void testRemoveMasthead(InputStream origin, InputStream result) {
        JsonBrowseAdParser parser = JsonBrowseAdParser.parse(origin);
        if (parser.removeMastHead()) {
            InputStream build = parser.toStream();
            assertEquals(TestHelpers.unescapeJavaString(Helpers.toString(result)), TestHelpers.unescapeJavaString(Helpers.toString(build)));
        } else {
            throw new IllegalStateException();
        }
    }
}