package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// Source: https://developer.amazon.com/docs/fire-tv/4k-apis-for-hdmi-mode-switch.html#amazonextension

public class DisplaySyncHelper implements UhdHelperListener {
    static final String TAG = "DisplaySyncHelper";
    private final Context mContext;
    private boolean mDisplaySyncInProgress = false;
    private UhdHelper mUhdHelper;
    private int mNewMode;

    public DisplaySyncHelper(Context context) {
        mContext = context;
    }

    private ArrayList<Display.Mode> filterSameResolutionModes(Display.Mode[] oldModes, Display.Mode currentMode) {
        ArrayList<Display.Mode> newModes = new ArrayList<>();
        int oldModesLen = oldModes.length;

        for (int i = 0; i < oldModesLen; ++i) {
            Display.Mode mode = oldModes[i];
            if (mode.getPhysicalHeight() == currentMode.getPhysicalHeight() && mode.getPhysicalWidth() == currentMode.getPhysicalWidth()) {
                newModes.add(mode);
            }
        }

        return newModes;
    }

    private ArrayList<Display.Mode> filterUHDModes(Display.Mode[] oldModes) {
        ArrayList<Display.Mode> newModes = new ArrayList<>();
        int modesNum = oldModes.length;

        for (int i = 0; i < modesNum; ++i) {
            Display.Mode mode = oldModes[i];
            Log.i("DisplaySyncHelper", "Check fo UHD: " + mode.getPhysicalWidth() + "x" + mode.getPhysicalHeight() + "@" + mode.getRefreshRate());
            if (mode.getPhysicalHeight() >= 2160) {
                Log.i("DisplaySyncHelper", "Found! UHD");
                newModes.add(mode);
            }
        }

        if (newModes.isEmpty()) {
            Log.i("DisplaySyncHelper", "NO UHD MODES FOUND!!");
        }

        return newModes;
    }

    private Display.Mode findCloserMode(ArrayList<Display.Mode> modes, float rate) {
        HashMap<Integer, int[]> relatedRates = new HashMap<>();
        relatedRates.put(1500, new int[]{6000, 3000});
        relatedRates.put(2397, new int[]{2397, 2400, 6000, 3000});
        relatedRates.put(2400, new int[]{2400, 6000, 3000});
        relatedRates.put(2500, new int[]{5000, 2500});
        relatedRates.put(2997, new int[]{2997, 6000, 3000});
        relatedRates.put(3000, new int[]{6000, 3000});
        relatedRates.put(5000, new int[]{5000, 2500});
        relatedRates.put(5994, new int[]{5994, 6000, 3000});
        relatedRates.put(6000, new int[]{6000, 3000});

        int myRate = (int) (rate * 100.0F);
        if (myRate >= 2300 && myRate <= 2399) {
            myRate = 2397;
        }

        if (relatedRates.containsKey(myRate)) {
            HashMap<Integer, Display.Mode> rateAndMode = new HashMap<>();
            Iterator modeIterator = modes.iterator();

            while (modeIterator.hasNext()) {
                Display.Mode mode = (Display.Mode) modeIterator.next();
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

    /**
     * Utility method to check if device is Amazon Fire TV device
     * @return {@code true} true if device is Amazon Fire TV device.
     */
    private boolean isAmazonFireTVDevice(){
        String deviceName = Build.MODEL;
        String manufacturerName = Build.MANUFACTURER;
        return (deviceName.startsWith("AFT")
                && "Amazon".equalsIgnoreCase(manufacturerName));
    }

    private boolean supportsDisplayModeChange() {
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
            Toast.makeText(mContext, R.string.autoframerate_not_supported, Toast.LENGTH_LONG).show();
        }

        return supportedDevice;
    }

    public boolean displayModeSyncInProgress() {
        return mDisplaySyncInProgress;
    }

    @Override
    public void onModeChanged(Display.Mode mode) {
        mDisplaySyncInProgress = false;

        if (mode == null && mUhdHelper.getMode() == null) {
            String msg = "Mode changed Failure, Internal error occurred.";
            Log.w(TAG, msg);
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        } else if (mUhdHelper.getMode().getModeId() != mNewMode) {
            // Once onDisplayChangedListener sends proper callback, the above if condition
            // need to changed to mode.getModeId() != modeId
            String msg = "Mode changed Failure, Current mode id is " + mUhdHelper.getMode().getModeId();
            Log.w(TAG, msg);
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
        else {
            Log.i(TAG, "Mode changed Success");
        }
    }

    public void setNeedDisplaySync(boolean enabled) {
        ExoPreferences prefs = ExoPreferences.instance(mContext);
        prefs.setAutoframerateChecked(enabled);
    }

    public boolean getNeedDisplaySync() {
        ExoPreferences prefs = ExoPreferences.instance(mContext);
        return prefs.getAutoframerateChecked();
    }

    // NOTE: switch not only framerate but resolution too
    private boolean getSwitchToUHD() {
        return getNeedDisplaySync();
    }

    public boolean syncDisplayMode(Window window, int videoWidth, float videoFramerate) {
        if (!getNeedDisplaySync() && !getSwitchToUHD()) { // none of the vars is set to true
            return false;
        }

        if (supportsDisplayModeChange() && videoWidth >= 10) {
            if (mUhdHelper == null) {
                mUhdHelper = new UhdHelper(mContext);
            }

            Display.Mode[] modes = mUhdHelper.getSupportedModes();
            boolean isUHD = false;
            ArrayList<Display.Mode> resultModes = new ArrayList<>();
            if (getSwitchToUHD()) {
                if (videoWidth > 1920) {
                    resultModes = filterUHDModes(modes);
                    if (!resultModes.isEmpty()) {
                        isUHD = true;
                    }
                }
            }

            if (getNeedDisplaySync() || isUHD) {
                Log.i("DisplaySyncHelper", "Need refresh rate adapt: " + getNeedDisplaySync() + " Need UHD switch: " + isUHD);
                Display.Mode mode = mUhdHelper.getMode();
                if (!isUHD) {
                    resultModes = filterSameResolutionModes(modes, mode);
                }

                Display.Mode closerMode = findCloserMode(resultModes, videoFramerate);
                if (closerMode == null) {
                    Log.i("DisplaySyncHelper", "Could not find closer refresh rate for " + videoFramerate + "fps");
                    return false;
                }

                Log.i("DisplaySyncHelper", "Found closer framerate: " + closerMode.getRefreshRate() + " for fps " + videoFramerate);
                if (closerMode.equals(mode)) {
                    Log.i("DisplaySyncHelper", "Do not need to change mode.");
                    return false;
                }

                mUhdHelper.registerModeChangeListener(this);
                mNewMode = closerMode.getModeId();
                mUhdHelper.setPreferredDisplayModeId(window, mNewMode, true);
                mDisplaySyncInProgress = true;
                return true;
            }
        }

        return false;
    }

    public int getCurrentModeId() {
        return mNewMode;
    }
}
