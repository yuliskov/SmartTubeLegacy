package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.content.Intent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors.PlayerListener;

public interface TwoFragmentsManager extends PlayerListener {
    void openExoPlayer(Intent intent);
    void setPlayerListener(PlayerListener listener);
}
