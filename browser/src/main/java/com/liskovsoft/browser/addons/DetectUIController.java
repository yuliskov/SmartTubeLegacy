package com.liskovsoft.browser.addons;

import androidx.fragment.app.Fragment;
import com.liskovsoft.browser.BaseBrowserFragment;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.PhoneUi;
import com.liskovsoft.browser.UI;
import com.liskovsoft.browser.XLargeUi;

public class DetectUIController extends Controller {
    public DetectUIController(Fragment browser) {
        super(browser.getActivity());
        initUi(browser);
    }

    /**
     * Create UI that best fit into your device.
     * @param browser
     */
    private void initUi(Fragment browser) {
        boolean xlarge = BaseBrowserFragment.isTablet(mActivity);
        UI ui;
        if (xlarge) {
            ui = new XLargeUi(browser, this);
        } else {
            ui = new PhoneUi(browser, this);
        }
        setUi(ui);
    }
}
