package com.liskovsoft.smartyoutubetv.misc;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.liskovsoft.browser.Controller;
import android.content.Context;
import com.liskovsoft.smartyoutubetv.bootstrap.BootstrapActivity;

public class StateUpdater {
    private final Controller mController;
    private final Context mContext;

    public StateUpdater(Controller controller, Context ctx) {
        mController = controller;
        mContext = ctx;
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
        // at this point state is dropped
        //if (isThisPlaylistBundle(state))
        //    state.clear();
        if (isThisActivityStartedFormBootstrap(state)) {
            state.clear();
        }
    }

    private boolean isThisActivityStartedFormBootstrap(Bundle state) {
        if (state == null) {
            return false;
        }
        Intent intent = ((AppCompatActivity) mContext).getIntent();
        boolean fromBootstrap = intent.getBooleanExtra(BootstrapActivity.FROM_BOOTSTRAP, false);
        return fromBootstrap;
    }

    private boolean isThisPlaylistBundle(Bundle state) {
        if (state == null) {
            return false;
        }
        Bundle curState = state.getBundle(String.valueOf(state.get("current")));
        if (curState == null) {
            return false;
        }
        String currentUrl = curState.getString("currentUrl");
        return currentUrl != null && currentUrl.contains("list=");
    }
}
