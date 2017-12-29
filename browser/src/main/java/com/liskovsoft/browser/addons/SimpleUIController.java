package com.liskovsoft.browser.addons;

import android.app.Activity;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.UI;

public class SimpleUIController extends Controller {
    public SimpleUIController(Activity browser) {
        super(browser);
        hideActionBar(browser);
        initUi();
    }

    /**
     * NOTE: placing this in different place cause some weird behavior <br/>
     * Ex: android.support.v7.widget.ActionBarOverlayLayout$LayoutParams cannot be cast to com.android.internal.widget.ActionBarOverlayLayout$LayoutParams
     * @param context
     */
    private void hideActionBar(Activity context) {
        // we must set theme before ui instantiation
        context.setTheme(R.style.SimpleUITheme);
    }

    private void initUi() {
        UI ui = new SimpleUi(mActivity, this);
        setUi(ui);
    }
}
