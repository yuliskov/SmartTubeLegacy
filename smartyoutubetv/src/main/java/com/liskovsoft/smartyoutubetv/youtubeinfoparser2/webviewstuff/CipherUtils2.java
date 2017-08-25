package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CipherUtils2 {
    private static final String decipherPattern =
            "var\\ [A-Za-z]{2}\\=\\{.*\\n.*\\n.*\\nfunction\\ [A-Za-z]{2}\\(a\\)\\{.*\\;return\\ a\\.join\\(\\\"\\\"\\)\\}";

    public static String extractDecipherCode(InputStream is) {
        Scanner scanner = new Scanner(is);
        Pattern regex = Pattern.compile(decipherPattern);
        String jsCode = scanner.findWithinHorizon(regex, 0);
        if (jsCode == null) {
            return "";
        }
        String cleaned = jsCode.replaceFirst("function\\ [A-Za-z]{2}", "function decipherSignature");
        return cleaned;
    }
}
