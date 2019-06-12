package com.liskovsoft.browser.addons;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.UI;

public class SimpleUIController extends Controller {
    public SimpleUIController(Fragment browser) {
        super(browser.getActivity());
        hideActionBar(browser.getActivity());
        initUi(browser);
    }

    /**
     * NOTE: placing this in different place cause some weird behavior <br/>
     * Ex: android.support.v7.widget.ActionBarOverlayLayout$LayoutParams cannot be cast to com.android.internal.widget.ActionBarOverlayLayout$LayoutParams
     * @param context
     */
    private void hideActionBar(Activity context) {
        // we must set theme before ui instantiation
        // NOTE: don't work when inside fragment
        context.setTheme(R.style.SimpleUITheme);
    }

    private void initUi(Fragment browser) {
        UI ui = new SimpleUi(browser, this);
        setUi(ui);
    }
}
