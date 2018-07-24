package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.misc;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * YouTube's decipher routine tools<br/>
 * Routine can have any form<br/>
 * Main file: <em>"tv-player.js"</em><br/>
 * Searched marks: a.split("") ... a.join("")
 */
public class CipherUtils {
    private static final String decipherPattern =
            "var\\ [A-Za-z]{2}\\=\\{.*\\n.*\\n.*\\nfunction\\ [_$A-Za-z]{2}\\(a\\)\\{.*\\;return\\ a\\.join\\(\\\"\\\"\\)\\}";

    public static String extractDecipherCode(InputStream is) {
        Scanner scanner = new Scanner(is);
        Pattern regex = Pattern.compile(decipherPattern);
        String jsCode = scanner.findWithinHorizon(regex, 0);
        if (jsCode == null) {
            return "";
        }
        String cleaned = jsCode.replaceFirst("function\\ [_$A-Za-z]{2}", "function decipherSignature");
        return cleaned;
    }
}
