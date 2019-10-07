package com.liskovsoft.browser;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liskovsoft.browser.addons.SimpleUIController;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.fragments.BrowserFragment;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseBrowserFragment extends Fragment implements BrowserFragment {
    private static final String TAG = BaseBrowserFragment.class.getSimpleName();
    private Controller mController;
    private final String mDefaultHomeUrl = "https://google.com";
    private final String mDefaultUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    private Map<String, String> mHeaders;

    public static final String ACTION_SHOW_BOOKMARKS = "show_bookmarks";
    public static final String ACTION_SHOW_BROWSER = "show_browser";
    public static final String ACTION_RESTART = "--restart--";
    private static final String EXTRA_STATE = "state";
    public static final String EXTRA_DISABLE_URL_OVERRIDE = "disable_url_override";
    private KeyguardManager mKeyguardManager;
    private PowerManager mPowerManager;
    private Bundle mIcicle;
    private int mState;
    private View mWrapper;
    private boolean mIsAttached;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.browser_fragment, container, false);
    }

    protected void setController(Controller controller) {
        mController = controller;
    }

    private void saveBrowserState(Bundle outState) {
        if (mController == null) {
            return;
        }

        Log.i(TAG, "BrowserActivity.onSaveInstanceState: this=" + this);
        mController.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        // NOTE: fix state saving when finishing activity
        saveBrowserState(null);
        //super.finish();
        //System.exit(0);
    }

    /**
     *  onSaveInstanceState(Bundle map)
     *  onSaveInstanceState is called right before onStop(). The map contains
     *  the saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveBrowserState(outState);
    }

    //@Override
    //public void onMemoryCritical() {
    //    // msg 4 future me: this method is not called when app is hidden
    //    saveBrowserState(null); // app nearly get killed
    //}

    /**
     * Screen might flash during pause. So doing hard work when ui not visible.
     */
    @Override
    public void onStart() {
        super.onStart();

        mIsAttached = true;

        // fragment currently in background state
        if (getState() == GenericFragment.STATE_PAUSED) {
            return;
        }

        resumeController();
    }

    /**
     * Screen might flash during pause. So doing hard work when ui not visible.
     */
    @Override
    public void onStop() {
        super.onStop();

        mIsAttached = false;

        // fragment currently in background state
        if (getState() == GenericFragment.STATE_PAUSED) {
            return;
        }

        pauseController();
    }

    private void pauseController() {
        if (mController == null) {
            return;
        }

        Log.i(TAG, "BrowserActivity.onPause: this=" + this);
        mController.onPause();
    }

    private void resumeController() {
        if (mController == null) {
            return;
        }
        Log.i(TAG, "BrowserActivity.onResume: this=" + this);
        mController.onResume();
    }

    //@SuppressLint("MissingSuperCall")
    //@Override
    //public void onDestroy() {
    //    // NOTE: don't try to call onDestroy(). Or you will get instant crash
    //    super.onResume();
    //    if (mController == null) {
    //        return;
    //    }
    //    logger.info("BrowserActivity.onDestroy: this=" + this);
    //
    //    // NOTE: BUGFIX: fixing bug with Resuming webview timers (friezed youtube logo)
    //    mController.onResume();
    //}

    public static boolean isTablet(Activity activity) {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mController == null) {
            return false;
        }
        return mController.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (mController == null) {
            return false;
        }
        return mController.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mController == null) {
            return false;
        }
        return mController.onKeyUp(keyCode, event);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (mController == null || intent == null) {
            return;
        }
        if (shouldIgnoreIntents()) return;
        if (ACTION_RESTART.equals(intent.getAction())) {
            Bundle outState = new Bundle();
            mController.onSaveInstanceState(outState);
            finish();
            getActivity().getApplicationContext().startActivity(
                    new Intent(getActivity().getApplicationContext(), BaseBrowserFragment.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(EXTRA_STATE, outState));
            return;
        }
        mController.handleNewIntent(intent);
    }

    private boolean shouldIgnoreIntents() {
        // Only process intents if the screen is on and the device is unlocked
        // aka, if we will be user-visible
        if (mKeyguardManager == null) {
            mKeyguardManager = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);
        }
        if (mPowerManager == null) {
            mPowerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        }
        boolean ignore = !mPowerManager.isScreenOn();
        ignore |= mKeyguardManager.inKeyguardRestrictedInputMode();
        Log.i(TAG, "ignore intents: " + ignore);
        return ignore;
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public void onPauseFragment() {
        mState = GenericFragment.STATE_PAUSED;
        pauseController();
    }

    @Override
    public void onResumeFragment() {
        mState = GenericFragment.STATE_RESUMED;

        if (!mIsAttached) {
            return;
        }

        resumeController();
    }

    @Override
    public View getWrapper() {
        return mWrapper;
    }

    @Override
    public void setWrapper(View wrapper) {
        mWrapper = wrapper;
    }
}