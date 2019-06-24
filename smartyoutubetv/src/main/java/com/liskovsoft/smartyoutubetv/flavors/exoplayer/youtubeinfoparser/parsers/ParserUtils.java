package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;

public class ParserUtils {
    private static final String TAG = ParserUtils.class.getSimpleName();

    public static String extractParam(String content, String queryParam) {
        Uri videoInfo = parseUri(content);
        String value = videoInfo.getQueryParameter(queryParam);

        if (value != null && value.isEmpty()) {
            return null;
        }

        return value;
    }

    public static Uri parseUri(String content) {
        if (content.startsWith("http")) {
            return Uri.parse(content);
        }

        return Uri.parse("http://example.com?" + content);
    }

    public static boolean isEmpty(MyQueryString queryString) {
        if (queryString == null) {
            return true;
        }

        return queryString.isEmpty();
    }

    public static DocumentContext createJsonInfoParser(String jsonInfo) {
        if (jsonInfo == null) {
            return null;
        }

        Configuration conf = Configuration
                .builder()
                .mappingProvider(new GsonMappingProvider())
                .jsonProvider(new GsonJsonProvider())
                .build();

        return JsonPath
                .using(conf)
                .parse(jsonInfo);
    }

    public static String extractString(String jsonPath, DocumentContext parser) {
        return ParserUtils.<String>extractType(jsonPath, parser);
    }

    public static Boolean extractBool(String jsonPath, DocumentContext parser) {
        return ParserUtils.<Boolean>extractType(jsonPath, parser);
    }

    private static <T> T extractType(String jsonPath, DocumentContext parser) {
        TypeRef<T> typeRef = new TypeRef<T>() {};

        T result = null;

        try {
            result = parser.read(jsonPath, typeRef);
        } catch (PathNotFoundException e) {
            String msg = "It is ok. JSON content doesn't contains param: " + jsonPath;
            Log.d(TAG, msg);
        }

        return result;
    }
}
