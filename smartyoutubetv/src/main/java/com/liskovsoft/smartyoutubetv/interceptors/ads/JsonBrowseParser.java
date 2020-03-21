package com.liskovsoft.smartyoutubetv.interceptors.ads;

import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers.ParserUtils;

import java.io.InputStream;

public class JsonBrowseParser {
    // Select one or zero objects with property 'tvMastheadRenderer'
    private static final String TV_MASTHEAD_SECTION =
            "$.contents.tvBrowseRenderer.content.tvSurfaceContentRenderer.content.sectionListRenderer.contents[?(@.tvMastheadRenderer)]";

    // res.contents.tvBrowseRenderer.content.tvSecondaryNavRenderer.sections[0].tvSecondaryNavSectionRenderer.tabs[0].tabRenderer.content.tvSurfaceContentRenderer.content.sectionListRenderer.contents[0].tvMastheadRenderer

    private static final String TV_MASTHEAD_SECTION2 =
            "$.contents.tvBrowseRenderer.content.tvSecondaryNavRenderer.sections[*].tvSecondaryNavSectionRenderer.tabs[*]." +
                    "tabRenderer.content.tvSurfaceContentRenderer.content.sectionListRenderer.contents[?(@.tvMastheadRenderer)]";

    private final DocumentContext mParser;

    public JsonBrowseParser(InputStream content) {
        mParser = ParserUtils.createJsonInfoParser(content);
    }

    public static JsonBrowseParser parse(InputStream content) {
        return new JsonBrowseParser(content);
    }

    public boolean canRemoveMastHead() {
        return ParserUtils.exists(TV_MASTHEAD_SECTION2, mParser) || ParserUtils.exists(TV_MASTHEAD_SECTION, mParser);
    }

    public boolean removeMastHead() {
        boolean result;

        result = ParserUtils.delete(TV_MASTHEAD_SECTION2, mParser) || ParserUtils.delete(TV_MASTHEAD_SECTION, mParser);

        return result;
    }

    public InputStream toStream() {
        return Helpers.toStream(mParser.jsonString());
    }
}
