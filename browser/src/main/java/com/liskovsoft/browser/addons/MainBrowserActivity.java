package com.liskovsoft.browser.addons;

import android.os.Bundle;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.addons.xwalk.XWalkBrowserActivity;

public abstract class MainBrowserActivity extends XWalkBrowserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hideTitleBar();
    }

    // NOTE: don't use this here or you'll get crash on some firmwares
    private void hideTitleBar() {
        // NOTE: we must set theme before ui instantiation
        setTheme(R.style.SimpleUITheme);
    }
}
