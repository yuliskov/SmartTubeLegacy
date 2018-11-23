package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;

public interface TwoFragmentsManager extends PlayerListener {
    void openExoPlayer(Intent intent);
    void setPlayerListener(PlayerListener listener);
    void onBrowserReady();
}
