package com.liskovsoft.smartyoutubetv.flavors.common.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Controller;
import com.liskovsoft.browser.addons.MainBrowserFragment;
import com.liskovsoft.browser.addons.SimpleUIController;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.smartyoutubetv.events.ControllerEventListener;
import com.liskovsoft.smartyoutubetv.fragments.FragmentManager;
import com.liskovsoft.smartyoutubetv.keytranslator.BrowserKeyTranslator;
import com.liskovsoft.smartyoutubetv.keytranslator.KeyTranslator;
import com.liskovsoft.smartyoutubetv.misc.intenttranslator.ServiceFinder;
import com.liskovsoft.smartyoutubetv.misc.UserAgentManager;
import com.liskovsoft.smartyoutubetv.misc.intenttranslator.YouTubeServiceFinder;

public abstract class SmartYouTubeTVBaseFragment extends MainBrowserFragment {
    private static final String TAG = SmartYouTubeTVBaseFragment.class.getSimpleName();
    private Controller mController;
    private KeyTranslator mTranslator;
    private UserAgentManager mUAManager;
    private ServiceFinder mServiceFinder;

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
        mUAManager = new UserAgentManager(getActivity());
    }

    private void initKeys() {
        mTranslator = new BrowserKeyTranslator();
    }

    private void initYouTubeServices() {
        mServiceFinder = new YouTubeServiceFinder(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createController(Bundle icicle) {
        Intent origin = getActivity().getIntent();
        Log.d(TAG, "creating controller: " + "icicle=" + icicle + ", intent=" + origin);

        mController = new SimpleUIController(this);
        mController.setListener(new ControllerEventListener(getActivity(), mController));
        mController.setDefaultUrl(Uri.parse(mServiceFinder.getUrl()));
        mController.setDefaultHeaders(mUAManager.getUAHeaders());

        Intent intent = mServiceFinder.getIntent(origin);
        boolean restoreState = icicle != null && intent.getData() == null;
        mController.start(restoreState ? null : intent);
        setController(mController);
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
        super.onNewIntent(mServiceFinder.getIntent(intent));
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        // NOP
    }
}
