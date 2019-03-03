package com.liskovsoft.sharedutils.configparser;

import com.liskovsoft.sharedutils.TestHelpers;
import com.liskovsoft.smartyoutubetv.common.BuildConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AssetPropertyParserTest {
    private AssetPropertyParser2 mParser;

    @Before
    public void setUp() throws Exception {
        mParser = new AssetPropertyParser2(RuntimeEnvironment.application, TestHelpers.openResource("test.properties"));
    }

    @Test
    public void testGetKeyValue() {
        assertEquals("simple_value", mParser.get("simple_key"));
    }

    @Test
    public void testGetArray() {
        assertEquals(5, mParser.getArray("simple_array_key").length);
    }

    @Test
    public void getGetValueWithoutComment() {
        assertEquals("simple_value2", mParser.get("simple_key2"));
    }
}