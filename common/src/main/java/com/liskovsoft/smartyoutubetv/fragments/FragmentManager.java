package com.liskovsoft.smartyoutubetv.fragments;

import android.view.KeyEvent;
import android.widget.FrameLayout;

public interface FragmentManager {
    void setDispatchEvent(KeyEvent event);
    void onAppLoaded();
    LoadingManager getLoadingManager();
}
