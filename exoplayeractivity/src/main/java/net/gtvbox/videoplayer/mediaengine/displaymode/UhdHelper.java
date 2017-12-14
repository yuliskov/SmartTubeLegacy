package net.gtvbox.videoplayer.mediaengine.displaymode;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Display.Mode;
import android.view.Window;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

public class UhdHelper {
   public static final int HEIGHT_UHD = 2160;
   private static final int INTERSTITIAL_FADED_BROADCAST_MSG = 4;
   private static final int INTERSTITIAL_TIMEOUT_MSG = 5;
   public static final String MODESWITCH_OVERLAY_DISABLE = "com.amazon.tv.notification.modeswitch_overlay.action.DISABLE";
   public static final String MODESWITCH_OVERLAY_ENABLE = "com.amazon.tv.notification.modeswitch_overlay.action.ENABLE";
   public static final String MODESWITCH_OVERLAY_EXTRA_STATE = "com.amazon.tv.notification.modeswitch_overlay.extra.STATE";
   public static final String MODESWITCH_OVERLAY_STATE_CHANGED = "com.amazon.tv.notification.modeswitch_overlay.action.STATE_CHANGED";
   private static final int MODE_CHANGED_MSG = 1;
   private static final int MODE_CHANGE_TIMEOUT_MSG = 2;
   public static final int OVERLAY_STATE_DISMISSED = 0;
   private static final int SEND_CALLBACK_WITH_SUPPLIED_RESULT = 3;
   public static final int SET_MODE_TIMEOUT_DELAY_MS = 15000;
   public static final int SHOW_INTERSTITIAL_TIMEOUT_DELAY_MS = 2000;
   private static final String TAG = UhdHelper.class.getSimpleName();
   public static final String version = "v1.1";
   private int currentOverlayStatus;
   private boolean isInterstitialFadeReceived = false;
   boolean isReceiversRegistered;
   Context mContext;
   DisplayListener mDisplayListener;
   DisplayManager mDisplayManager;
   private Display mInternalDisplay;
   private AtomicBoolean mIsSetModeInProgress;
   private UhdHelperListener mListener;
   private Window mTargetWindow;
   private UhdHelper.WorkHandler mWorkHandler;
   private UhdHelper.OverlayStateChangeReceiver overlayStateChangeReceiver;
   private String sDisplayClassName = "android.view.Display";
   private String sGetModeIdMethodName = "getModeId";
   private String sGetModeMethodName = "getMode";
   private String sGetPhysicalHeightMethodName = "getPhysicalHeight";
   private String sGetPhysicalWidthMethodName = "getPhysicalWidth";
   private String sGetRefreshRateMethodName = "getRefreshRate";
   private String sPreferredDisplayModeIdFieldName = "preferredDisplayModeId";
   private String sSupportedModesMethodName = "getSupportedModes";
   private boolean showInterstitial = false;

   public UhdHelper(Context var1) {
      this.mContext = var1;
      this.mInternalDisplay = new Display();
      this.mIsSetModeInProgress = new AtomicBoolean(false);
      this.mWorkHandler = new UhdHelper.WorkHandler(Looper.getMainLooper());
      this.overlayStateChangeReceiver = new UhdHelper.OverlayStateChangeReceiver();
      this.mDisplayManager = (DisplayManager)this.mContext.getSystemService("display");
      this.isReceiversRegistered = false;
   }

   private Display.Mode convertReturnedModeToInternalMode(Object var1) {
      try {
         Class var6 = var1.getClass();
         int var3 = (Integer)var6.getDeclaredMethod(this.sGetModeIdMethodName).invoke(var1);
         int var4 = (Integer)var6.getDeclaredMethod(this.sGetPhysicalWidthMethodName).invoke(var1);
         int var5 = (Integer)var6.getDeclaredMethod(this.sGetPhysicalHeightMethodName).invoke(var1);
         float var2 = (Float)var6.getDeclaredMethod(this.sGetRefreshRateMethodName).invoke(var1);
         Display.Mode var8 = this.mInternalDisplay.getModeInstance(var3, var4, var5, var2);
         return var8;
      } catch (Exception var7) {
         Log.e(TAG, "error converting", var7);
         return null;
      }
   }

   @TargetApi(17)
   private android.view.Display getCurrentDisplay() {
      if (this.mContext == null) {
         return null;
      } else {
         android.view.Display[] var1 = this.mDisplayManager.getDisplays();
         if (var1 != null && var1.length != 0) {
            return var1[0];
         } else {
            Log.e(TAG, "ERROR on device to get the display");
            return null;
         }
      }
   }

