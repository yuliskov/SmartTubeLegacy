package net.gtvbox.videoplayer.mediaengine.displaymode;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Display {
   public Display.Mode getModeInstance(int var1, int var2, int var3, float var4) {
      return new Display.Mode(var1, var2, var3, var4);
   }

   public static class Mode implements Parcelable {
      public static final Creator<Display.Mode> CREATOR = new Creator<Display.Mode>() {
         public Display.Mode createFromParcel(Parcel var1) {
            return new Display.Mode(var1);
         }

         public Display.Mode[] newArray(int var1) {
            return new Display.Mode[var1];
         }
      };
      private int mHeight;
      private int mModeId;
      private float mRefreshRate;
      private int mWidth;

      Mode(int var1, int var2, int var3, float var4) {
         this.mModeId = var1;
         this.mWidth = var2;
         this.mHeight = var3;
         this.mRefreshRate = var4;
      }

      private Mode(Parcel var1) {
         this(var1.readInt(), var1.readInt(), var1.readInt(), var1.readFloat());
      }

      public int describeContents() {
         return 0;
      }

      public boolean equals(Object var1) {
         if (this != var1) {
            if (var1 == null) {
               return false;
            }

            if (this.getClass() != var1.getClass()) {
               return false;
            }

            Display.Mode var2 = (Display.Mode)var1;
            if (this.mModeId != var2.mModeId) {
               return false;
            }

            if (this.mHeight != var2.mHeight) {
               return false;
            }

            if (this.mWidth != var2.mWidth) {
               return false;
            }

            if (Float.floatToIntBits(this.mRefreshRate) != Float.floatToIntBits(var2.mRefreshRate)) {
               return false;
            }
         }

         return true;
      }

      public int getModeId() {
         return this.mModeId;
      }

      public int getPhysicalHeight() {
         return this.mHeight;
      }

      public int getPhysicalWidth() {
         return this.mWidth;
      }

      public float getRefreshRate() {
         return this.mRefreshRate;
      }

      public int hashCode() {
         return (((this.mModeId + 31) * 31 + this.mHeight) * 31 + this.mWidth) * 31 + Float.floatToIntBits(this.mRefreshRate);
      }

      public boolean matches(int var1, int var2, float var3) {
         return this.mWidth == var1 && this.mHeight == var2 && Float.floatToIntBits(this.mRefreshRate) == Float.floatToIntBits(var3);
      }

      public String toString() {
         return "Mode [mModeId=" + this.mModeId + ", mHeight=" + this.mHeight + ", mWidth=" + this.mWidth + ", mRefreshRate=" + this.mRefreshRate + "]";
      }

      public void writeToParcel(Parcel var1, int var2) {
         var1.writeInt(this.mModeId);
         var1.writeInt(this.mWidth);
         var1.writeInt(this.mHeight);
         var1.writeFloat(this.mRefreshRate);
      }
   }
}
