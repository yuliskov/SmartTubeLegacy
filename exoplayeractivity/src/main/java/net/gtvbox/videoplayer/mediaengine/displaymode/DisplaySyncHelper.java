package net.gtvbox.videoplayer.mediaengine.displaymode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DisplaySyncHelper implements UhdHelperListener {
    static final String TAG = "DisplaySyncHelper";
    private final Context mContext;
    private boolean mDisplaySyncInProgress = false;
    private final boolean mNeedDisplaySync;
    private boolean mSwitchToUHD;

    public DisplaySyncHelper(Context context) {
        mContext = context;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mNeedDisplaySync = prefs.getBoolean("display_rate_switch", false);
        //mSwitchToUHD = prefs.getBoolean("switch_to_uhd", false);
        // NOTE: switch not only framerate but resolution too
        mSwitchToUHD = mNeedDisplaySync;
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

    private boolean supportsDisplayModeChange() {
        boolean result = true;
        if (VERSION.SDK_INT < 21) {
            result = false;
        } else {
            switch (VERSION.SDK_INT) {
                case 21:
                case 22:
                    String var2 = Build.MODEL;
                    String var3 = Build.MANUFACTURER;
                    if (!var2.startsWith("AFT") || !"Amazon".equalsIgnoreCase(var3)) {
                        return false;
                    }
                    break;
                default:
                    return true;
            }
        }

        return result;
    }

    public boolean displayModeSyncInProgress() {
        return mDisplaySyncInProgress;
    }

    public void onModeChanged(Display.Mode var1) {
        mDisplaySyncInProgress = false;
    }

    public boolean syncDisplayMode(Window window, int videoWidth, float videoFramerate) {
        if (!mNeedDisplaySync && !mSwitchToUHD) { // none of the vars is set to true
            return false;
        }

        if (supportsDisplayModeChange() && videoWidth >= 10) {
            UhdHelper uhdHelper = new UhdHelper(mContext);
            Display.Mode[] modes = uhdHelper.getSupportedModes();
            boolean isUHD = false;
            ArrayList<Display.Mode> resultModes = new ArrayList<>();
            if (mSwitchToUHD) {
                if (videoWidth > 1920) {
                    resultModes = filterUHDModes(modes);
                    if (!resultModes.isEmpty()) {
                        isUHD = true;
                    }
                }
            }

            if (mNeedDisplaySync || isUHD) {
                Log.i("DisplaySyncHelper", "Need refresh rate adapt: " + mNeedDisplaySync + " Need UHD switch: " + isUHD);
                Display.Mode mode = uhdHelper.getMode();
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

                uhdHelper.registerModeChangeListener(this);
                uhdHelper.setPreferredDisplayModeId(window, closerMode.getModeId(), true);
                mDisplaySyncInProgress = true;
                return true;
            }
        }

        return false;
    }
}
