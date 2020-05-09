package com.liskovsoft.smartyoutubetv.interceptors.ads.contentfilter;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ReplacingInputStreamTest {
    private InputStream mMainJSOrigin;
    private InputStream mMainJSResult;

    @Before
    public void setUp() {
        mMainJSOrigin = TestHelpers.openResource("ads/main_origin.js");
        mMainJSResult = TestHelpers.openResource("ads/main_result.js");
    }

    @Test
    public void replacementTest() {
        InputStream ris = new ReplacingInputStream(mMainJSOrigin, "tvMastheadRenderer".getBytes(), "tvMastheadRendererOld".getBytes());
        assertEquals(TestHelpers.unescapeJavaString(Helpers.toString(mMainJSResult)), TestHelpers.unescapeJavaString(Helpers.toString(ris)));
    }
}