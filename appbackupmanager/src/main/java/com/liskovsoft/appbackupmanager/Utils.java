package com.liskovsoft.appbackupmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;
import com.liskovsoft.appbackupmanager.ui.HandleMessages;

import java.io.File;

public class Utils
{
    public static void showErrors(final Activity activity)
    {
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                String errors = ShellCommands.getErrors();
                if(errors.length() > 0)
                {
                    new AlertDialog.Builder(activity)
                    .setTitle(R.string.errorDialogTitle)
                    .setMessage(errors)
                    .setPositiveButton(R.string.dialogOK, null)
                    .show();
                    ShellCommands.clearErrors();
                }
            }
        });
    }
    public static File createBackupDir(final Activity activity, final String path)
    {
        FileCreationHelper fileCreator = new FileCreationHelper();
        File backupDir;
        if(path.trim().length() > 0)
        {
            backupDir = fileCreator.createBackupFolder(path);
            if(fileCreator.isFallenBack())
            {
                activity.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast.makeText(activity, activity.getString(R.string.mkfileError) + " " + path + " - " + activity.getString(R.string.fallbackToDefault) + ": " + FileCreationHelper.getDefaultBackupDirPath(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        else
        {
            backupDir = fileCreator.createBackupFolder(FileCreationHelper.getDefaultBackupDirPath());
        }
        if(backupDir == null)
        {
            showWarning(activity, activity.getString(R.string.mkfileError) + " " + FileCreationHelper.getDefaultBackupDirPath(), activity.getString(R.string.backupFolderError));
        }
        return backupDir;
    }
    public static void showWarning(final Activity activity, final String title, final String message)
    {
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                new AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setNeutralButton(R.string.dialogOK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id){}})
                    .setCancelable(false)
                    .show();
            }
        });
    }
    public static void showConfirmDialog(Activity activity, String title, String message, final Command confirmCommand)
    {
        new AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.dialogOK, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    confirmCommand.execute();
                }
            })
            .setNegativeButton(R.string.dialogCancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id){}})
            .show();
    }

    @SuppressWarnings("NewApi")
    public static void reloadWithParentStack(Activity activity)
    {
        Intent intent = activity.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        TaskStackBuilder.create(activity)
            .addNextIntentWithParentStack(intent)
            .startActivities();
    }
    public static void reShowMessage(HandleMessages handleMessages, long tid)
    {
        // since messages are progressdialogs and not dialogfragments they need to be set again manually
        if(tid != -1)
            for(Thread t : Thread.getAllStackTraces().keySet())
                if(t.getId() == tid && t.isAlive())
                    handleMessages.reShowMessage();
    }
    public static String getName(String path)
    {
        if(path.endsWith(File.separator))
            path = path.substring(0, path.length() - 1);
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }
    public static void logDeviceInfo(Context context, String tag) {
        final String abiVersion = AssetsHandler.getAbi();
        try {
            final String packageName = context.getPackageName();
            final PackageInfo packageInfo = context.getPackageManager()
                .getPackageInfo(packageName, 0);
            final int versionCode = packageInfo.versionCode;
            final String versionName = packageInfo.versionName;
            Log.i(tag, String.format("running version %s/%s on abi %s",
                versionCode, versionName, abiVersion));
        } catch (PackageManager.NameNotFoundException e){
            Log.i(tag, String.format(
                "unable to determine package version (%s), abi version %s",
                e.toString(), abiVersion));
        }
    }
    public interface Command
    {
        public void execute();
    }
}
