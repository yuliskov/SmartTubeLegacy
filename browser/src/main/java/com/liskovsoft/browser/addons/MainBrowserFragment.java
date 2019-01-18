package com.liskovsoft.browser.addons;

import com.liskovsoft.browser.R;
import com.liskovsoft.browser.xwalk.XWalkBrowserFragment;

public abstract class MainBrowserFragment extends XWalkBrowserFragment {
    // NOTE: don't use this here or you'll get crash on some firmwares
    private void hideTitleBar() {
        // NOTE: we must set theme before ui instantiation
        // NOTE: don't work when inside fragment
        getActivity().setTheme(R.style.SimpleUITheme);
    }
}
