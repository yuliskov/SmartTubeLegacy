package com.liskovsoft.smartyoutubetv.flavors.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.liskovsoft.browser.fragments.GenericFragment;

public abstract class SingleFragmentManagerActivity extends FragmentManagerActivity {
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragments();
        setupEvents();
    }

    private void setupEvents() {
        setActiveFragment((GenericFragment) mFragment);
    }

    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mFragment = getFragment();
        transaction.add(android.R.id.content, mFragment);
        transaction.commit();
    }

    protected abstract Fragment getFragment();
}
