package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.ParserUtils;

import java.io.InputStream;

public class JsonBrowseParser {
    // Select one or zero objects with property 'tvMastheadRenderer'
    private static final String TV_MASTHEAD_SECTION =
            "$.contents.tvBrowseRenderer.content.tvSurfaceContentRenderer.content.sectionListRenderer.contents[?(@.tvMastheadRenderer)]";
    private final DocumentContext mParser;
    private boolean mRemoveMustHead;

    public JsonBrowseParser(InputStream content) {
        mParser = ParserUtils.createJsonInfoParser(content);
    }

    public static JsonBrowseParser parse(InputStream content) {
        return new JsonBrowseParser(content);
    }

    public boolean canRemoveMustHead() {
        return ParserUtils.exists(TV_MASTHEAD_SECTION, mParser);
    }

    public JsonBrowseParser removeMustHead() {
        mRemoveMustHead = true;

        return this;
    }

    public InputStream build() {
        if (mRemoveMustHead) {
            ParserUtils.delete(TV_MASTHEAD_SECTION, mParser);
        }

        return Helpers.toStream(mParser.jsonString());
    }
}
