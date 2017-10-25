package com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.tmp;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CipherUtils {
    public static String decipherSignature(String sig) {
        List<String> letters = new ArrayList<>(Arrays.asList(sig.split("")));
        permute(letters, 7);
        reverse(letters, 19);
        permute(letters, 33);
        splice(letters, 2);
        permute(letters, 51);
        splice(letters, 2);
        permute(letters, 46);
        reverse(letters, 70);
        splice(letters, 1);
        return TextUtils.join("", letters);
    }
    private static void permute(List<String> a, int b) {
        String c = a.get(0);
        a.set(0, a.get(b % a.size()));
        a.set(b, c);
    }
    private static void reverse(List<String> a, int b) {
        Collections.reverse(a);
    }
    private static void splice(List<String> a, int b) {
        for (int i = 0; i < b; i++) {
            a.remove(0);
        }
    }
}
