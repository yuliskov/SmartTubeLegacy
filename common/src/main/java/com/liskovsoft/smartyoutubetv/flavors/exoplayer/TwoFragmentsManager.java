package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.content.Intent;

public interface TwoFragmentsManager {
    void openExoPlayer(Intent intent);
    void playerClosed(Intent intent);
}
