package com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator;

import android.content.Intent;

public interface ServiceFinder {
    String getUrl();
    Intent transformIntent(Intent intent);
    boolean isDefault(Intent intent);
    boolean checkUrl(String url);
}
