package com.liskovsoft.smartyoutubetv.flavors.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.liskovsoft.browser.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.R;

public abstract class SingleFragmentManagerActivity extends FragmentManagerActivity {
    private BrowserFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        initBrowserFragment();
        setupEvents();
    }

    private void setupEvents() {
        setActiveFragment(mFragment, true);
    }

    private void initBrowserFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mFragment = getBrowserFragment();
        transaction.add(R.id.browser_wrapper, (Fragment) mFragment);
        transaction.commit();
    }

    protected abstract BrowserFragment getBrowserFragment();
}
