package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.content.Intent;
import com.jayway.jsonpath.DocumentContext;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

import java.util.Arrays;

public abstract class JsonParserBase {
    private static final String TAG = JsonParserBase.class.getSimpleName();
    private final DocumentContext mParser;

    /**
     * Parses next json content
     * @param nextContent json content
     * @param <T> Could be String or Stream
     */
    public <T> JsonParserBase(T nextContent) {
        mParser = ParserUtils.createJsonInfoParser(nextContent);
    }

    public JsonParserBase(DocumentContext parser) {
        mParser = parser;
    }

    protected Integer integer(String... paths) {
        Integer result = null;

        for (String path : paths) {
            result = ParserUtils.extractInt(path, mParser);
            if (result != null) {
                break;
            }
        }

        if (result == null) {
            Log.e(TAG, "Oops... seems that video metadata format has been changed: " + Arrays.toString(paths));
        }

        return result;
    }

    protected Boolean bool(String... paths) {
        Boolean result = null;

        for (String path : paths) {
            result = ParserUtils.extractBool(path, mParser);
            if (result != null) {
                break;
            }
        }
        
        if (result == null) {
            Log.d(TAG, "Oops... seems that video metadata format has been changed: " + Arrays.toString(paths));
        }

        return result;
    }

    protected String str(String... paths) {
        String result = null;

        for (String path : paths) {
            result = ParserUtils.extractString(path, mParser);
            if (result != null) {
                break;
            }
        }

        if (result == null) {
            Log.d(TAG, "Oops... seems that video metadata format has been changed: " + Arrays.toString(paths));
        }

        return result;
    }
}
