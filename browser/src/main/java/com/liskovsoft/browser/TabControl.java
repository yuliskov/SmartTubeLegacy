package com.liskovsoft.browser;

import android.os.Bundle;
import android.webkit.WebView;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TabControl {
    private static final String TAG = TabControl.class.getSimpleName();
    // next Tab ID, starting at 1
    private static long sNextId = 1;
    private final Controller mController;
    private final int mMaxTabs = 99;
    private final ArrayList<Tab> mTabQueue;
    private final BrowserSettings mSettings;
    private ArrayList<Tab> mTabs;
    private int mCurrentTab;
    private int mTabCount;

    private static final String POSITIONS = "positions";
    private static final String CURRENT = "current";
    private OnThumbnailUpdatedListener mOnThumbnailUpdatedListener;

    public static interface OnThumbnailUpdatedListener {
        void onThumbnailUpdated(Tab t);
    }

    public TabControl(Controller controller) {
        mController = controller;
        mTabs = new ArrayList<>(mMaxTabs);
        mTabQueue = new ArrayList<>(mMaxTabs);
        mSettings = BrowserSettings.getInstance();
    }

    /**
     * Check if the state can be restored.  If the state can be restored, the
     * current tab id is returned.  This can be passed to restoreState below
     * in order to restore the correct tab.  Otherwise, -1 is returned and the
     * state cannot be restored.
     */
    long canRestoreState(Bundle inState, boolean restoreIncognitoTabs) {
        final long[] ids = (inState == null) ? null : inState.getLongArray(POSITIONS);
        if (ids == null) {
            return -1;
        }
        final long oldcurrent = inState.getLong(CURRENT);
        long current = -1;
        if (restoreIncognitoTabs || (hasState(oldcurrent, inState) && !isIncognito(oldcurrent, inState))) {
            current = oldcurrent;
        } else {
            // pick first non incognito tab
            for (long id : ids) {
                if (hasState(id, inState) && !isIncognito(id, inState)) {
                    current = id;
                    break;
                }
            }
        }
        return current;
    }

    private boolean hasState(long id, Bundle state) {
        if (id == -1) return false;
        Bundle tab = state.getBundle(Long.toString(id));
        return ((tab != null) && !tab.isEmpty());
    }

    private boolean isIncognito(long id, Bundle state) {
        Bundle tabstate = state.getBundle(Long.toString(id));
        if ((tabstate != null) && !tabstate.isEmpty()) {
            return tabstate.getBoolean(Tab.INCOGNITO);
        }
        return false;
    }

    /**
     * Restore the state of all the tabs.
     * @param currentId The tab id to restore.
     * @param inState The saved state of all the tabs.
     * @param restoreIncognitoTabs Restoring private browsing tabs
     * @param restoreAll All webviews get restored, not just the current tab
     *        (this does not override handling of incognito tabs)
     */
    public void restoreState(Bundle inState, long currentId, boolean restoreIncognitoTabs, boolean restoreAll) {
        if (currentId == -1) {
            return;
        }

        long[] ids = inState.getLongArray(POSITIONS);

        if (ids == null) {
            ids = new long[]{};
        }

        if (ids.length > 1) {
            int maxTabs = mSettings.getMaxTabs();
            if (maxTabs > 0) {
                int startIndex = ids.length - maxTabs;
                if (startIndex >= 0) {
                    Log.d(TAG, "Limiting tabs to " + maxTabs + ", old value: " + ids.length + "...");
                    ids = Arrays.copyOfRange(ids, startIndex, ids.length);
                }
            }
        }

        long maxId = -Long.MAX_VALUE;
        HashMap<Long, Tab> tabMap = new HashMap<>();
        for (long id : ids) {
            if (id > maxId) {
                maxId = id;
            }
            final String idkey = Long.toString(id);
            Bundle state = inState.getBundle(idkey);
            if (state == null || state.isEmpty()) {
                // Skip tab
                continue;
            } else if (!restoreIncognitoTabs
                    && state.getBoolean(Tab.INCOGNITO)) {
                // ignore tab
            } else if (id == currentId || restoreAll) {
                // create tab and restore its state
                Tab t = createNewTab(state, false);
                if (t == null) {
                    // We could "break" at this point, but we want
                    // sNextId to be set correctly.
                    continue;
                }
                tabMap.put(id, t);
                // Me must set the current tab before restoring the state
                // so that all the client classes are set.
                if (id == currentId) {
                    setCurrentTab(t);
                }
            } else {
                // Create a new tab and don't restore the state yet, add it
                // to the tab list
                Tab t = new Tab(mController, state);
                tabMap.put(id, t);
                mTabs.add(t);
                // added the tab to the front as they are not current
                mTabQueue.add(0, t);
            }
        }

        // make sure that there is no id overlap between the restored
        // and new tabs
        sNextId = maxId + 1;

        if (mCurrentTab == -1) {
            if (getTabCount() > 0) {
                setCurrentTab(getTab(0));
            }
        }
        // restore parent/child relationships
        for (long id : ids) {
            final Tab tab = tabMap.get(id);
            final Bundle b = inState.getBundle(Long.toString(id));
            if ((b != null) && (tab != null)) {
                final long parentId = b.getLong(Tab.PARENTTAB, -1);
                if (parentId != -1) {
                    final Tab parent = tabMap.get(parentId);
                    if (parent != null) {
                        parent.addChildTab(tab);
                    }
                }
            }
        }
    }

    /**
     * Create a new tab.
     * @return The newly createTab or null if we have reached the maximum
     *         number of open tabs.
     */
    public Tab createNewTab(boolean privateBrowsing) {
        return createNewTab(null, privateBrowsing);
    }

    private Tab createNewTab(Bundle state, boolean privateBrowsing) {
        WebView w = createNewWebView();
        Tab t = new Tab(mController, w, state);
        mTabs.add(t);
        Log.d(TAG, String.format("Creating tab # %s with id %s. Total tabs: %s", ++mTabCount, t.getId(), mTabs.size()));
        // mController.onTabCreated(t);
        return t;
    }

    /**
     * Creates a new WebView and registers it with the global settings.
     */
    private WebView createNewWebView() {
        return createNewWebView(false);
    }

    boolean canCreateNewTab() {
        return mMaxTabs > mTabs.size();
    }

    /**
     * Creates a new WebView and registers it with the global settings.
     * @param privateBrowsing When true, enables private browsing in the new
     *        WebView.
     */
    private WebView createNewWebView(boolean privateBrowsing) {
        return mController.getWebViewFactory().createWebView(privateBrowsing);
    }

    /**
     * Recreate the main WebView of the given tab.
     */
    void recreateWebView(Tab t) {
        final WebView w = t.getWebView();
        if (w != null) {
            t.destroy();
        }
        // Create a new WebView. If this tab is the current tab, we need to put
        // back all the clients so force it to be the current tab.
        t.setWebView(createNewWebView(), false);
        if (getCurrentTab() == t) {
            setCurrentTab(t, true);
        }
    }

    void addPreloadedTab(Tab tab) {
        for (Tab current : mTabs) {
            if (current != null && current.getId() == tab.getId()) {
                throw new IllegalStateException("Tab with id " + tab.getId() + " already exists: "
                        + current.toString());
            }
        }
        mTabs.add(tab);
        tab.setController(mController);
        mController.onSetWebView(tab, tab.getWebView());
        tab.putInBackground();
    }

    /**
     * Given a Tab, find it's position
     * @param tab to find
     * @return position of Tab or -1 if not found
     */
    int getTabPosition(Tab tab) {
        if (tab == null) {
            return -1;
        }
        return mTabs.indexOf(tab);
    }

    Tab getLeastUsedTab(Tab current) {
        if (getTabCount() == 1 || current == null) {
            return null;
        }
        if (mTabQueue.size() == 0) {
            return null;
        }
        // find a tab which is not the current tab or the parent of the
        // current tab
        for (Tab t : mTabQueue) {
            if (t != null && t.getWebView() != null) {
                if (t != current && t != current.getParent()) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * Return the tab with the matching application id.
     * @param id The application identifier.
     */
    Tab getTabFromAppId(String id) {
        if (id == null) {
            return null;
        }
        for (Tab t : mTabs) {
            if (id.equals(t.getAppId())) {
                return t;
            }
        }
        return null;
    }

    /**
     * Stop loading in all opened WebView including subWindows.
     */
    void stopAllLoading() {
        for (Tab t : mTabs) {
            final WebView webview = t.getWebView();
            if (webview != null) {
                webview.stopLoading();
            }
            final WebView subview = t.getSubWebView();
            if (subview != null) {
                subview.stopLoading();
            }
        }
    }

    public int getCurrentPosition() {
        // TODO: not implemented
        return -1;
    }

    /**
     * Return the current tab's top-level WebView. This can return a subwindow
     * if one exists.
     * @return The top-level WebView of the current tab.
     */
    WebView getCurrentTopWebView() {
        Tab t = getTab(mCurrentTab);
        if (t == null) {
            return null;
        }
        return t.getTopWindow();
    }

    /**
     * Return the current tab's subwindow if it exists.
     * @return The subwindow of the current tab or null if it doesn't exist.
     */
    WebView getCurrentSubWindow() {
        Tab t = getTab(mCurrentTab);
        if (t == null) {
            return null;
        }
        return t.getSubWebView();
    }


    public List<Tab> getTabs() {
        return mTabs;
    }


    /**
     * Return the tab at the specified position.
     * @return The Tab for the specified position or null if the tab does not
     *         exist.
     */
    Tab getTab(int position) {
        if (position >= 0 && position < mTabs.size()) {
            return mTabs.get(position);
        }
        return null;
    }

    /**
     * Return the current tab.
     * @return The current tab.
     */
    Tab getCurrentTab() {
        return getTab(mCurrentTab);
    }

    /**
     * Put the current tab in the background and set newTab as the current tab.
     * @param newTab The new tab. If newTab is null, the current tab is not
     *               set.
     */
    boolean setCurrentTab(Tab newTab) {
        return setCurrentTab(newTab, false);
    }

    // Used by Tab.onJsAlert() and friends
    void setActiveTab(Tab tab) {
        // Calls TabControl.setCurrentTab()
        mController.setActiveTab(tab);
    }

    /**
     * Returns the number of tabs created.
     * @return The number of tabs created.
     */
    int getTabCount() {
        return mTabs.size();
    }


    synchronized static long getNextId() {
        return sNextId++;
    }

    public OnThumbnailUpdatedListener getOnThumbnailUpdatedListener() {
        return mOnThumbnailUpdatedListener;
    }


    /**
     * Destroy all the tabs and subwindows
     */
    void destroy() {
        for (Tab t : mTabs) {
            t.destroy();
        }
        mTabs.clear();
        mTabQueue.clear();
    }

    // This method checks if a tab matches the given url.
    private boolean tabMatchesUrl(Tab t, String url) {
        return url.equals(t.getUrl()) || url.equals(t.getOriginalUrl());
    }

    /**
     * Return the tab that matches the given url.
     * @param url The url to search for.
     */
    Tab findTabWithUrl(String url) {
        if (url == null) {
            return null;
        }
        // Check the current tab first.
        Tab currentTab = getCurrentTab();
        if (currentTab != null && tabMatchesUrl(currentTab, url)) {
            return currentTab;
        }
        // Now check all the rest.
        for (Tab tab : mTabs) {
            if (tabMatchesUrl(tab, url)) {
                return tab;
            }
        }
        return null;
    }

    /**
     * save the tab state:
     * current position
     * position sorted array of tab ids
     * for each tab id, save the tab state
     * @param outState
     */
    void saveState(Bundle outState) {
        final int numTabs = getTabCount();
        if (numTabs == 0) {
            return;
        }
        long[] ids = new long[numTabs];
        int i = 0;
        for (Tab tab : mTabs) {
            Bundle tabState = tab.saveState();
            if (tabState != null) {
                ids[i++] = tab.getId();
                String key = Long.toString(tab.getId());
                if (outState.containsKey(key)) {
                    // Dump the tab state for debugging purposes
                    for (Tab dt : mTabs) {
                        Log.e(TAG, dt.toString());
                    }
                    throw new IllegalStateException(
                            String.format("Error saving state, duplicate tab ids: %s!", key));
                }
                outState.putBundle(key, tabState);
            } else {
                ids[i++] = -1;
                // Since we won't be restoring the thumbnail, delete it
                tab.deleteThumbnail();
            }
        }
        if (!outState.isEmpty()) {
            outState.putLongArray(POSITIONS, ids);
            Tab current = getCurrentTab();
            long cid = -1;
            if (current != null) {
                cid = current.getId();
            }
            outState.putLong(CURRENT, cid);
        }
    }

    /**
     * Return the current tab's main WebView. This will always return the main
     * WebView for a given tab and not a subwindow.
     * @return The current tab's WebView.
     */
    WebView getCurrentWebView() {
        Tab t = getTab(mCurrentTab);
        if (t == null) {
            return null;
        }
        return t.getWebView();
    }

    /**
     * If force is true, this method skips the check for newTab == current.
     */
    private boolean setCurrentTab(Tab newTab, boolean force) {
        Tab current = getTab(mCurrentTab);
        if (current == newTab && !force) {
            return true;
        }
        if (current != null) {
            current.putInBackground();
            mCurrentTab = -1;
        }
        if (newTab == null) {
            return false;
        }

        // Move the newTab to the end of the queue
        int index = mTabQueue.indexOf(newTab);
        if (index != -1) {
            mTabQueue.remove(index);
        }
        mTabQueue.add(newTab);

        // Display the new current tab
        mCurrentTab = mTabs.indexOf(newTab);
        WebView mainView = newTab.getWebView();
        boolean needRestore = mainView == null;
        if (needRestore) {
            // Same work as in createNewTab() except don't do new Tab()
            mainView = createNewWebView();
            newTab.setWebView(mainView);
        }
        newTab.putInForeground();
        return true;
    }

    /**
     * Remove the tab from the list. If the tab is the current tab shown, the
     * last created tab will be shown.
     * @param t The tab to be removed.
     */
    boolean removeTab(Tab t) {
        if (t == null) {
            return false;
        }

        // Grab the current tab before modifying the list.
        Tab current = getCurrentTab();

        // Remove t from our list of tabs.
        mTabs.remove(t);

        // Put the tab in the background only if it is the current one.
        if (current == t) {
            t.putInBackground();
            mCurrentTab = -1;
        } else {
            // If a tab that is earlier in the list gets removed, the current
            // index no longer points to the correct tab.
            mCurrentTab = getTabPosition(current);
        }

        // destroy the tab
        t.destroy();
        // clear it's references to parent and children
        t.removeFromTree();

        // Remove it from the queue of viewed tabs.
        mTabQueue.remove(t);
        return true;
    }
}
