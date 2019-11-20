package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate;

import android.app.Activity;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.Window;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.DisplayHolder.Mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

// Source: https://developer.amazon.com/docs/fire-tv/4k-apis-for-hdmi-mode-switch.html#amazonextension

class DisplaySyncHelper implements UhdHelperListener {
    private static final String TAG = DisplaySyncHelper.class.getSimpleName();
    private static final int STATE_ORIGINAL = 1;
    private static final int STATE_CURRENT = 2;
    protected final Activity mContext;
    private boolean mDisplaySyncInProgress = false;
    private UhdHelper mUhdHelper;
    protected DisplayHolder.Mode mOriginalMode;
    protected DisplayHolder.Mode mNewMode;
    private DisplayHolder.Mode mCurrentMode;
    // switch not only framerate but resolution too
    protected static boolean SWITCH_TO_UHD = true;
    private int mModeLength = -1;
    private AutoFrameRateListener mListener;

    interface AutoFrameRateListener {
        void onModeStart(Mode newMode);
    }

    public DisplaySyncHelper(Activity context) {
        mContext = context;
    }

    private List<DisplayHolder.Mode> filterSameResolutionModes(DisplayHolder.Mode[] oldModes, DisplayHolder.Mode currentMode) {
        if (currentMode == null) {
            return Collections.emptyList();
        }

        ArrayList<DisplayHolder.Mode> newModes = new ArrayList<>();
        int oldModesLen = oldModes.length;

        for (int i = 0; i < oldModesLen; ++i) {
            DisplayHolder.Mode mode = oldModes[i];
            if (mode == null) {
                continue;
            }

            if (mode.getPhysicalHeight() == currentMode.getPhysicalHeight() && mode.getPhysicalWidth() == currentMode.getPhysicalWidth()) {
                newModes.add(mode);
            }
        }

        return newModes;
    }

    private ArrayList<DisplayHolder.Mode> filterModes(DisplayHolder.Mode[] oldModes, int minHeight, int maxHeight) {
        ArrayList<DisplayHolder.Mode> newModes = new ArrayList<>();
        int modesNum = oldModes.length;

        for (int i = 0; i < modesNum; ++i) {
            DisplayHolder.Mode mode = oldModes[i];
            int height = mode.getPhysicalHeight();
            if (height >= minHeight && height <= maxHeight) {
                newModes.add(mode);
            }
        }

        if (newModes.isEmpty()) {
            Log.i(TAG, "NO MODES FOUND!! " + Arrays.asList(oldModes));
        } else {
            Log.i(TAG, "FOUND MODES! " + newModes);
        }

        return newModes;
    }

    protected DisplayHolder.Mode findCloserMode(Mode[] modes, float videoFramerate) {
        if (modes == null) {
            return null;
        }

        return findCloserMode(Arrays.asList(modes), videoFramerate);
    }

    private DisplayHolder.Mode findCloserMode(List<Mode> modes, float videoFramerate) {
        HashMap<Integer, int[]> relatedRates;

        relatedRates = getRateMapping();

        int myRate = (int) (videoFramerate * 100.0F);

        if (myRate >= 2300 && myRate <= 2399) {
            myRate = 2397;
        }

        if (relatedRates.containsKey(myRate)) {
            HashMap<Integer, DisplayHolder.Mode> rateAndMode = new HashMap<>();
            Iterator modeIterator = modes.iterator();

            while (modeIterator.hasNext()) {
                DisplayHolder.Mode mode = (DisplayHolder.Mode) modeIterator.next();
                rateAndMode.put((int) (mode.getRefreshRate() * 100.0F), mode);
            }

            int[] rates = relatedRates.get(myRate);
            int ratesLen = rates.length;

            for (int i = 0; i < ratesLen; ++i) {
                int newRate = rates[i];
                if (rateAndMode.containsKey(newRate)) {
                    return rateAndMode.get(newRate);
                }
            }
        }

        return null;
    }

    protected HashMap<Integer, int[]> getRateMapping() {
        HashMap<Integer, int[]> relatedRates = new HashMap<>();
        relatedRates.put(1500, new int[]{3000, 6000});
        relatedRates.put(2397, new int[]{2397, 2400, 3000, 6000});
        relatedRates.put(2400, new int[]{2400, 3000, 6000});
        relatedRates.put(2500, new int[]{2500, 5000});
        relatedRates.put(2997, new int[]{2997, 3000, 6000});
        relatedRates.put(3000, new int[]{3000, 6000});
        relatedRates.put(5000, new int[]{5000, 2500});
        relatedRates.put(5994, new int[]{5994, 6000, 3000});
        relatedRates.put(6000, new int[]{6000, 3000});
        return relatedRates;
    }

