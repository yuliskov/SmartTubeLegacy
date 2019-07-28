package com.liskovsoft.smartyoutubetv.misc.myquerystring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MyQueryStringFactoryTest {
    private static final String PATH_URL = "http://myurl.com/hello/world/key1/value1/key2/value2/";
    private static final String PATH_URL2 = "http://myurl.com/hello/world/key2/value2/";
    private static final String PATH_URL3 = "http://myurl.com/hello/world/key1/value1/key2/value2/key3/value3";
    private static final String PATH_URL4 = "http://myurl.com/hello/world/key1/value3/key2/value2/";
    private static final String NORMAL_URL = "http://myurl.com/hello/world?key1=value1&key2=value2";
    private static final String NORMAL_URL2 = "http://myurl.com/hello/world?key2=value2";
    private static final String NORMAL_URL3 = "http://myurl.com/hello/world?key1=value1&key2=value2&key3=value3";
    private static final String NORMAL_URL4 = "http://myurl.com/hello/world?key1=value3&key2=value2";

    @Test
    public void testQueryStringValidity() {
        MyQueryString queryString = MyUrlEncodedQueryString.parse(PATH_URL);
        assertTrue(!queryString.isValid());

        queryString = MyUrlEncodedQueryString.parse(NORMAL_URL);
        assertTrue(queryString.isValid());

        queryString = MyPathQueryString.parse(NORMAL_URL);
        assertTrue(!queryString.isValid());

        queryString = MyPathQueryString.parse(PATH_URL);
        assertTrue(queryString.isValid());
    }

    @Test
    public void testParamRemoving() {
        MyQueryString queryString = MyUrlEncodedQueryString.parse(NORMAL_URL);
        queryString.remove("key1");
        assertEquals(NORMAL_URL2, queryString.toString());

        queryString = MyPathQueryString.parse(PATH_URL);
        queryString.remove("key1");
        assertEquals(PATH_URL2, queryString.toString());
    }

    @Test
    public void testParamAdding() {
        MyQueryString queryString = MyUrlEncodedQueryString.parse(NORMAL_URL);
        queryString.set("key3", "value3");
        assertEquals(NORMAL_URL3, queryString.toString());

        queryString = MyPathQueryString.parse(PATH_URL);
        queryString.set("key3", "value3");
        assertEquals(PATH_URL3, queryString.toString());
    }

    @Test
    public void testParamReplacing() {
        MyQueryString queryString = MyUrlEncodedQueryString.parse(NORMAL_URL);
        queryString.set("key1", "value3");
        assertEquals(NORMAL_URL4, queryString.toString());

        queryString = MyPathQueryString.parse(PATH_URL);
        queryString.set("key1", "value3");
        assertEquals(PATH_URL4, queryString.toString());
    }
}