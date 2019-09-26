package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;
import android.view.KeyEvent;

public interface FragmentManager {
    void setDispatchEvent(KeyEvent event);
    void onAppLoaded();
    void onExitDialogShown();
    boolean isAppLoaded();
    LoadingManager getLoadingManager();
    void startActivityForResult(Intent intent, ActivityResult callback);
    void disableKeyEvents();
    void onSearchFieldFocused();
}
