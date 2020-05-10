package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.misc;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * YouTube's decipher routine tools<br/>
 * Routine can have any form<br/>
 * Files: <em>"tv-player.js"</em> or <em>"tv-player-ias.js"</em><br/>
 * Pattern: a.split("") ... a.join("")
 */
public class DecipherUtils {
    // Don't remove var part! It is used inside the decipher function.
    private static final Pattern DECIPHER_PATTERN = Pattern.compile(
            "var [_$A-Za-z]{2}=\\{.*\\n.*\\n.*\\nfunction [_$A-Za-z]{2}\\(a\\)\\{.*a\\.split\\(\"\"\\).*;return a\\.join\\(\"\"\\)\\}");
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("function [_$A-Za-z]{2}");

    public static String extractDecipherCode(InputStream is) {
        Scanner scanner = new Scanner(is);
        String jsCode = scanner.findWithinHorizon(DECIPHER_PATTERN, 0);
        if (jsCode == null) {
            return null;
        }
        return FUNCTION_PATTERN.matcher(jsCode).replaceFirst("function decipherSignature");
    }
}
