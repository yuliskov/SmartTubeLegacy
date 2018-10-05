package com.liskovsoft.browser.addons;

import android.os.Bundle;
import com.liskovsoft.browser.R;
import com.liskovsoft.browser.addons.xwalk.XWalkBrowserFragment;

public abstract class MainBrowserFragment extends XWalkBrowserFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hideTitleBar();
    }

    // NOTE: don't use this here or you'll get crash on some firmwares
    private void hideTitleBar() {
        // NOTE: we must set theme before ui instantiation
        getActivity().setTheme(R.style.SimpleUITheme);
    }
}
