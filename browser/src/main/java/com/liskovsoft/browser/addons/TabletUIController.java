package com.liskovsoft.browser.addons;

import androidx.fragment.app.Fragment;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.UI;
import com.liskovsoft.browser.XLargeUi;

public class TabletUIController extends Controller {
    public TabletUIController(Fragment browser) {
        super(browser.getActivity());
        initUI(browser);
    }

    private void initUI(Fragment browser) {
        UI ui = new XLargeUi(browser, this);
        setUi(ui);
    }
}
