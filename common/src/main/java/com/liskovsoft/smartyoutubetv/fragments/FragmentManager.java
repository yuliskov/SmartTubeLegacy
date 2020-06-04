package com.liskovsoft.smartyoutubetv.fragments;

import android.content.Intent;
import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.misc.keyhandler.GlobalKeyHandler;

public interface FragmentManager {
    void setDispatchEvent(KeyEvent event);
    void onAppLoaded();
    void onExitDialogShown();
    boolean isAppLoaded();
    LoadingManager getLoadingManager();
    void startActivityForResult(Intent intent, ActivityResult callback);
    void disableKeyEvents();
    void onSearchFieldFocused();
    void handleIntent(Intent intent);
    GlobalKeyHandler getKeyHandler();
}
