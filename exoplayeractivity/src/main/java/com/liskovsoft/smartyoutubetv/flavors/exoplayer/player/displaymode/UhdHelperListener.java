package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode;

import android.view.Window;

/**
 * The interface that must be implemented and registered
 * with {@link UhdHelper#registerModeChangeListener(UhdHelperListener) registerListener}
 * to find out the result of requested mode change.
 * <p>
 * Callback will be issued on the Main/UI thread of the application.
 *
 * To unregister the listener, use
 * {@link UhdHelper#unregisterDisplayModeChangeListener(UhdHelperListener) unregisterDisplayModeChangeListener}
 */
public interface UhdHelperListener {
    /**
     * Callback containing the result of the mode change after
     * {@link UhdHelper#setPreferredDisplayModeId(Window, int,boolean) setPreferredDisplayModeId}
     * returns a true.
     *
     * @param mode The {@link Display.Mode Mode} object containing
     *             the mode switched to OR NULL if there was a timeout
     *             or internal error while changing the mode.
     */
    void onModeChanged(Display.Mode mode);

}

