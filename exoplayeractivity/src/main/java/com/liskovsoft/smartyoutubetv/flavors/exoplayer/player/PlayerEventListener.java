package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

public interface PlayerEventListener {
    void onAppPause();
    void onAppResume();
    void onAppInit();
    void onPlayerCreated();
    void onPlayerDestroyed();
    void onPlaybackReady();
}
