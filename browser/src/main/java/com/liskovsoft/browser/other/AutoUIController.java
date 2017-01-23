package com.liskovsoft.browser.other;

import android.app.Activity;
import com.liskovsoft.browser.*;

public class AutoUIController extends Controller {
    public AutoUIController(Activity browser) {
        super(browser);
        initUi();
    }

    /**
     * Create UI that best fit into your device.
     */
    private void initUi() {
        boolean xlarge = BrowserActivity.isTablet(mActivity);
        UI ui;
        if (xlarge) {
            ui = new XLargeUi(mActivity, this);
        } else {
            ui = new PhoneUi(mActivity, this);
        }
        setUi(ui);
    }
}
