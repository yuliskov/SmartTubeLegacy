package com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CipherUtils2 {
    private static final String decipherPattern_old1 = "var\\ EQ\\=\\{[\\s\\S]*function\\ FQ\\(a\\)\\{[\\s\\S]*?\\}";
    private static final String decipherPattern = "var\\ [A-Z]{1}Q\\=\\{[\\s\\S]*?function\\ [A-Z]{1}Q\\(a\\)\\{[\\s\\S]*?\\}";

    public static String extractDecipherCode(InputStream is) {
        Scanner scanner = new Scanner(is);
        Pattern regex = Pattern.compile(decipherPattern);
        String jsCode = scanner.findWithinHorizon(regex, 0);
        String cleaned = jsCode.replaceFirst("function\\ [A-Z]{2}", "function decipherSignature");
        return cleaned;
    }
}