    /**
     * Utility method to check if device is Amazon Fire TV device
     * @return {@code true} true if device is Amazon Fire TV device.
     */
    public static boolean isAmazonFireTVDevice(){
        String deviceName = Build.MODEL;
        String manufacturerName = Build.MANUFACTURER;
        return (deviceName.startsWith("AFT")
                && "Amazon".equalsIgnoreCase(manufacturerName));
    }

    public boolean supportsDisplayModeChangeComplex() {
        if (mModeLength == -1) {
            Mode[] supportedModes = null;

            if (VERSION.SDK_INT >= 21) {
                supportedModes = getUhdHelper().getSupportedModes();
            }

            mModeLength = supportedModes == null ? 0 : supportedModes.length;
        }

        return mModeLength > 1 && supportsDisplayModeChange();
    }

    /**
     * Check whether device supports mode change. Also shows toast if no
     * @return mode change supported
     */
    public static boolean supportsDisplayModeChange() {
        boolean supportedDevice = true;

        //We fail for following conditions
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            supportedDevice = false;
        } else {
            switch (Build.VERSION.SDK_INT) {
                case Build.VERSION_CODES.LOLLIPOP:
                case Build.VERSION_CODES.LOLLIPOP_MR1:
                    if (!isAmazonFireTVDevice()) {
                        supportedDevice = false;
                    }
                    break;
            }
        }

        if (!supportedDevice) {
            Log.i(TAG, "Device doesn't support display mode change");
        }

