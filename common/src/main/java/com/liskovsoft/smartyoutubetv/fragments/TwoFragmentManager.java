package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;

public interface TwoFragmentManager extends PlayerListener, FragmentManager {
    void openExoPlayer(Intent intent, boolean pauseBrowser);
    void closeExoPlayer();
    void openBrowser(boolean pausePrevious);
    void setPlayerListener(PlayerListener listener);
    void pausePrevious();
    void onBrowserLoaded();
}
