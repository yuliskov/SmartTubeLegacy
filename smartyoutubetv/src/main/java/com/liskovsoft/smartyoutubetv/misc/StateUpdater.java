package com.liskovsoft.smartyoutubetv.misc;

import android.os.Bundle;
import com.liskovsoft.browser.Controller;

public class StateUpdater {
    private final Controller mController;

    public StateUpdater(Controller controller) {
        mController = controller;
    }

    public void fixPlaylistUrl(Bundle state) {
        //TabControl tabControl = mController.getTabControl();
        //String url = tabControl.getTabs().get(0).getWebView().getUrl();

        String currentUrl = state.getBundle(String.valueOf(state.get("current"))).getString("currentUrl");
        if (currentUrl == null) {
            return;
        }

        if (!currentUrl.contains("list=")) {
            return;
        }

        // we can't alter bundle with WebView state so reset it instead

        String newUrl = currentUrl.replaceAll("list=.*&", "");
        state.getBundle(String.valueOf(state.get("current"))).putString("currentUrl", newUrl);
    }

    public void updateState(Bundle state) {
        if (isThisPlaylistBundle(state))
            state.clear();
    }

    private boolean isThisPlaylistBundle(Bundle state) {
        if (state == null) {
            return false;
        }
        String currentUrl = state.getBundle(String.valueOf(state.get("current"))).getString("currentUrl");
        return currentUrl != null && currentUrl.contains("list=");
    }
}
