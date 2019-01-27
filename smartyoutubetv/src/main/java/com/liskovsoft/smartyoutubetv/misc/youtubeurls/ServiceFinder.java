package com.liskovsoft.smartyoutubetv.misc.youtubeurls;

import android.content.Intent;

public interface ServiceFinder {
    String getUrl();
    boolean isPersistent();
    Intent getIntent(Intent intent);
}
