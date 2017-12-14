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

public class DisplaySyncHelper implements UhdHelperListener {
   static final String TAG = "DisplaySyncHelper";
   Context mContext;
   private boolean mDisplaySyncInProgress = false;
   private final boolean mNeedDisplaySync;
   private boolean mSwitchToUHD;

   public DisplaySyncHelper(Context var1) {
      SharedPreferences var2 = PreferenceManager.getDefaultSharedPreferences(var1);
      this.mNeedDisplaySync = var2.getBoolean("display_rate_switch", false);
      this.mSwitchToUHD = var2.getBoolean("switch_to_uhd", true);
   }

   private ArrayList<Display.Mode> filterSameResolutionModes(Display.Mode[] var1, Display.Mode var2) {
      ArrayList var5 = new ArrayList();
      int var4 = var1.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Display.Mode var6 = var1[var3];
         if (var6.getPhysicalHeight() == var2.getPhysicalHeight() && var6.getPhysicalWidth() == var2.getPhysicalWidth()) {
            var5.add(var6);
         }
      }

      return var5;
   }

   private ArrayList<Display.Mode> filterUHDModes(Display.Mode[] var1) {
      ArrayList var4 = new ArrayList();
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Display.Mode var5 = var1[var2];
         Log.i("DisplaySyncHelper", "Check fo UHD: " + var5.getPhysicalWidth() + "x" + var5.getPhysicalHeight() + "@" + var5.getRefreshRate());
         if (var5.getPhysicalHeight() >= 2160) {
            Log.i("DisplaySyncHelper", "Found! UHD");
            var4.add(var5);
         }
      }

      if (var4.isEmpty()) {
         Log.i("DisplaySyncHelper", "NO UHD MODES FOUND!!");
      }

      return var4;
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
      boolean var1 = true;
      if (VERSION.SDK_INT < 21) {
         var1 = false;
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

      return var1;
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
         ArrayList var7 = new ArrayList();
         boolean var5 = false;
         boolean var4 = var5;
         ArrayList var6 = var7;
         if (this.mSwitchToUHD) {
            var4 = var5;
            var6 = var7;
            if (videoWidth > 1920) {
               var7 = this.filterUHDModes(modes);
               var4 = var5;
               var6 = var7;
               if (!var7.isEmpty()) {
                  var4 = true;
                  var6 = var7;
               }
            }
         }

         if (this.mNeedDisplaySync || var4) {
            Log.i("DisplaySyncHelper", "Need refresh rate adapt: " + this.mNeedDisplaySync + " Need UHD switch: " + var4);
            Display.Mode var10 = uhdHelper.getMode();
            if (!var4) {
               var6 = this.filterSameResolutionModes(modes, var10);
            }

            Display.Mode var11 = this.findCloserMode(var6, videoFramerate);
            if (var11 == null) {
               Log.i("DisplaySyncHelper", "Could not find closer refresh rate for " + videoFramerate + "fps");
               return false;
            }

            Log.i("DisplaySyncHelper", "Found closer framerate: " + var11.getRefreshRate() + " for fps " + videoFramerate);
            if (var11.equals(var10)) {
               Log.i("DisplaySyncHelper", "Do not need to change mode.");
               return false;
            }

            uhdHelper.registerModeChangeListener(this);
            uhdHelper.setPreferredDisplayModeId(window, var11.getModeId(), true);
            this.mDisplaySyncInProgress = true;
            return true;
         }
      }

      return false;
   }
}
