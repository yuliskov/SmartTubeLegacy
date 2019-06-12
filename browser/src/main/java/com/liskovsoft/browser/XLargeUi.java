package com.liskovsoft.browser;

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.List;

public class XLargeUi extends TitleBarBaseUi {
    private static final String TAG = XLargeUi.class.getSimpleName();
    private final Handler mHandler;
    private final TabBar mTabBar;
    //private final ActionBar mActionBar;
    private final NavigationBarTablet mNavBar;
    private ActionBar mActionBar;

    public XLargeUi(Fragment browser, UiController controller) {
        super(browser, controller);

        Log.i(TAG, "About to load tablet interface");

        mHandler = new Handler();
        mNavBar = (NavigationBarTablet) mTitleBar.getNavigationBar();
        mTabBar = new TabBar(mActivity, mUiController, this);
        //mActionBar = mActivity.getActionBar();
        //setupActionBar();
        initActionBar();
        setupActionBar();
        setUseQuickControls(BrowserSettings.getInstance().useQuickControls());
    }

    /**
     * Activates current tab. So tab be able to receive key events. Without this remote controls wont work.
     */
    private void initActionBar() {
        if (!(mActivity instanceof AppCompatActivity))
            return;

        mActionBar = ((AppCompatActivity)mActivity).getSupportActionBar();
    }

    private void setupActionBar() {
        if (mActionBar == null) {
            Log.e(TAG, "Activity does not have support action bar");
            return;
        }
        mActionBar.setNavigationMode(androidx.appcompat.app.ActionBar.NAVIGATION_MODE_STANDARD);
        mActionBar.setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(mTabBar);
    }

    //private void setupActionBar() {
    //    if (mActionBar == null) {
    //        logger.error("Activity does not have action bar");
    //        return;
    //    }
    //    mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    //    mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    //    mActionBar.setCustomView(mTabBar);
    //}

    private boolean isTypingKey(KeyEvent evt) {
        return evt.getUnicodeChar() > 0;
    }

    @Override
    public void setUseQuickControls(boolean useQuickControls) {
        super.setUseQuickControls(useQuickControls);
        checkHideActionBar();
        if (!useQuickControls) {
            mActionBar.show();
        }
        mTabBar.setUseQuickControls(mUseQuickControls);
    }

    private void checkHideActionBar() {
        if (mUseQuickControls) {
            mHandler.post(new Runnable() {
                public void run() {
                    mActionBar.hide();
                }
            });
        }
    }

    @Override
    public void addTab(Tab tab) {
        mTabBar.onNewTab(tab);
    }

    @Override
    public void editUrl(boolean clearInput, boolean forceIME) {
        if (mUseQuickControls) {
            mTitleBar.setShowProgressOnly(false);
        }
        super.editUrl(clearInput, forceIME);
    }

    @Override
    public void updateTabs(List<Tab> tabs) {
        mTabBar.updateTabs(tabs);
        checkHideActionBar();
    }

    @Override
    public boolean dispatchKey(int code, KeyEvent event) {
        if (mActiveTab != null) {
            WebView web = mActiveTab.getWebView();
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (code) {
                    case KeyEvent.KEYCODE_TAB:
                    case KeyEvent.KEYCODE_DPAD_UP:
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if ((web != null) && web.hasFocus() && !mTitleBar.hasFocus()) {
                            editUrl(false, false);
                            return true;
                        }
                }
                boolean ctrl = event.hasModifiers(KeyEvent.META_CTRL_ON);
                if (!ctrl && isTypingKey(event) && !mTitleBar.isEditingUrl()) {
                    editUrl(true, false);
                    return mContentView.dispatchKeyEvent(event);
                }
            }
        }
        return false;
    }
}
