package com.liskovsoft.browser.util;

import android.app.Activity;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.UI;

public class SimpleUIController extends Controller {
    public SimpleUIController(Activity browser) {
        this(browser, null);
    }
    public SimpleUIController(Activity browser, ControllerPostProcessor postProcessor) {
        super(browser, postProcessor);
        // we must set theme before ui instantiation
        browser.setTheme(R.style.SimpleUITheme);
        initUi();
    }

    private void initUi() {
        UI ui = new SimpleUi(mActivity, this);
        setUi(ui);
    }


}
