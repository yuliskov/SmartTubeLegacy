package com.liskovsoft.browser.custom;

import android.os.Bundle;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.xwalk.XWalkBrowserActivity;

public abstract class MainBrowserActivity extends XWalkBrowserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
    }

    private void hideTitleBar() {
        // NOTE: we must set theme before ui instantiation
        setTheme(R.style.SimpleUITheme);
    }
}