        return supportedDevice;
    }

    public boolean displayModeSyncInProgress() {
        return mDisplaySyncInProgress;
    }

    @Override
    public void onModeChanged(DisplayHolder.Mode mode) {
        mDisplaySyncInProgress = false;

        Mode currentMode = mUhdHelper.getCurrentMode();

        if (mode == null && currentMode == null) {
            String msg = "Mode change failure. Internal error occurred.";
            Log.w(TAG, msg);

            CommonApplication.getPreferences().setCurrentDisplayMode(msg);
        } else {
            int modeId = mNewMode != null ? mNewMode.getModeId() : -1;

            if (currentMode.getModeId() != modeId) {
                // Once onDisplayChangedListener sends proper callback, the above if condition
                // need to changed to mode.getModeId() != modeId
                String msg = String.format("Mode change failure. Current mode id is %s. Expected mode id is %s", currentMode.getModeId(), modeId);
                Log.w(TAG, msg);

                String newMsg = String.format("Expected %s", UhdHelper.formatMode(mNewMode));
                CommonApplication.getPreferences().setCurrentDisplayMode(String.format("%s (%s)", UhdHelper.formatMode(currentMode), newMsg));
            } else {
                Log.i(TAG, "Mode changed successfully");

                CommonApplication.getPreferences().setCurrentDisplayMode(UhdHelper.formatMode(currentMode));
            }
        }
    }

    // switch frame rate only
    private boolean getNeedDisplaySync() {
        return true;
    }

    /**
     * Tries to find best suited display params for the video
     * @param window window object
     * @param videoWidth width of the video material
     * @param videoFramerate framerate of the video
     * @return
     */
    public boolean syncDisplayMode(Window window, int videoWidth, float videoFramerate) {
        if (supportsDisplayModeChange() && videoWidth >= 10) {
            if (mUhdHelper == null) {
                mUhdHelper = new UhdHelper(mContext);
            }

            DisplayHolder.Mode[] modes = mUhdHelper.getSupportedModes();

            Log.d(TAG, "Modes supported by device:");
            Log.d(TAG, Arrays.asList(modes));

            boolean needResolutionSwitch = false;

            List<DisplayHolder.Mode> resultModes = new ArrayList<>();

            if (SWITCH_TO_UHD) { // switch not only framerate but resolution too
                int minHeight;
                int maxHeight;

                if (videoWidth > 1920) {
                    minHeight = 2160;
                    maxHeight = 5000;
                } else {
                    minHeight = 1080;
                    maxHeight = 1080;
                }

                resultModes = filterModes(modes, minHeight, maxHeight);

                if (!resultModes.isEmpty()) {
                    needResolutionSwitch = true;
                }
            }

            Log.i(TAG, "Need resolution switch: " + needResolutionSwitch);

            DisplayHolder.Mode currentMode = mUhdHelper.getCurrentMode();

            if (!needResolutionSwitch) {
                resultModes = filterSameResolutionModes(modes, currentMode);
            }

            DisplayHolder.Mode closerMode = findCloserMode(resultModes, videoFramerate);

            if (closerMode == null) {
                String msg = "Could not find closer refresh rate for " + videoFramerate + "fps";
                Log.i(TAG, msg);
                CommonApplication.getPreferences().setCurrentDisplayMode(String.format("%s (%s)", UhdHelper.formatMode(currentMode), msg));
                return false;
            }

            Log.i(TAG, "Found closer framerate: " + closerMode.getRefreshRate() + " for fps " + videoFramerate);
            Log.i(TAG, "Current mode: " + currentMode);

            if (closerMode.equals(currentMode)) {
                Log.i(TAG, "Do not need to change mode.");
                CommonApplication.getPreferences().setCurrentDisplayMode(UhdHelper.formatMode(currentMode));
                return false;
            }
            
            mNewMode = closerMode;
            mUhdHelper.registerModeChangeListener(this);
            mUhdHelper.setPreferredDisplayModeId(window, mNewMode.getModeId(), true);
            mDisplaySyncInProgress = true;

            if (mListener != null) {
                mListener.onModeStart(mNewMode);
            }

            return true;
        }

        return false;
    }

    /**
     * Lazy init of uhd helper.<br/>
     * Convenient when user doesn't use a afr at all.
     * @return helper
     */
    protected UhdHelper getUhdHelper() {
        if (mUhdHelper == null) {
            mUhdHelper = new UhdHelper(mContext);
        }

        return mUhdHelper;
    }

    public void saveOriginalState() {
        saveState(STATE_ORIGINAL);
    }

    public void saveCurrentState() {
        saveState(STATE_CURRENT);
    }

    public boolean restoreOriginalState(Window window) {
        return restoreState(window, STATE_ORIGINAL);
    }

    public boolean restoreCurrentState(Window window) {
        return restoreState(window, STATE_CURRENT);
    }

    private void saveState(int state) {
        DisplayHolder.Mode mode = getUhdHelper().getCurrentMode();

        if (mode != null) {
            switch (state) {
                case STATE_ORIGINAL:
                    mOriginalMode = mode;
                    CommonApplication.getPreferences().setDefaultDisplayMode(UhdHelper.formatMode(mode));
                    break;
                case STATE_CURRENT:
                    mCurrentMode = mode;
                    break;
            }
        }
    }

    private boolean restoreState(Window window, int state) {
        DisplayHolder.Mode modeTmp = null;

        switch (state) {
            case STATE_ORIGINAL:
                modeTmp = mOriginalMode;
                break;
            case STATE_CURRENT:
                modeTmp = mCurrentMode;
                break;
        }

        if (modeTmp == null) {
            return false;
        }

        DisplayHolder.Mode mode = getUhdHelper().getCurrentMode();

        if (modeTmp.equals(mode)) {
            Log.i(TAG, "Do not need to restore mode.");
            return false;
        }

        getUhdHelper().setPreferredDisplayModeId(
                window,
                modeTmp.getModeId(),
                true);

        return true;
    }

    public void resetStats() {
        mModeLength = -1;
    }

    public void setListener(AutoFrameRateListener listener) {
        mListener = listener;
    }

    /**
     * Set default mode to 1920x1080@50<br/>
     * Because switch not work with some devices running at 60HZ. Like: UGOOS
     */
    public void applyModeChangeFix(Window window) {
        if (mOriginalMode != null) {
            if (mOriginalMode.getRefreshRate() > 55) {
                setDefaultMode(window, mOriginalMode.getPhysicalWidth(), 50);
            } else {
                setDefaultMode(window, mOriginalMode.getPhysicalWidth(), 60);
            }
        } else {
            setDefaultMode(window, 1080, 50);
        }
    }

    private void setDefaultMode(Window window, int width, float frameRate) {
        syncDisplayMode(window, width, frameRate);

        if (mNewMode != null) {
            mOriginalMode = mNewMode;
            CommonApplication.getPreferences().setDefaultDisplayMode(UhdHelper.formatMode(mOriginalMode));
        }
    }
}
