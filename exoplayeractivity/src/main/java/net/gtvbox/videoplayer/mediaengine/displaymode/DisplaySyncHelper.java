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
   Context mContext;
   private boolean mDisplaySyncInProgress = false;
   private final boolean mNeedDisplaySync;
   private boolean mSwitchToUHD;

   public DisplaySyncHelper(Context var1) {
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(var1);
      this.mNeedDisplaySync = prefs.getBoolean("display_rate_switch", true);
      this.mSwitchToUHD = prefs.getBoolean("switch_to_uhd", true);
   }

   private ArrayList<Display.Mode> filterSameResolutionModes(Display.Mode[] oldModes, Display.Mode currentMode) {
      ArrayList<Display.Mode> newModes = new ArrayList<>();
      int oldModesLen = oldModes.length;

      for(int i = 0; i < oldModesLen; ++i) {
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

      for(int i = 0; i < modesNum; ++i) {
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

   private Display.Mode findCloserMode(ArrayList<Display.Mode> var1, float var2) {
      HashMap var7 = new HashMap();
      var7.put(1500, new int[]{6000, 3000});
      var7.put(2397, new int[]{2397, 2400, 6000, 3000});
      var7.put(2400, new int[]{2400, 6000, 3000});
      var7.put(2500, new int[]{5000, 2500});
      var7.put(2997, new int[]{2997, 6000, 3000});
      var7.put(3000, new int[]{6000, 3000});
      var7.put(5000, new int[]{5000, 2500});
      var7.put(5994, new int[]{5994, 6000, 3000});
      var7.put(6000, new int[]{6000, 3000});
      int var4 = (int)(var2 * 100.0F);
      int var3 = var4;
      if (var4 >= 2300) {
         var3 = var4;
         if (var4 <= 2399) {
            var3 = 2397;
         }
      }

      if (var7.containsKey(var3)) {
         HashMap var6 = new HashMap();
         Iterator var9 = var1.iterator();

         while(var9.hasNext()) {
            Display.Mode var8 = (Display.Mode)var9.next();
            var6.put((int)(var8.getRefreshRate() * 100.0F), var8);
         }

         int[] var10 = (int[])var7.get(var3);
         var4 = var10.length;

         for(var3 = 0; var3 < var4; ++var3) {
            int var5 = var10[var3];
            if (var6.containsKey(var5)) {
               return (Display.Mode)var6.get(var5);
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
         switch(VERSION.SDK_INT) {
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
      return this.mDisplaySyncInProgress;
   }

   public void onModeChanged(Display.Mode var1) {
      this.mDisplaySyncInProgress = false;
   }

   public boolean syncDisplayMode(Window window, int videoWidth, float videoFramerate) {
      if (this.supportsDisplayModeChange() && videoWidth >= 10) {
         UhdHelper uhdHelper = new UhdHelper(this.mContext);
         Display.Mode[] modes = uhdHelper.getSupportedModes();
         boolean isUHD = false;
         ArrayList<Display.Mode> resultModes = new ArrayList<>();
         if (this.mSwitchToUHD) {
            if (videoWidth > 1920) {
               resultModes = this.filterUHDModes(modes);
               if (!resultModes.isEmpty()) {
                  isUHD = true;
               }
            }
         }

         if (this.mNeedDisplaySync || isUHD) {
            Log.i("DisplaySyncHelper", "Need refresh rate adapt: " + this.mNeedDisplaySync + " Need UHD switch: " + isUHD);
            Display.Mode mode = uhdHelper.getMode();
            if (!isUHD) {
               resultModes = this.filterSameResolutionModes(modes, mode);
            }

            Display.Mode closerMode = this.findCloserMode(resultModes, videoFramerate);
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
            this.mDisplaySyncInProgress = true;
            return true;
         }
      }

      return false;
   }
}
