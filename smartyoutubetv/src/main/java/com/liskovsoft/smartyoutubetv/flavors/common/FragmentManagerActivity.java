package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.BuildConfig;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.common.loading.TipsLoadingManager;
import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.sharedutils.helpers.PermissionManager;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.fragments.ActivityResult;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;
import com.liskovsoft.smartyoutubetv.misc.MainApkUpdater;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.voicesearch.VoiceSearchBridge;
import com.liskovsoft.smartyoutubetv.voicesearch.VoiceSearchBusBridge;

import java.util.HashMap;

public abstract class FragmentManagerActivity extends AppCompatActivity implements FragmentManager {
    private static final String TAG = FragmentManagerActivity.class.getSimpleName();
    private KeyEvent mEvent;
    private GenericFragment mActiveFragment;
    private GenericFragment mPrevFragment;
    private VoiceSearchBridge mVoiceBridge;
    private LoadingManager mLoadingManager;
    private boolean mLoadingDone;
    private MainApkUpdater mApkUpdater;
    private int mRequestCode = 50;
    private HashMap<Integer, ActivityResult> mResultMap;
    private boolean mDisableKeyEvents;
    private static final long LONG_PRESS_TIME_MS = 5_000;
    private Handler mHandler;
    private Runnable mExitAppFn = ()-> {MessageHelpers.showMessage(this, R.string.close_msg); this.finish();};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // fix lang in case activity has been destroyed and then restored
        setupLang();

        if (savedInstanceState != null) {
            // fixing bug where player's menu shown on boot and browser forgot user agent
            Log.d(TAG, "State not null... clearing");
            savedInstanceState.clear();
        }

        super.onCreate(savedInstanceState);


        initPermissions();

        setupFontSize();

        setupVoiceSearch();

        makeActivityHorizontal();
        hideTitleBar();

        mLoadingManager = new TipsLoadingManager(this);
        mApkUpdater = new MainApkUpdater(this);
        mResultMap = new HashMap<>();
        mHandler = new Handler(getMainLooper());
    }

    @Override
    public LoadingManager getLoadingManager() {
        return mLoadingManager;
    }

    private void hideTitleBar() {
        setTheme(com.liskovsoft.browser.R.style.SimpleUITheme);
    }

    protected GenericFragment getActiveFragment() {
        return mActiveFragment;
    }

    protected void setActiveFragment(GenericFragment fragment, boolean pausePrevious) {
        if (fragment == null) {
            throw new IllegalStateException("Active fragment can't be null");
        }

        mDisableKeyEvents = false;

        if (mActiveFragment == fragment) {
            if (pausePrevious && mPrevFragment != null) {
                pauseFragment(mPrevFragment);
            }

            return;
        }

        if (pausePrevious) {
            pauseFragment(mActiveFragment);
            mPrevFragment = null;
        } else {
            // same fragment to be paused later
            mPrevFragment = mActiveFragment;
        }

        mActiveFragment = fragment;

        resumeFragment(mActiveFragment);
    }

    public void pausePrevious() {
        if (mPrevFragment == null) {
            return;
        }

        pauseFragment(mPrevFragment);
        mPrevFragment = null;
    }

    private void resumeFragment(GenericFragment fragment) {
        if (fragment == null) {
            return;
        }

        if (fragment.getState() == GenericFragment.STATE_RESUMED) {
            Log.d(TAG, "Fragment already resumed: " + fragment.getClass().getSimpleName());
            return;
        }

        Log.d(TAG, "Resuming fragment: " + fragment.getClass().getSimpleName());

        // one event instead of onStart and then onResume
        fragment.onResumeFragment();
    }

    private void pauseFragment(GenericFragment fragment) {
        if (fragment == null) {
            return;
        }

        if (fragment.getState() == GenericFragment.STATE_PAUSED) {
            Log.d(TAG, "Fragment already paused: " + fragment.getClass().getSimpleName());
            return;
        }

        Log.d(TAG, "Pausing fragment: " + fragment.getClass().getSimpleName());

        // one event instead of onPause and then onStop
        fragment.onPauseFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mActiveFragment.onNewIntent(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mActiveFragment.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mActiveFragment.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mActiveFragment.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        mActiveFragment.finish();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActiveFragment.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        checkLongPressExit(event);

        if (mDisableKeyEvents) { // 'll be enabled again after fragment switching
            return true;
        }

        Log.d(TAG, "Dispatching event: " + event + ", on fragment: " + mActiveFragment.getClass().getSimpleName());

        event = unknownKeyFix(event);

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && !mLoadingDone) {
            SmartUtils.returnToLaunchersDialog(this);
            return true;
        }

        mEvent = event; // give a choice to modify this event in the middle of the pipeline
        return mVoiceBridge.onKeyEvent(mEvent) || mActiveFragment.dispatchKeyEvent(mEvent) || super.dispatchKeyEvent(mEvent);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return mActiveFragment.dispatchGenericMotionEvent(event) || super.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mActiveFragment.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setDispatchEvent(KeyEvent event) {
        mEvent = event;
    }

    private void setupFontSize() {
        Helpers.adjustFontScale(getResources().getConfiguration(), this);
    }

    private void setupLang() {
        new LangUpdater(this).update();
    }

    private void setupVoiceSearch() {
        mVoiceBridge = new VoiceSearchBusBridge(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResult result = mResultMap.get(requestCode);

        if (result != null) {
            result.onResult(resultCode, data);
            mResultMap.remove(requestCode);
            return;
        }

        mVoiceBridge.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionManager.REQUEST_EXTERNAL_STORAGE) {
            // Check if the only required permission has been granted
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                MessageHelpers.showMessage(this, "REQUEST_EXTERNAL_STORAGE permission has been granted");
            } else {
                MessageHelpers.showLongMessage(this, "Unable to grant REQUEST_EXTERNAL_STORAGE permission");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initPermissions() {
        if (BuildConfig.DEBUG) { // avoid error on genymotion emulator (NoSuchField error)
            return;
        }

        PermissionManager.verifyStoragePermissions(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // int a = 1/0;

        Log.flush();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Helpers.makeActivityFullscreen(this);
        Helpers.makeActivityHorizontal(this);
    }

    @Override
    public void onAppLoaded() {
        mLoadingDone = true;
        mLoadingManager.hide();
        mApkUpdater.start();
    }

    @Override
    public boolean isAppLoaded() {
        return mLoadingDone;
    }

    @Override
    public void startActivityForResult(Intent intent, ActivityResult callback) {
        int requestCode = mRequestCode++;
        mResultMap.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void disableKeyEvents() {
        // only browser: prevent user from pressing back key multiple times
        if (mActiveFragment instanceof BrowserFragment) {
            mDisableKeyEvents = true;
        }
    }

    private void makeActivityHorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    private void checkLongPressExit(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN &&
            event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getRepeatCount() == 0) { // same event fires multiple times
                mHandler.postDelayed(mExitAppFn, LONG_PRESS_TIME_MS);
            }
        } else {
            mHandler.removeCallbacks(mExitAppFn);
        }
    }

    private KeyEvent unknownKeyFix(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_UNKNOWN &&
                event.getScanCode() == 68) {
            event = new KeyEvent(
                    event.getDownTime(),
                    event.getEventTime(),
                    event.getAction(),
                    KeyEvent.KEYCODE_BACK,
                    event.getRepeatCount(),
                    event.getMetaState(),
                    event.getDeviceId(),
                    event.getScanCode(),
                    event.getFlags(),
                    event.getSource()
            );
        }

        return event;
    }
}
