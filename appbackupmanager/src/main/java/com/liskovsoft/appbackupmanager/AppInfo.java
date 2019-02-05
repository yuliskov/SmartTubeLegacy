package com.liskovsoft.appbackupmanager;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class AppInfo
implements Comparable<AppInfo>, Parcelable
{
    LogFile logInfo;
    String label, packageName, versionName, sourceDir, dataDir;
    int versionCode, backupMode;
    private boolean system, installed, checked, disabled;
    public Bitmap icon;
    public static final int MODE_UNSET = 0;
    public static final int MODE_APK = 1;
    public static final int MODE_DATA = 2;
    public static final int MODE_BOTH = 3;

    public AppInfo(String packageName, String label, String versionName, int versionCode, String sourceDir, String dataDir, boolean system, boolean installed)
    {
        this.label = label;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.sourceDir = sourceDir;
        this.dataDir = dataDir;
        this.system = system;
        this.installed = installed;
        this.backupMode = MODE_UNSET;
    }
    public String getPackageName()
    {
        return packageName;
    }
    public String getLabel()
    {
        return label;
    }
    public String getVersionName()
    {
        return versionName;
    }
    public int getVersionCode()
    {
        return versionCode;
    }
    public String getSourceDir()
    {
        return sourceDir;
    }
    public String getDataDir()
    {
        return dataDir;
    }
    public int getBackupMode()
    {
        return backupMode;
    }
    public LogFile getLogInfo()
    {
        return logInfo;
    }
    public void setLogInfo(LogFile newLogInfo)
    {
        logInfo = newLogInfo;
        backupMode = logInfo.getBackupMode();
    }
    public void setBackupMode(int modeToAdd)
    {
        // add only if both values are different and neither is MODE_BOTH
        if(backupMode == MODE_BOTH || modeToAdd == MODE_BOTH)
            backupMode = MODE_BOTH;
        else if(modeToAdd != backupMode)
            backupMode += modeToAdd;
    }
    public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }
    public boolean isDisabled()
    {
        return disabled;
    }
    public boolean isSystem()
    {
        return system;
    }
    public boolean isInstalled()
    {
        return installed;
    }
    // list of single files used by special backups - only for compatibility now
    public String[] getFilesList()
    {
        return null;
    }
    // should ideally be removed once proper polymorphism is implemented
    public boolean isSpecial()
    {
        return false;
    }
    public int compareTo(AppInfo appInfo)
    {
        return label.compareToIgnoreCase(appInfo.getLabel());
    }
    public String toString()
    {
        return label + " : " + packageName;
    }
    public int describeContents()
    {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeParcelable(logInfo, flags);
        out.writeString(label);
        out.writeString(packageName);
        out.writeString(versionName);
        out.writeString(sourceDir);
        out.writeString(dataDir);
        out.writeInt(versionCode);
        out.writeInt(backupMode);
        out.writeBooleanArray(new boolean[] {system, installed, checked});
        out.writeParcelable(icon, flags);
    }
    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>()
    {
        public AppInfo createFromParcel(Parcel in)
        {
            return new AppInfo (in);
        }
        public AppInfo[] newArray(int size)
        {
            return new AppInfo[size];
        }
    };
    protected AppInfo(Parcel in)
    {
        logInfo = in.readParcelable(getClass().getClassLoader());
        label = in.readString();
        packageName = in.readString();
        versionName = in.readString();
        sourceDir = in.readString();
        dataDir = in.readString();
        versionCode = in.readInt();
        backupMode = in.readInt();
        boolean[] bools = new boolean[4];
        in.readBooleanArray(bools);
        system = bools[0];
        installed = bools[1];
        checked = bools[2];
        icon = (Bitmap) in.readParcelable(getClass().getClassLoader());
    }
}
