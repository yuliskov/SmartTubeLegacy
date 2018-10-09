package com.liskovsoft.smartyoutubetv.core.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class SingleFragmentManagerActivity extends EventManagerActivity {
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFragments();
        setupEvents();
    }

    private void setupEvents() {
        setActiveFragment(mFragment);
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
