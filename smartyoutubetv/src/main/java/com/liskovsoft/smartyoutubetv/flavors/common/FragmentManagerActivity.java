package com.liskovsoft.smartyoutubetv.flavors.common;

import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.liskovsoft.sharedutils.helpers.AppInfoHelpers;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.helpers.PermissionHelpers;
import com.liskovsoft.sharedutils.locale.LangHelper;
import com.liskovsoft.sharedutils.locale.LocaleContextWrapper;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.flavors.common.loading.TipsLoadingManager;
import com.liskovsoft.smartyoutubetv.fragments.ActivityResult;
import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;
import com.liskovsoft.smartyoutubetv.misc.LangUpdater;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcher;
import com.liskovsoft.smartyoutubetv.misc.appstatewatcher.AppStateWatcherBase;
import com.liskovsoft.smartyoutubetv.misc.keyhandler.GlobalKeyHandler;
import com.liskovsoft.smartyoutubetv.prefs.SmartPreferences;
import com.liskovsoft.smartyoutubetv.voicesearch.VoiceSearchBridge;
import com.liskovsoft.smartyoutubetv.voicesearch.VoiceSearchBusBridge;

import java.util.HashMap;

public abstract class FragmentManagerActivity extends CrashHandlerActivity implements FragmentManager {
    private static final String TAG = FragmentManagerActivity.class.getSimpleName();
    private KeyEvent mEvent;
    private GenericFragment mActiveFragment;
    private GenericFragment mPrevFragment;
    private VoiceSearchBridge mVoiceBridge;
    protected LoadingManager mLoadingManager;
    private boolean mLoadingDone;
    private int mRequestCode = 50;
    private HashMap<Integer, ActivityResult> mResultMap;
    private boolean mDisableKeyEvents;
    private GlobalKeyHandler mKeyHandler;
    private AppStateWatcherBase mAppStateWatcher;
    private Uri mUrlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupLog();

        mAppStateWatcher = new AppStateWatcher(this);
        mAppStateWatcher.run();

        // fix lang in case activity has been destroyed and then restored
        setupLang();

        if (savedInstanceState != null) {
            // fixing bug where player's menu shown on boot and browser forgot user agent
            Log.d(TAG, "State not null... clearing");
            savedInstanceState.clear();
        }

        super.onCreate(savedInstanceState);

        setupFontSize();

        setupVoiceSearch();

        makeActivityHorizontal();

        hideTitleBar();

        mLoadingManager = new TipsLoadingManager(this);
        mResultMap = new HashMap<>();
        mKeyHandler = new GlobalKeyHandler(this);

        mUrlData = getIntent().getData();

        // for search on app boot see onAppLoaded method

        Log.d(TAG, "onCreate intent: " + getIntent().toUri(0)); // print all extras
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
        checkMemory();

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

        CommonApplication.getPreferences().sync();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mActiveFragment.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        CommonApplication.getPreferences().setLastUserInteraction(System.currentTimeMillis());

        mKeyHandler.checkLongPressExit(event);

        if (mDisableKeyEvents || mActiveFragment == null) { // 'll be enabled again after fragment switching
            return true;
        }

        event = mKeyHandler.translateKey(event);

        if (event == null) { // event is ignored
            return false;
        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && !mLoadingDone) {
            Log.d(TAG, "Back pressed. Exiting from the app...");
            mAppStateWatcher.onExit();
            SmartUtils.returnToLaunchersDialogOrExit(this);
            return true;
        }

        Log.d(TAG, "Dispatching event: " + event + ", on fragment: " + mActiveFragment.getClass().getSimpleName());

        mEvent = event; // give a ability to modify this event in the middle of the pipeline
        return mVoiceBridge.onKeyEvent(mEvent) || mActiveFragment.dispatchKeyEvent(mEvent) || super.dispatchKeyEvent(mEvent);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        CommonApplication.getPreferences().setLastUserInteraction(System.currentTimeMillis());

        return mActiveFragment.dispatchGenericMotionEvent(event) || super.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        CommonApplication.getPreferences().setLastUserInteraction(System.currentTimeMillis());

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

    /**
     * Received data from voice dialog
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResult result = mResultMap.get(requestCode);

        if (result != null) {
            result.onResult(resultCode, data);
            mResultMap.remove(requestCode);
            return;
        }

        mAppStateWatcher.onActivityResult(requestCode, resultCode, data);

        mVoiceBridge.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionHelpers.REQUEST_EXTERNAL_STORAGE) {
            // Check if the only required permission has been granted
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.d(TAG, "REQUEST_EXTERNAL_STORAGE permission has been granted");
            } else {
                Log.e(TAG, "Unable to grant REQUEST_EXTERNAL_STORAGE permission");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        mAppStateWatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();

        CommonApplication.getPreferences().sync();
        Log.flush();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Helpers.makeActivityFullscreen(this);
        Helpers.makeActivityHorizontal(this);

        mAppStateWatcher.onResume();
    }

    @Override
    public void onAppLoaded() {
        Log.d(TAG, "App loaded");
        mLoadingDone = true;
        mLoadingManager.hide();
        mAppStateWatcher.onLoad();

        if (mVoiceBridge.openSearchPage(mUrlData)) {
            onSearchQuery();
        }

        mActiveFragment.onLoad();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: " + intent);

        super.onNewIntent(intent);

        mUrlData = intent.getData();

        if (mVoiceBridge.openSearchPage(intent.getData())) {
            onSearchQuery();
        }

        mAppStateWatcher.onNewIntent(intent);
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

    @Override
    public void onExitDialogShown() {
        mKeyHandler.checkDoubleBackExit();
    }

    @Override
    public void onSearchFieldFocused() {
        mActiveFragment.showSoftKeyboard();
    }

    public void onSearchQuery() {}

    private void checkMemory() {
        MemoryInfo memory = Helpers.getAvailableMemory(this);

        if (VERSION.SDK_INT >= 16) {
            Log.d(TAG, "Checking memory. Avail: " + memory.availMem + ". Threshold: " + memory.threshold + ". Total: " + memory.totalMem + ". Low: " + memory.lowMemory);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.d(TAG, "onTrimMemory: " + level);

        checkMemory();

        //if (level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE) {
        //    Log.e(TAG, "Warning: app will be killed soon");
        //    onMemoryCritical();
        //}
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String langCode = new LangUpdater(newBase).getUpdatedLocale();
        super.attachBaseContext(LocaleContextWrapper.wrap(newBase, LangHelper.parseLangCode(langCode)));
    }

    protected void onMemoryCritical() {}

    private void setupLog() {
        // used mainly on custom builds (no bootstrap activity)
        SmartPreferences prefs = CommonApplication.getPreferences();
        Log.init(this, prefs.getLogType(), AppInfoHelpers.getActivityLabel(this, prefs.getBootActivityName()));
    }
}
