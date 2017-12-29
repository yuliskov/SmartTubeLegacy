package com.liskovsoft.browser.addons;

import android.app.Activity;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.UI;
import com.liskovsoft.browser.XLargeUi;

public class TabletUIController extends Controller {
    public TabletUIController(Activity browser) {
        super(browser);
        initUI();
    }

    private void initUI() {
        UI ui = new XLargeUi(mActivity, this);
        setUi(ui);
    }
}
