package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.ParserUtils;

import java.io.InputStream;

public class JsonBrowseAdParser {
    // All objects with property 'tvMastheadRenderer'
    // ex: https://github.com/json-path/JsonPath
    private static final JsonPath TV_MASTHEAD_SECTION_ANY = JsonPath.compile("$..[?(@.tvMastheadRenderer)]");

    private final DocumentContext mParser;

    public JsonBrowseAdParser(InputStream content) {
        mParser = ParserUtils.createJsonInfoParser(content);
    }

    public static JsonBrowseAdParser parse(InputStream content) {
        return new JsonBrowseAdParser(content);
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
