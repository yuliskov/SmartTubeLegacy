package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder;

import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc.YouTubeMediaItem;

import java.io.InputStream;
import java.util.List;

public class MyMPDParser implements MPDParser {
    private final InputStream mInputStream;

    public MyMPDParser(InputStream inputStream) {

        mInputStream = inputStream;
    }

    @Override
    public List<YouTubeMediaItem> parse() {
        return null;
    }
}
