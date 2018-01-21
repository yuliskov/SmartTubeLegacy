package com.liskovsoft.browser.helpers;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * @hide
 */
public class ResponseUtils {
    /**
     * Returns the response charset of a HTTP response based on the {@code Content-Type} of
     * the response (see RFC 7230). If the {@code Content-Type} header is missing or invalid,
     * the response is assumed to be encoded as {@code UTF-8}. Note that a charset usually
     * makes sense only for {@code "text/plain"} and other "text based" responses.
     *
     * @throws IllegalCharsetNameException if the response specified charset is illegal.
     * @throws UnsupportedCharsetException if the response specified charset is unsupported.
     */
    public static Charset responseCharset(String contentTypeHeader)
            throws IllegalCharsetNameException, UnsupportedCharsetException {
        Charset responseCharset = StandardCharsets.UTF_8;
        if (contentTypeHeader != null) {
            Map<String, String> contentTypeParams = parseContentTypeParameters(contentTypeHeader);
            String charsetParameter = contentTypeParams.get("charset");
            if (charsetParameter != null) {
                responseCharset = Charset.forName(charsetParameter);
            }
        }
        return responseCharset;
    }
    /**
     * Parse content-type parameters. The format of this header is roughly :
     * {@code type/subtype; param1=value1; param2=value2 ...} where each of the
     * parameters are optional. Parsing is lenient, malformed parameters are ignored.
     *
     * Parameter keys & values are trimmed of whitespace and keys are converted to
     * lower case.
     */
    private static Map<String, String> parseContentTypeParameters(String contentTypeHeader) {
        Map<String, String> parameters = Collections.emptyMap();
        String[] fields = contentTypeHeader.split(";");
        if (fields.length > 1) {
            parameters = new HashMap<>();
            // Ignore the first element in the array (the type/subtype).
            for (int i = 1; i < fields.length; ++i) {
                final String parameter = fields[i];
                if (!parameter.isEmpty()) {
                    final String[] components = parameter.split("=");
                    if (components.length != 2) {
                        continue;
                    }
                    final String key = components[0].trim().toLowerCase();
                    final String value = components[1].trim();
                    if (key.isEmpty() || value.isEmpty()) {
                        continue;
                    }
                    parameters.put(key, value);
                }
            }
        }
        return parameters;
    }
}

