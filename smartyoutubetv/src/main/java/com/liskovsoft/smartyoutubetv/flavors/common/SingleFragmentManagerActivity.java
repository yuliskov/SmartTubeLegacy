package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.R;

public abstract class SingleFragmentManagerActivity extends FragmentManagerActivity {
    private static final String TAG = SingleFragmentManagerActivity.class.getSimpleName();
    private BrowserFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        getLoadingManager().show();

        initBrowserFragment();
        setupEvents();
        Helpers.disableScreensaver(this);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mFragment != null) {
            mFragment.onNewIntent(intent);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                Log.e(TAG, "Warning: app will be killed soon");
                mFragment.onMemoryCritical();
                break;
        }
    }
}