   private void hideOptimizingOverlay() {
      Intent var1 = new Intent("com.amazon.tv.notification.modeswitch_overlay.action.DISABLE");
      this.mContext.sendBroadcast(var1);
      Log.i(TAG, "Sending the broadcast to hide display overlay");
   }

   private void initModeChange(int param1, Field param2) {
      // $FF: Couldn't be decompiled
   }

   private boolean isAmazonFireTVDevice() {
      String var1 = Build.MODEL;
      String var2 = Build.MANUFACTURER;
      return var1.startsWith("AFT") && "Amazon".equalsIgnoreCase(var2);
   }

   private void showOptimizingOverlay() {
      Intent var1 = new Intent("com.amazon.tv.notification.modeswitch_overlay.action.ENABLE");
      this.mContext.sendBroadcast(var1);
      Log.i(TAG, "Sending the broadcast to display overlay");
   }

   public Display.Mode getMode() {
      android.view.Display var1 = this.getCurrentDisplay();
      if (var1 == null) {
         return null;
      } else {
         try {
            Display.Mode var3 = this.convertReturnedModeToInternalMode(Class.forName(this.sDisplayClassName).getDeclaredMethod(this.sGetModeMethodName, (Class[])null).invoke(var1, (Object[])null));
            return var3;
         } catch (Exception var2) {
            Log.e(TAG, var2.getLocalizedMessage());
            Log.e(TAG, "Current Mode is not present in supported Modes");
            return null;
         }
      }
   }

   public Display.Mode[] getSupportedModes() {
       Display.Mode[] newModes;
       try {
           Class<?> aDisplay = Class.forName(sDisplayClassName);
           Method aSupportedModes = aDisplay.getDeclaredMethod(sSupportedModesMethodName);
           Mode[] oldModes = (Mode[]) aSupportedModes.invoke(getCurrentDisplay());

           newModes = new Display.Mode[oldModes.length];

           for (int i = 0; i < oldModes.length; i++) {
               newModes[i] = convertReturnedModeToInternalMode(oldModes[i]);
           }
       } catch (Exception e) {
           throw new IllegalStateException(e);
       }

       return newModes;

       // $FF: Couldn't be decompiled
   }

   public void registerModeChangeListener(UhdHelperListener var1) {
      this.mListener = var1;
   }

