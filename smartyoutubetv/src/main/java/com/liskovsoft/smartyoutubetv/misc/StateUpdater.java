package com.liskovsoft.smartyoutubetv.misc;

import android.os.Bundle;

public class StateUpdater {
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

        String newUrl = currentUrl.replaceAll("list=.*&", "");
        state.getBundle(String.valueOf(state.get("current"))).putString("currentUrl", newUrl);
    }
}
