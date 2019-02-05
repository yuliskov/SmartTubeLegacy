package com.liskovsoft.appbackupmanager;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class BackupRestoreHelper
{
    final static String TAG = BackupRestoreHelper.class.getSimpleName();

    public enum ActionType {
        BACKUP, RESTORE
    }

    public static int backup(Context context, File backupDir, AppInfo appInfo, ShellCommands shellCommands, int backupMode)
    {
        int ret = 0;
        File backupSubDir = new File(backupDir, appInfo.getPackageName());
        if(!backupSubDir.exists())
            backupSubDir.mkdirs();
        else if(backupMode != AppInfo.MODE_DATA && appInfo.getSourceDir().length() > 0)
        {
            if(appInfo.getLogInfo() != null && appInfo.getLogInfo().getSourceDir().length() > 0 && !appInfo.getSourceDir().equals(appInfo.getLogInfo().getSourceDir()))
            {
                String apk = appInfo.getLogInfo().getApk();
                if(apk != null)
                {
                    ShellCommands.deleteBackup(new File(backupSubDir, apk));
                    if(appInfo.getLogInfo().isEncrypted())
                        ShellCommands.deleteBackup(new File(backupSubDir, apk + ".gpg"));

                }
            }
        }

        if(appInfo.isSpecial())
        {
            ret = shellCommands.backupSpecial(backupSubDir, appInfo.getLabel(), appInfo.getFilesList());
            appInfo.setBackupMode(AppInfo.MODE_DATA);
        }
        else
        {
            ret = shellCommands.doBackup(context, backupSubDir, appInfo.getLabel(), appInfo.getDataDir(), appInfo.getSourceDir(), backupMode);
            appInfo.setBackupMode(backupMode);
        }

        shellCommands.logReturnMessage(context, ret);
        LogFile.writeLogFile(backupSubDir, appInfo, backupMode);
        return ret;
    }
    public static int restore(Context context, File backupDir, AppInfo appInfo, ShellCommands shellCommands, int mode, Crypto crypto)
    {
        int apkRet, restoreRet, permRet, cryptoRet;
        apkRet = restoreRet = permRet = cryptoRet = 0;
        File backupSubDir = new File(backupDir, appInfo.getPackageName());
        String apk = new LogFile(backupSubDir, appInfo.getPackageName()).getApk();
        String dataDir = appInfo.getDataDir();
        // extra check for needToDecrypt here because of BatchActivity which cannot really reset crypto to null for every package to restore
        if(crypto != null && Crypto.needToDecrypt(backupDir, appInfo, mode))
            crypto.decryptFromAppInfo(context, backupDir, appInfo, mode);
        if(mode == AppInfo.MODE_APK || mode == AppInfo.MODE_BOTH)
        {
            if(apk != null && apk.length() > 0)
            {
                if(appInfo.isSystem()) {
                    apkRet = shellCommands.restoreSystemApk(backupSubDir,
                        appInfo.getLabel(), apk);
                } else {
                    apkRet = shellCommands.restoreUserApk(backupSubDir,
                        appInfo.getLabel(), apk, context.getApplicationInfo().dataDir);
                }
                if(appInfo.isSystem() && appInfo.getLogInfo() != null)
                {
                    File apkFile = new File(backupDir, appInfo.getPackageName() + "/" + appInfo.getLogInfo().getApk());
                    shellCommands.copyNativeLibraries(apkFile, backupSubDir, appInfo.getPackageName());
                }
            }
            else if(!appInfo.isSpecial())
            {
                String s = "no apk to install: " + appInfo.getPackageName();
                Log.e(TAG, s);
                ShellCommands.writeErrorLog(appInfo.getPackageName(), s);
                apkRet = 1;
            }
        }
        if(mode == AppInfo.MODE_DATA || mode == AppInfo.MODE_BOTH)
        {
            if(apkRet == 0 && (appInfo.isInstalled() || mode == AppInfo.MODE_BOTH))
            {
                if(appInfo.isSpecial())
                {
                    restoreRet = shellCommands.restoreSpecial(backupSubDir, appInfo.getLabel(), appInfo.getFilesList());
                }
                else
                {
                    restoreRet = shellCommands.doRestore(context, backupSubDir, appInfo.getLabel(), appInfo.getPackageName(), appInfo.getLogInfo().getDataDir());

                    permRet = shellCommands.setPermissions(dataDir);
                }
            }
            else
            {
                Log.e(TAG, "cannot restore data without restoring apk, package is not installed: " + appInfo.getPackageName());
                apkRet = 1;
                ShellCommands.writeErrorLog(appInfo.getPackageName(), context.getString(R.string.restoreDataWithoutApkError));
            }
        }
        if(crypto != null)
        {
            Crypto.cleanUpDecryption(appInfo, backupSubDir, mode);
            if(crypto.isErrorSet())
                cryptoRet = 1;
        }
        int ret = apkRet + restoreRet + permRet + cryptoRet;
        shellCommands.logReturnMessage(context, ret);
        return ret;
    }
    public interface OnBackupRestoreListener
    {
        void onBackupRestoreDone();
    }
}