   @TargetApi(17)
   public void setPreferredDisplayModeId(Window var1, int var2, boolean var3) {
      String var10 = Build.MODEL;
      boolean var4 = true;
      this.mWorkHandler.setCallbackListener(this.mListener);
      if (VERSION.SDK_INT < 21) {
         var4 = false;
      } else {
         switch(VERSION.SDK_INT) {
         case 21:
         case 22:
            if (!this.isAmazonFireTVDevice()) {
               var4 = false;
            }
         }
      }

      if (!var4) {
         Log.i(TAG, "Attempt to set preferred Display mode on an unsupported device: " + var10);
         this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, (Object)null));
      } else {
         if (!this.isAmazonFireTVDevice()) {
            var3 = false;
         }

         if (this.mIsSetModeInProgress.get()) {
            Log.e(TAG, "setPreferredDisplayModeId is already in progress! Cannot set another while it is in progress");
            this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, (Object)null));
         } else {
            Display.Mode var15 = this.getMode();
            if (var15 != null && var15.getModeId() != var2) {
               Display.Mode[] var16 = this.getSupportedModes();
               boolean var7 = false;
               boolean var8 = false;
               int var9 = var16.length;
               int var5 = 0;

               boolean var6;
               while(true) {
                  var6 = var7;
                  var4 = var8;
                  if (var5 >= var9) {
                     break;
                  }

                  Display.Mode var11 = var16[var5];
                  if (var11.getModeId() == var2) {
                     if (var11.getPhysicalHeight() >= 2160) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     var6 = true;
                     break;
                  }

                  ++var5;
               }

               if (!var6) {
                  Log.e(TAG, "Requested mode id not among the supported Mode Id.");
                  this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, (Object)null));
               } else {
                  this.mIsSetModeInProgress.set(true);
                  this.mWorkHandler.setExpectedMode(var2);
                  this.mContext.registerReceiver(this.overlayStateChangeReceiver, new IntentFilter("com.amazon.tv.notification.modeswitch_overlay.action.STATE_CHANGED"));
                  this.mDisplayListener = new DisplayListener() {
                     public void onDisplayAdded(int var1) {
                     }

                     public void onDisplayChanged(int var1) {
                        Log.i(UhdHelper.TAG, "onDisplayChanged. id= " + var1 + " " + UhdHelper.this.mDisplayManager.getDisplay(var1).toString());
                        UhdHelper.this.mWorkHandler.obtainMessage(1).sendToTarget();
                     }

                     public void onDisplayRemoved(int var1) {
                     }
                  };
                  this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mWorkHandler);
                  this.isReceiversRegistered = true;
                  this.mTargetWindow = var1;
                  if (var3 && var4) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  this.showInterstitial = var3;
                  Class var13 = this.mTargetWindow.getAttributes().getClass();

                  Field var14;
                  try {
                     var14 = var13.getDeclaredField(this.sPreferredDisplayModeIdFieldName);
                  } catch (Exception var12) {
                     Log.e(TAG, var12.getLocalizedMessage());
                     this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, (Object)null));
                     return;
                  }

                  if (this.showInterstitial) {
                     this.isInterstitialFadeReceived = false;
                     this.showOptimizingOverlay();
                     this.mWorkHandler.sendMessageDelayed(this.mWorkHandler.obtainMessage(5), 2000L);
                  } else {
                     this.initModeChange(var2, var14);
                  }
               }
            } else {
               Log.i(TAG, "Current mode id same as mode id requested or is Null. Aborting.");
               this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, var15));
            }
         }
      }
   }

   public void unregisterDisplayModeChangeListener(UhdHelperListener var1) {
      this.mListener = null;
   }

   private class OverlayStateChangeReceiver extends BroadcastReceiver {
      private final int OVERLAY_FADE_COMPLETE_EXTRA;

      private OverlayStateChangeReceiver() {
         this.OVERLAY_FADE_COMPLETE_EXTRA = 3;
      }

      public void onReceive(Context var1, Intent var2) {
         UhdHelper.this.currentOverlayStatus = var2.getIntExtra("com.amazon.tv.notification.modeswitch_overlay.extra.STATE", -1);
         if (UhdHelper.this.currentOverlayStatus == 3 && !UhdHelper.this.isInterstitialFadeReceived) {
            UhdHelper.this.mWorkHandler.removeMessages(5);
            UhdHelper.this.mWorkHandler.sendMessage(UhdHelper.this.mWorkHandler.obtainMessage(4));
            Log.i(UhdHelper.TAG, "Got the Interstitial text fade broadcast, Starting the mode change");
         }

      }
   }

   private class WorkHandler extends Handler {
      private UhdHelperListener mCallbackListener;
      private int mRequestedModeId;

      public WorkHandler(Looper var2) {
         super(var2);
      }

      private void doPostModeSetCleanup() {
         // $FF: Couldn't be decompiled
      }

      private void maybeDoACallback(Display.Mode var1) {
         if (this.mCallbackListener != null) {
            Log.d(UhdHelper.TAG, "Sending callback to listener");
            this.mCallbackListener.onModeChanged(var1);
         } else {
            Log.d(UhdHelper.TAG, "Can't issue callback as no listener registered");
         }
      }

      private void setCallbackListener(UhdHelperListener var1) {
         this.mCallbackListener = var1;
      }

      public void handleMessage(Message var1) {
         switch(var1.what) {
         case 1:
            Display.Mode var2 = UhdHelper.this.getMode();
            if (var2 == null) {
               Log.w(UhdHelper.TAG, "Mode query returned null after onDisplayChanged callback");
               return;
            }

            if (var2.getModeId() == this.mRequestedModeId) {
               Log.i(UhdHelper.TAG, "Callback for expected change Id= " + this.mRequestedModeId);
               this.maybeDoACallback(var2);
               this.doPostModeSetCleanup();
               return;
            }

            Log.w(UhdHelper.TAG, "Callback received but not expected mode. Mode= " + var2 + " expected= " + this.mRequestedModeId);
            return;
         case 2:
            Log.i(UhdHelper.TAG, "Time out without mode change");
            this.maybeDoACallback((Display.Mode)null);
            this.doPostModeSetCleanup();
            return;
         case 3:
            this.maybeDoACallback((Display.Mode)var1.obj);
            if (var1.arg1 == 1) {
               this.doPostModeSetCleanup();
               return;
            }
            break;
         case 4:
            if (!UhdHelper.this.isInterstitialFadeReceived) {
               Log.i(UhdHelper.TAG, "Broadcast for text fade received, Initializing the mode change.");
               UhdHelper.this.isInterstitialFadeReceived = true;
               UhdHelper.this.initModeChange(this.mRequestedModeId, (Field)null);
               return;
            }
            break;
         case 5:
            if (!UhdHelper.this.isInterstitialFadeReceived) {
               Log.w(UhdHelper.TAG, "Didn't received any broadcast for interstitial text fade till time out, starting the mode change.");
               UhdHelper.this.isInterstitialFadeReceived = true;
               UhdHelper.this.initModeChange(this.mRequestedModeId, (Field)null);
               return;
            }
         }

      }

      public void setExpectedMode(int var1) {
         this.mRequestedModeId = var1;
      }
   }
}
