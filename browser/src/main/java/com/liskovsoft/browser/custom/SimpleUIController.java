package com.liskovsoft.browser.custom;

import android.app.Activity;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.UI;
import com.liskovsoft.browser.xwalk.XWalkInitCompleted;
import com.squareup.otto.Subscribe;

public class SimpleUIController extends Controller {
    public SimpleUIController(Activity browser) {
        super(browser);
        // we must set theme before ui instantiation
        browser.setTheme(R.style.SimpleUITheme);
        initUi();
    }

    private void initUi() {
        UI ui = new SimpleUi(mActivity, this);
        setUi(ui);
    }
}
