package com.liskovsoft.browser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabBar extends LinearLayout implements View.OnClickListener {
    private static final String TAG = TabBar.class.getSimpleName();
    private final TabScrollView mTabs;
    private final ImageButton mNewTab;
    private Map<Tab, TabView> mTabMap;
    private final UiController mUiController;
    private final Activity mActivity;
    private final TabControl mTabControl;
    private final XLargeUi mUi;

    public TabBar(Activity browser, UiController controller, XLargeUi ui) {
        super(browser);
        mActivity = browser;
        mUiController = controller;
        mTabControl = mUiController.getTabControl();
        mUi = ui;

        mTabMap = new HashMap<>();
        LayoutInflater factory = LayoutInflater.from(browser);
        factory.inflate(R.layout.tab_bar, this);

        mTabs = (TabScrollView) findViewById(R.id.tabs);
        mNewTab = (ImageButton) findViewById(R.id.newtab);
        mNewTab.setOnClickListener(this);

        updateTabs(mUiController.getTabs());
    }

    void updateTabs(List<Tab> tabs) {
        if (tabs == null) {
            Log.e(TAG, "Tab list is empty");
            return;
        }
        mTabs.clearTabs();
        mTabMap.clear();
        for (Tab tab : tabs) {
            TabView tv = buildTabView(tab);
            mTabs.addTab(tv);
        }
        mTabs.setSelectedTab(mTabControl.getCurrentPosition());
    }

    public void onNewTab(Tab tab) {
        TabView tv = buildTabView(tab);
        animateTabIn(tab, tv);
    }

    private void animateTabIn(Tab tab, TabView tv) {
        mTabs.addTab(tv);
        // TODO: not implemented
    }

    private TabView buildTabView(Tab tab) {
        TabView tabview = new TabView(mActivity, tab);
        mTabMap.put(tab, tabview);
        tabview.setOnClickListener(this);
        return tabview;
    }

    @Override
    public void onClick(View view) {
        // TODO: not implemented
    }

    public void setUseQuickControls(boolean useQuickControls) {
        // TODO: not implemented
    }

    /**
     * View used in the tab bar
     */
    class TabView extends LinearLayout implements View.OnClickListener {

        public TabView(Context context, Tab tab) {
            super(context);
        }

        @Override
        public void onClick(View view) {

        }

        public void updateLayoutParams() {

        }
    }
}
