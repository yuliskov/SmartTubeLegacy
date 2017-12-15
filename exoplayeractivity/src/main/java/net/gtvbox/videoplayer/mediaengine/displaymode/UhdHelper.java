package net.gtvbox.videoplayer.mediaengine.displaymode;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display.Mode;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

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
   private boolean isReceiversRegistered;
   private final Context mContext;
   private DisplayListener mDisplayListener;
   private DisplayManager mDisplayManager;
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

   public UhdHelper(Context context) {
      mContext = context;
      mInternalDisplay = new Display();
      mIsSetModeInProgress = new AtomicBoolean(false);
      mWorkHandler = new UhdHelper.WorkHandler(Looper.getMainLooper());
      overlayStateChangeReceiver = new UhdHelper.OverlayStateChangeReceiver();
      mDisplayManager = (DisplayManager) mContext.getSystemService("display");
      isReceiversRegistered = false;
   }

   private Display.Mode convertReturnedModeToInternalMode(Object mode) {
      try {
         Class<?> aMode = mode.getClass();
         int id = (Integer)aMode.getDeclaredMethod(sGetModeIdMethodName).invoke(mode);
         int width = (Integer)aMode.getDeclaredMethod(sGetPhysicalWidthMethodName).invoke(mode);
         int height = (Integer)aMode.getDeclaredMethod(sGetPhysicalHeightMethodName).invoke(mode);
         float rate = (Float)aMode.getDeclaredMethod(sGetRefreshRateMethodName).invoke(mode);
         Display.Mode newMode = this.mInternalDisplay.getModeInstance(id, width, height, rate);
         return newMode;
      } catch (Exception ex) {
         Log.e(TAG, "error converting", ex);
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

   private void initModeChange(int modeId, Field attributeFlagField) {
       LayoutParams params = mTargetWindow.getAttributes();

       try {
           if (attributeFlagField == null) {
               Class<? extends LayoutParams> aLayoutParams = params.getClass();
               attributeFlagField = aLayoutParams.getDeclaredField(sPreferredDisplayModeIdFieldName);
           }

           attributeFlagField.setInt(params, modeId);
           mTargetWindow.setAttributes(params);
       } catch (Exception e) {
           Message message2 = mWorkHandler.obtainMessage(0x3, 1,1);
           mWorkHandler.sendMessage(message2);

           throw new IllegalStateException(e);
       }

       Message message = mWorkHandler.obtainMessage(0x2);
       mWorkHandler.sendMessageDelayed(message, 0x3a98);


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
   public void setPreferredDisplayModeId(Window targetWindow, int modeId, boolean allowOverlayDisplay) {
      String model = Build.MODEL;
      boolean deviceSupported = true;
      this.mWorkHandler.setCallbackListener(this.mListener);
      if (VERSION.SDK_INT < 21) {
         deviceSupported = false;
      } else {
         switch(VERSION.SDK_INT) {
         case 21:
         case 22:
            if (!this.isAmazonFireTVDevice()) {
               deviceSupported = false;
            }
         }
      }

      if (!deviceSupported) {
         Log.i(TAG, "Attempt to set preferred Display mode on an unsupported device: " + model);
         this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, (Object)null));
      } else {
         if (!this.isAmazonFireTVDevice()) {
            allowOverlayDisplay = false;
         }

         if (this.mIsSetModeInProgress.get()) {
            Log.e(TAG, "setPreferredDisplayModeId is already in progress! Cannot set another while it is in progress");
            this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, (Object)null));
         } else {
            Display.Mode currentMode = this.getMode();
            if (currentMode != null && currentMode.getModeId() != modeId) {
               Display.Mode[] supportedModes = this.getSupportedModes();
               int supportedModesLen = supportedModes.length;
               int idx = 0;

               boolean modeIdSupported;
               while(true) {
                  modeIdSupported = false;
                  deviceSupported = false;
                  if (idx >= supportedModesLen) {
                     break;
                  }

                  Display.Mode mode = supportedModes[idx];
                  if (mode.getModeId() == modeId) {
                     if (mode.getPhysicalHeight() >= 2160) {
                        deviceSupported = true;
                     } else {
                        deviceSupported = false;
                     }

                     modeIdSupported = true;
                     break;
                  }

                  ++idx;
               }

               if (!modeIdSupported) {
                  Log.e(TAG, "Requested mode id not among the supported Mode Id.");
                  this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, (Object)null));
               } else {
                  this.mIsSetModeInProgress.set(true);
                  this.mWorkHandler.setExpectedMode(modeId);
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
                  this.mTargetWindow = targetWindow;
                  if (allowOverlayDisplay && deviceSupported) {
                     allowOverlayDisplay = true;
                  } else {
                     allowOverlayDisplay = false;
                  }

                  this.showInterstitial = allowOverlayDisplay;
                  Class aLayoutParams = this.mTargetWindow.getAttributes().getClass();

                  Field aPreferredDisplayModeId;
                  try {
                     aPreferredDisplayModeId = aLayoutParams.getDeclaredField(this.sPreferredDisplayModeIdFieldName);
                  } catch (Exception ex) {
                     Log.e(TAG, ex.getLocalizedMessage());
                     this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, (Object)null));
                     return;
                  }

                  if (this.showInterstitial) {
                     this.isInterstitialFadeReceived = false;
                     this.showOptimizingOverlay();
                     this.mWorkHandler.sendMessageDelayed(this.mWorkHandler.obtainMessage(5), 2000L);
                  } else {
                     this.initModeChange(modeId, aPreferredDisplayModeId);
                  }
               }
            } else {
               Log.i(TAG, "Current mode id same as mode id requested or is Null. Aborting.");
               this.mWorkHandler.sendMessage(this.mWorkHandler.obtainMessage(3, 1, 1, currentMode));
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

      @TargetApi(17)
      private void doPostModeSetCleanup() {
         mWorkHandler.removeMessages(0x2);
          if (isReceiversRegistered) {
              mDisplayManager.unregisterDisplayListener(mDisplayListener);
              mContext.unregisterReceiver(overlayStateChangeReceiver);
              isReceiversRegistered = false;
          }

          mWorkHandler.removeMessages(0x1);
          mWorkHandler.mCallbackListener = null;
          mIsSetModeInProgress.set(false);

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
