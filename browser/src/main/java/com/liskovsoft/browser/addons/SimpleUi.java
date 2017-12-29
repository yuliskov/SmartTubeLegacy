package com.liskovsoft.browser.addons;

import android.app.Activity;
import com.liskovsoft.browser.BaseUi;
import com.liskovsoft.browser.UiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUi extends BaseUi {
    private final Logger logger = LoggerFactory.getLogger(SimpleUi.class);

    public SimpleUi(Activity browser, UiController controller) {
        super(browser, controller);
    }
}
