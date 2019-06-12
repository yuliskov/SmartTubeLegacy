package com.liskovsoft.browser;

import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import com.liskovsoft.sharedutils.mylogger.Log;

public class PhoneUi extends TitleBarBaseUi {
    private static final String TAG = PhoneUi.class.getSimpleName();
    private final int mActionBarHeight;
    private NavigationBarPhone mNavigationBar;

    public PhoneUi(Fragment browser, UiController controller) {
        super(browser, controller);

        Log.i(TAG, "About to load phone interface");

        setUseQuickControls(BrowserSettings.getInstance().useQuickControls());
        mNavigationBar = (NavigationBarPhone) mTitleBar.getNavigationBar();
        TypedValue heightValue = new TypedValue();
        // below proper way to work with com.android.internal.R.attr.actionBarSize resource
        int actionBarSize = Resources.getSystem().getIdentifier("actionBarSize", "attr", "android");
        browser.getActivity().getTheme().resolveAttribute(
                actionBarSize, heightValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(heightValue.data,
                browser.getResources().getDisplayMetrics());

    }
}
