package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.ParserUtils;

import java.io.InputStream;

public class JsonBrowseParser {
    // All objects with property 'tvMastheadRenderer'
    // ex: https://github.com/json-path/JsonPath
    private static final JsonPath TV_MASTHEAD_SECTION_ANY = JsonPath.compile("$..[?(@.tvMastheadRenderer)]");

    private final DocumentContext mParser;

    public JsonBrowseParser(InputStream content) {
        mParser = ParserUtils.createJsonInfoParser(content);
    }

    public static JsonBrowseParser parse(InputStream content) {
        return new JsonBrowseParser(content);
    }

    public boolean removeMastHead() {
        boolean result = false;

        if (ParserUtils.exists(TV_MASTHEAD_SECTION_ANY, mParser)) {
            ParserUtils.delete(TV_MASTHEAD_SECTION_ANY, mParser);
            result = true;
        }

        return result;
    }

    public InputStream toStream() {
        return Helpers.toStream(mParser.jsonString());
    }
}
