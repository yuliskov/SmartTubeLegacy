package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.liskovsoft.smartyoutubetv.fragments.PlayerFragment;

public interface PlayerInterface extends PlayerFragment {
    boolean isUiVisible();
    SimpleExoPlayer getPlayer();
    PlayerView getExoPlayerView();
}
