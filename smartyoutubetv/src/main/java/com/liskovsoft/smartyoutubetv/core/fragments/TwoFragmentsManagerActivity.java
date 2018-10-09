package com.liskovsoft.smartyoutubetv.core.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class TwoFragmentsManagerActivity extends EventManagerActivity {
    private Fragment mFirstFragment;
    private Fragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFirstFragment();
        initSecondFragment();
        setupEvents();
    }

    protected abstract Fragment getFirstFragment();
    protected abstract Fragment getSecondFragment();

    private void setupEvents() {
        setActiveFragment(mFirstFragment);
    }

    private void initFirstFragment() {
        mFirstFragment = getFirstFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, mFirstFragment);
        transaction.commit();
    }

    private void initSecondFragment() {
        mSecondFragment = getSecondFragment();
        if (mSecondFragment == null)
            return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, mSecondFragment);
        transaction.commit();
    }
}
