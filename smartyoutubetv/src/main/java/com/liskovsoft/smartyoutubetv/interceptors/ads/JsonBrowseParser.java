package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.ParserUtils;

import java.io.InputStream;

public class JsonBrowseParser {
    private static final String TV_MASTHEAD_SECTION_ROOT = "$.contents.tvBrowseRenderer.content.tvSurfaceContentRenderer.content.sectionListRenderer.contents[0]";
    private static final String TV_MASTHEAD_SECTION = TV_MASTHEAD_SECTION_ROOT + ".tvMastheadRenderer";
    private final DocumentContext mParser;
    private boolean mRemoveMustHead;

    public JsonBrowseParser(InputStream content) {
        mParser = ParserUtils.createJsonInfoParser(Helpers.toString(content));
    }

    public static JsonBrowseParser parse(InputStream content) {
        return new JsonBrowseParser(content);
    }

    public JsonBrowseParser removeMustHead() {
        mRemoveMustHead = true;
        return this;
    }

    public InputStream build() {
        if (mRemoveMustHead && ParserUtils.contains(TV_MASTHEAD_SECTION, mParser)) {
            mParser.delete(TV_MASTHEAD_SECTION_ROOT);
        }

        return Helpers.toStream(mParser.jsonString());
    }
}
