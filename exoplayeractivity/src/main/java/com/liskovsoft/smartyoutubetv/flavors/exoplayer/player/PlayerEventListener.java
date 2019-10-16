package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

public interface PlayerEventListener {
    void onAppInit();
    void onPlayerCreated();
    void onPlayerClosed();
    void onPlaybackReady();
    void onTrackEnded();
    void onAppPause();
    void onAppResume();
}
