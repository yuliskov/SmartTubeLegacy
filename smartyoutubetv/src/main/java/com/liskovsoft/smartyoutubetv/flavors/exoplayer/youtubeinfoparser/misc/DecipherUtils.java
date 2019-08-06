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
    private static final String decipherPattern =
            "var [_$A-Za-z]{2}=\\{.*\\n.*\\n.*\\nfunction [_$A-Za-z]{2}\\(a\\)\\{.*a\\.split\\(\"\"\\).*;return a\\.join\\(\"\"\\)\\}";

    public static String extractDecipherCode(InputStream is) {
        Scanner scanner = new Scanner(is);
        Pattern regex = Pattern.compile(decipherPattern);
        String jsCode = scanner.findWithinHorizon(regex, 0);
        if (jsCode == null) {
            return null;
        }
        String cleaned = jsCode.replaceFirst("function [_$A-Za-z]{2}", "function decipherSignature");
        return cleaned;
    }
}
