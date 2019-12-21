package com.liskovsoft.smartyoutubetv.flavors.common.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebView;
import androidx.fragment.app.FragmentActivity;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.Tab;
import com.liskovsoft.browser.addons.MainBrowserFragment;
import com.liskovsoft.browser.addons.SimpleUIController;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.events.ControllerEventListener;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.keyhandler.AmazonKeyHandler;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.fragments.GenericFragment;
import com.liskovsoft.smartyoutubetv.keytranslator.KeyTranslator;
import com.liskovsoft.smartyoutubetv.misc.keyhandler.AltMediaBrowserKeyTranslator;
import com.liskovsoft.smartyoutubetv.misc.keyhandler.BrowserKeyTranslator;
import com.liskovsoft.smartyoutubetv.misc.keyhandler.MediaBrowserKeyTranslator;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator.ServiceFinder;
import com.liskovsoft.smartyoutubetv.misc.youtubeintenttranslator.YouTubeServiceFinder;

public abstract class SmartYouTubeTVBaseFragment extends MainBrowserFragment {
    private static final String TAG = SmartYouTubeTVBaseFragment.class.getSimpleName();
    private Controller mController;
    private KeyTranslator mTranslator;
    private UserAgentManager mUAManager;
    private ServiceFinder mServiceFinder;
    private String mSavedUrl;

    @Override
    public void onActivityCreated(Bundle icicle) {
        Log.i(TAG, "SmartYouTubeTVActivityBase::init");

        setupUA();
        super.onActivityCreated(icicle);

        initYouTubeServices();
        initKeys();

        createController(icicle);
    }

    private String getLocalizedTitle() {
        String label = null;
        try {
            label = getResources().getString(
                    getActivity().getPackageManager().getActivityInfo(getActivity().getComponentName(), 0).labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return label;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            Browser.activityRestored = true;
        }
    }

    private void setupUA() {
        mUAManager = new UserAgentManager();
    }

    private void initKeys() {
        mTranslator = BrowserKeyTranslator.create();
    }

    private void initYouTubeServices() {
        mServiceFinder = new YouTubeServiceFinder(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Entry point for browser related stuff
     */
    private void createController(Bundle icicle) {
        Intent origin = getActivity().getIntent();
        Log.d(TAG, "creating controller: " + "icicle=" + icicle + ", intent=" + origin);

        mController = new SimpleUIController(this);
        mController.setListener(new ControllerEventListener(getActivity(), mController));
        mController.setDefaultUrl(Uri.parse(mServiceFinder.getUrl()));
        mController.setDefaultHeaders(mUAManager.getUAHeaders());

        Intent intent = mServiceFinder.transformIntent(origin);
        boolean restoreState = icicle != null && intent.getData() == null;
        mController.start(restoreState ? null : intent);
        setController(mController);

        startWatchDog();
    }

    private void startWatchDog() {
        FragmentActivity activity = getActivity();

        if (activity != null) {
            new Handler(activity.getMainLooper()).postDelayed(() -> {
                Tab currentTab = mController.getCurrentTab();

                if (currentTab != null) {
                    WebView webView = currentTab.getWebView();
                    if (webView != null) {
                        String url = webView.getUrl();
                        if (!mServiceFinder.checkUrl(url)) {
                            Log.d(TAG, "Oops, wrong url found. Restoring last safe url... " + url);
                            webView.loadUrl(mServiceFinder.getUrl());
                        }
                    }
                }
            }, 5_000);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        event = mTranslator.doTranslateKeys(event);
        setDispatchEvent(event);

        return false;
    }

    private void setDispatchEvent(KeyEvent event) {
        if (getActivity() instanceof FragmentManager) {
            ((FragmentManager) getActivity()).setDispatchEvent(event);
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return translateMouseWheelToArrowKeys(event);
    }

    private boolean translateMouseWheelToArrowKeys(MotionEvent event) {
        if (0 != (event.getSource() & InputDevice.SOURCE_CLASS_POINTER)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_SCROLL:
                    fakeHorizontalScroll(event);
                    fakeVerticalScroll(event);
                    return false;
                // Disable events below completely.
                // This should fix hide off keyboard using air-mouse.
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_HOVER_ENTER:
                case MotionEvent.ACTION_HOVER_EXIT:
                case MotionEvent.ACTION_HOVER_MOVE:
                    return true;
            }
        }
        return false;
    }

    private void fakeVerticalScroll(MotionEvent event) {
        if (Helpers.floatEquals(event.getAxisValue(MotionEvent.AXIS_VSCROLL), 0.0f)) {
            return;
        }
        KeyEvent keyEvent = null;
        if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f)
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN);
        else
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP);
        dispatchKeyEvent(keyEvent);
    }

    private void fakeHorizontalScroll(MotionEvent event) {
        if (Helpers.floatEquals(event.getAxisValue(MotionEvent.AXIS_HSCROLL), 0.0f)) {
            return;
        }
        KeyEvent keyEvent = null;
        if (event.getAxisValue(MotionEvent.AXIS_HSCROLL) < 0.0f)
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT);
        else
            keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT);
        dispatchKeyEvent(keyEvent);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (mServiceFinder.isDefault(intent)) {
            return;
        }

        super.onNewIntent(mServiceFinder.transformIntent(intent));
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        // NOP, handled in other place
    }

    @Override
    public void showSoftKeyboard() {
        mController.showSoftKeyboard();
    }

    /**
     * Release browser memory
     */
    @Override
    public void onMemoryCritical() {
        Log.e(TAG, "App will be killed soon");

        if (getState() == GenericFragment.STATE_PAUSED) {
            mController.onResume();
        }

        if (mController.getCurrentTab() != null) {
            mController.getCurrentTab().reload();
        }
    }

    @Override
    public void onLoad() {
        if (getState() == GenericFragment.STATE_PAUSED) {
            mController.onPause();
        }
    }
}
