package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser;

import android.net.Uri;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class PlayerResponseParser {
    private final String mContent;
    private final DocumentContext mParser;

    public PlayerResponseParser(String content) {
        if (content == null) {
            throw new IllegalStateException("content cannot be null");
        }

        mContent = content;
        mParser = JsonPath.parse(mContent);
    }

    public Uri getSubsUri() {
        String url = mParser.read("$.captions.playerCaptionsTracklistRenderer.captionTracks[0].baseUrl");
        return Uri.parse(url);
    }
}
