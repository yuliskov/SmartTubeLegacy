package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parsers;

import android.net.Uri;

import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.misc.myquerystring.MyQueryString;

import java.io.InputStream;

public final class ParserUtils {

    private static final String TAG = ParserUtils.class.getSimpleName();

    private static final Configuration JSON_PATH_CONFIGURATION = Configuration.builder()
            .mappingProvider(new GsonMappingProvider())
            .jsonProvider(new GsonJsonProvider())
            .build();

    private ParserUtils() {
    }

    public static String extractParam(String queryParam, String content) {
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

    public static <T> DocumentContext createJsonInfoParser(T jsonInfo) {
        // NOTE: Use String as input. InputStreams do not work!!!!

        if (jsonInfo == null) {
            throw new IllegalStateException("Can't create parser. jsonInfo == null");
        }

        DocumentContext jsonPath;

        try {
            if (jsonInfo instanceof InputStream) {
                jsonPath = JsonPath.using(JSON_PATH_CONFIGURATION).parse((InputStream) jsonInfo, "UTF-8");
            } else if (jsonInfo instanceof String) {
                jsonPath = JsonPath.using(JSON_PATH_CONFIGURATION).parse((String) jsonInfo);
            } else {
                throw new IllegalStateException("Can't create parser. Unknown input type: " + jsonInfo.getClass().getSimpleName());
            }
        } catch (JsonSyntaxException e) {
            throw new IllegalStateException("Malformed json: " + jsonInfo, e);
        }

        return jsonPath;
    }

    public static boolean contains(String jsonPath, DocumentContext parser) {
        return ParserUtils.<Object>extractType(jsonPath, parser) != null;
    }

    public static Integer extractInt(String jsonPath, DocumentContext parser) {
        return ParserUtils.<Integer>extractType(jsonPath, parser);
    }

    public static String extractString(String jsonPath, DocumentContext parser) {
        return ParserUtils.<String>extractType(jsonPath, parser);
    }

    public static Boolean extractBool(String jsonPath, DocumentContext parser) {
        return ParserUtils.<Boolean>extractType(jsonPath, parser);
    }

    private static <T> T extractType(String jsonPath, DocumentContext parser) {
        if (parser == null) {
            Log.e(TAG, "Can't extract JSON value. Parser is null");
            return null;
        }

        TypeRef<T> typeRef = new TypeRef<T>() {};

        T result = null;

        try {
            result = parser.read(jsonPath, typeRef);
        } catch (PathNotFoundException e) {
            String msg = "Can't get value. JSON content doesn't contains param: " + jsonPath;
            Log.d(TAG, msg);
        }

        return result;
    }

    public static void delete(JsonPath jsonPath, DocumentContext parser) {
        try {
            parser.delete(jsonPath);
        } catch (PathNotFoundException e) { // NOTE: exception isn't thrown in some cases
            String msg = "Can't delete value. JSON content doesn't contains param: " + jsonPath;
            Log.d(TAG, msg);
        }
    }

    public static boolean exists(JsonPath jsonPath, DocumentContext parser) {
        boolean result;

        try {
            JsonArray objOrArray = parser.read(jsonPath);

            result = objOrArray.size() != 0;
        } catch (PathNotFoundException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
