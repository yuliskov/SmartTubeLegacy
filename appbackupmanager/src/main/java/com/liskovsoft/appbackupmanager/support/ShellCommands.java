package com.liskovsoft.appbackupmanager.support;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.liskovsoft.appbackupmanager.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShellCommands implements CommandHandler.UnexpectedExceptionListener
{
    final static String TAG = ShellCommands.class.getSimpleName();
    final static String EXTERNAL_FILES = "external_files";

    CommandHandler commandHandler = new CommandHandler();

    private final String oabUtils;
    private boolean legacyMode;

    SharedPreferences prefs;
    String busybox;
    ArrayList<String> users;
    private static String errors = "";
    boolean multiuserEnabled;
    private static Pattern gidPattern = Pattern.compile("Gid:\\s*\\(\\s*(\\d+)");
    private static Pattern uidPattern = Pattern.compile("Uid:\\s*\\(\\s*(\\d+)");
    public ShellCommands(SharedPreferences prefs, ArrayList<String> users,
        File filesDir)
    {
        this.users = users;
        this.prefs = prefs;
        String defaultBox = Build.VERSION.SDK_INT >= 23 ? "toybox" : "busybox";
        busybox = prefs.getString(Constants.PREFS_PATH_BUSYBOX, defaultBox).trim();
        if(busybox.length() == 0)
        {
            String[] boxPaths = new String[] {"toybox", "busybox",
                "/system/xbin/busybox"};
            for(String box : boxPaths) {
                if(checkBusybox(box)) {
                    busybox = box;
                    break;
                }
                // fallback:
                busybox = "busybox";
            }
        }
        this.users = getUsers();
        multiuserEnabled = this.users != null && this.users.size() > 1;

        this.oabUtils = new File(filesDir, AssetsHandler.OAB_UTILS)
            .getAbsolutePath();
        if(!checkOabUtils()) {
            legacyMode = true;
        }
    }
    public ShellCommands(SharedPreferences prefs, File filesDir)
    {
        this(prefs, null, filesDir);
        // initialize with userlist as null. getUsers checks if list is null and simply returns it if isn't and if its size is greater than 0.
    }

    @Override
    public void onUnexpectedException(Throwable t) {
        Log.e(TAG, "unexpected exception caught", t);
        writeErrorLog("", t.toString());
        errors += t.toString();
    }

    public int doBackup(Context context, File backupSubDir, String label, String packageData, String packageApk, int backupMode)
    {
        String backupSubDirPath = swapBackupDirPath(backupSubDir.getAbsolutePath());
        Log.i(TAG, "backup: " + label);
        // since api 24 (android 7) ApplicationInfo.dataDir can be null
        // this doesn't seem to be documented. proper sanity checking is needed
        if(packageData == null){
            writeErrorLog(label,
                "packageData is null. this is unexpected, please report it.");
            return 1;
        }
        List<String> commands = new ArrayList<>();
        // -L because fat (which will often be used to store the backup files)
        // doesn't support symlinks
        String followSymlinks = prefs.getBoolean("followSymlinks", true) ? "L" : "";
        switch(backupMode)
        {
            case AppInfo.MODE_APK:
                commands.add("cp " + packageApk + " " + backupSubDirPath);
                break;
            case AppInfo.MODE_DATA:
                commands.add("cp -R" + followSymlinks + " " + packageData + " " + backupSubDirPath);
                break;
            default: // defaults to MODE_BOTH
                commands.add("cp -R" + followSymlinks + " " + packageData + " " + backupSubDirPath);
                commands.add("cp " + packageApk + " " + backupSubDirPath);
                break;
        }
        File externalFilesDir = getExternalFilesDirPath(context, packageData);
        File backupSubDirExternalFiles = null;
        boolean backupExternalFiles = prefs.getBoolean("backupExternalFiles", false);
        if(backupExternalFiles && backupMode != AppInfo.MODE_APK && externalFilesDir != null) {
            backupSubDirExternalFiles = new File(backupSubDir, EXTERNAL_FILES);
            if(backupSubDirExternalFiles.exists() || backupSubDirExternalFiles.mkdir()) {
                commands.add("cp -R" + followSymlinks + " " +
                swapBackupDirPath(externalFilesDir.getAbsolutePath()) +
                " " + swapBackupDirPath(backupSubDir.getAbsolutePath() +
                "/" + EXTERNAL_FILES));
            } else {
                Log.e(TAG, "couldn't create " + backupSubDirExternalFiles.getAbsolutePath());
            }
        } else if(!backupExternalFiles && backupMode != AppInfo.MODE_APK) {
            String data = packageData.substring(packageData.lastIndexOf("/"));
            deleteBackup(new File(backupSubDir, EXTERNAL_FILES  + "/" + data + ".zip.gpg"));
        }
        List<String> errors = new ArrayList<>();
        int ret = commandHandler.runCmd("su", commands, line -> {},
            errors::add, e -> {
                Log.e(TAG, String.format("Exception caught running: %s",
                    TextUtils.join(", ", commands)), e);
                writeErrorLog(label, e.toString());
            }, this);
        if(errors.size() == 1 ) {
            String line = errors.get(0);
            // ignore error if it is about /lib while followSymlinks
            // is false or if it is about /lock in the data of firefox
            if((!prefs.getBoolean("followSymlinks", true) &&
                    (line.contains("lib") && ((line.contains("not permitted")
                    && line.contains("symlink"))) || line.contains("No such file or directory")))
                    || (line.contains("mozilla") && line.contains("/lock")))
                ret = 0;
        } else {
            for (String line : errors)
                writeErrorLog(label, line);
        }
        if(backupSubDirPath.startsWith(context.getApplicationInfo().dataDir))
        {
            /**
                * if backupDir is set to oab's own datadir (/data/data/dk.jens.backup)
                * we need to ensure that the permissions are correct before trying to
                * zip. on the external storage, gid will be sdcard_r (or something similar)
                * without any changes but in the app's own datadir files will have both uid
                * and gid as 0 / root when they are first copied with su.
            */
            ret = ret + setPermissions(backupSubDirPath);
        }
        String folder = new File(packageData).getName();
        deleteBackup(new File(backupSubDir, folder + "/lib"));
        if(label.equals(TAG))
        {
            copySelfAPk(backupSubDir, packageApk); // copy apk of app to parent directory for visibility
        }
        // only zip if data is backed up
        if(backupMode != AppInfo.MODE_APK)
        {
            int zipret = compress(new File(backupSubDir, folder));
            if(backupSubDirExternalFiles != null)
                zipret += compress(new File(backupSubDirExternalFiles, packageData.substring(packageData.lastIndexOf("/") + 1)));
            if(zipret != 0)
                ret += zipret;
        }
        // delete old encrypted files if encryption is not enabled
        if(!prefs.getBoolean(Constants.PREFS_ENABLECRYPTO, false))
            Crypto.cleanUpEncryptedFiles(backupSubDir, packageApk, packageData, backupMode, prefs.getBoolean("backupExternalFiles", false));
        return ret;
    }
    public int doRestore(Context context, File backupSubDir, String label, String packageName, String dataDir)
    {
        String backupSubDirPath = swapBackupDirPath(backupSubDir.getAbsolutePath());
        String dataDirName = dataDir.substring(dataDir.lastIndexOf("/") + 1);
        int unzipRet = -1;
        Log.i(TAG, "restoring: " + label);

        try
        {
            killPackage(context, packageName);
            File zipFile = new File(backupSubDir, dataDirName + ".zip");
            if(zipFile.exists())
                unzipRet = Compression.unzip(zipFile, backupSubDir);
            if(prefs.getBoolean("backupExternalFiles", false))
            {
                File externalFiles = new File(backupSubDir, EXTERNAL_FILES);
                if(externalFiles.exists())
                {
                    String externalFilesPath = context.getExternalFilesDir(null).getAbsolutePath();
                    externalFilesPath = externalFilesPath.substring(0, externalFilesPath.lastIndexOf(context.getApplicationInfo().packageName));
                    Compression.unzip(new File(externalFiles, dataDirName + ".zip"), new File(externalFilesPath));
                }
            }

            // check if there is a directory to copy from - it is not necessarily an error if there isn't
            String[] list = new File(backupSubDir, dataDirName).list();
            if(list != null && list.length > 0)
            {
                List<String> commands = new ArrayList<>();
                String restoreCommand = busybox + " cp -r " + backupSubDirPath + "/" + dataDirName + "/* " + dataDir + "\n";
                if(!(new File(dataDir).exists()))
                {
                    restoreCommand = "mkdir " + dataDir + "\n" + restoreCommand;
                    // restored system apps will not necessarily have the data folder (which is otherwise handled by pm)
                }
                commands.add(restoreCommand);
                if(Build.VERSION.SDK_INT >= 23) {
                    commands.add("restorecon -R " + dataDir + " || true");
                }
                int ret = commandHandler.runCmd("su", commands, line -> {},
                    line -> writeErrorLog(label, line),
                    e -> Log.e(TAG, "doRestore: " + e.toString()), this);
                if(multiuserEnabled)
                {
                    disablePackage(packageName);
                }
                return ret;
            }
            else
            {
                Log.i(TAG, packageName + " has empty or non-existent subdirectory: " + backupSubDir.getAbsolutePath() + "/" + dataDirName);
                return 0;
            }
        }
        finally
        {
            if(unzipRet == 0)
            {
                deleteBackup(new File(backupSubDir, dataDirName));
            }
        }
    }
    public int backupSpecial(File backupSubDir, String label, String... files)
    {
        // backup method only used for the special appinfos which can have lists of single files
        String backupSubDirPath = swapBackupDirPath(backupSubDir.getAbsolutePath());
        Log.i(TAG, "backup: " + label);
        List<String> commands = new ArrayList<>();
        if(files != null)
            for(String file : files)
                commands.add("cp -r " + file + " " + backupSubDirPath);
        int ret = commandHandler.runCmd("su", commands, line -> {},
            line -> writeErrorLog(label, line),
            e -> Log.e(TAG, "backupSpecial: " + e.toString()), this);
        if(files != null)
        {
            for(String file : files)
            {
                File f = new File(backupSubDir, Utils.getName(file));
                if(f.isDirectory())
                {
                    int zipret = compress(f);
                    if(zipret != 0 && zipret != 2)
                        ret += zipret;
                }
            }
        }
        return ret;
    }
    public int restoreSpecial(File backupSubDir, String label, String... files)
    {
        String backupSubDirPath = swapBackupDirPath(backupSubDir.getAbsolutePath());
        int unzipRet = 0;
        ArrayList<String> toDelete = new ArrayList<String>();

        Log.i(TAG, "restoring: " + label);
        try
        {
            List<String> commands = new ArrayList<>();
            if(files != null)
            {
                for(String file : files)
                {
                    Ownership ownership = getOwnership(file);
                    String filename = Utils.getName(file);
                    if(file.endsWith(File.separator))
                        file = file.substring(0, file.length() - 1);
                    String dest = file;
                    if(new File(file).isDirectory())
                    {
                        dest = file.substring(0, file.lastIndexOf("/"));
                        File zipFile = new File(backupSubDir, filename + ".zip");
                        if(zipFile.exists())
                        {
                            int ret = Compression.unzip(zipFile, backupSubDir);
                            // delay the deletion of the unzipped directory until the copying has been done
                            if(ret == 0)
                            {
                                toDelete.add(filename);
                            }
                            else
                            {
                                unzipRet += ret;
                                writeErrorLog(label, "error unzipping " + file);
                                continue;
                            }
                        }
                    }
                    else
                    {
                        ownership = getOwnership(file, "su");
                    }
                    commands.add("cp -r " + backupSubDirPath + "/" + filename + " " + dest);
                    commands.add(String.format("%s -R %s %s", busybox,
                        ownership.toString(), file));
                    commands.add(busybox + " chmod -R 0771 " + file);
                }
            }
            int ret = commandHandler.runCmd("su", commands, line -> {},
                line -> writeErrorLog(label, line),
                e -> Log.e(TAG, "restoreSpecial: " + e.toString()), this);
            return ret + unzipRet;
        }
        catch(IndexOutOfBoundsException | OwnershipException e)
        {
            Log.e(TAG, "restoreSpecial: " + e.toString());
        }
        finally
        {
            for(String filename : toDelete)
                deleteBackup(new File(backupSubDir, filename));
        }
        return 1;
    }
    private static ArrayList<String> getIdsFromStat(String stat)
    {
        Matcher uid = uidPattern.matcher(stat);
        Matcher gid = gidPattern.matcher(stat);
        if(!uid.find() || !gid.find())
            return null;
        ArrayList<String> res = new ArrayList<String>();
        res.add(uid.group(1));
        res.add(gid.group(1));
        return res;
    }
    public Ownership getOwnership(String packageDir) throws OwnershipException
    {
        return getOwnership(packageDir, "su");
    }
    public Ownership getOwnership(String packageDir, String shellPrivs)
        throws OwnershipException
    {
        if(!legacyMode) {
            List<String> result = new ArrayList<>();
            commandHandler.runCmd(shellPrivs, String.format("%s owner %s", oabUtils, packageDir),
                result::add, line -> writeErrorLog("oab-utils", line),
                e -> Log.e(TAG, "getOwnership: " + e.toString()), this);
            if(result.size() != 1) {
                if(result.size() < 1) {
                    throw new OwnershipException(
                        "got empty result from oab-utils");
                }
                StringBuilder sb = new StringBuilder();
                for(String line : result) {
                    sb.append(line).append("\n");
                }
                throw new OwnershipException(String.format(
                    "unexpected ownership result from oab-utils: %s",
                    sb.toString()));
            }
            try {
                JSONObject ownershipJson = new JSONObject(result.get(0));
                return new Ownership(ownershipJson.getInt("uid"),
                    ownershipJson.getInt("gid"));
            } catch (JSONException e) {
                throw new OwnershipException(String.format(
                    "error parsing ownership json: %s", e.toString()));
            }
        } else {
            List<String> commands = new ArrayList<>();
            /*
             * some packages can have 0 / UNKNOWN as uid and gid for a short
             * time before being switched to their proper ids so to work
             * around the race condition we sleep a little.
             */
            commands.add("sleep 1");
            commands.add(busybox + " stat " + packageDir);
            StringBuilder sb = new StringBuilder();
            // you don't need su for stat - you do for ls -l /data/
            // and for stat on single files
            int ret = commandHandler.runCmd(shellPrivs, commands, sb::append,
                line -> writeErrorLog("", line),
                e -> Log.e(TAG, "getOwnership: " + e.toString()), this);
            Log.i(TAG, "getOwnership return: " + ret);
            ArrayList<String> uid_gid = getIdsFromStat(sb.toString());
            if(uid_gid == null || uid_gid.isEmpty()) {
                throw new OwnershipException(
                    "no uid or gid found while trying to set permissions");
            }
            return new Ownership(uid_gid.get(0), uid_gid.get(1));
        }
    }
    public int setPermissions(String packageDir)
    {
        try
        {
            Ownership ownership = getOwnership(packageDir);
            List<String> commands = new ArrayList<>();
            if(Build.VERSION.SDK_INT < 23) {
                commands.add("for dir in " + packageDir + "/*; do if " +
                busybox + " test `" + busybox +
                " basename $dir` != \"lib\"; then " + busybox +
                " chown -R " + ownership.toString() +
                " $dir; " + busybox + " chmod -R 771 $dir; fi; done");
            } else {
                if(!legacyMode) {
                    commands.add(String.format("%s change-owner -r %s %s",
                        oabUtils, ownership.toString(), packageDir));
                    commands.add(String.format("%s set-permissions -r 771 %s", oabUtils,
                        packageDir));
                } else {
                    // android 6 has moved to toybox which doesn't include [ or [[
                    // meanwhile its implementation of test seems to be broken at least in cm 13
                    // cf. https://github.com/jensstein/oandbackup/issues/116
                    commands.add(String.format("%s chown -R %s %s",
                        busybox, ownership.toString(), packageDir));
                    commands.add(String.format("%s chmod -R 771 %s",
                        busybox, packageDir));
                }
            }
            // midlertidig indtil mere detaljeret som i fix_permissions l.367
            int ret = commandHandler.runCmd("su", commands, line -> {},
                line -> writeErrorLog(packageDir, line),
                e -> Log.e(TAG, "error while setPermissions: " + e.toString()), this);
            Log.i(TAG, "setPermissions return: " + ret);
            return ret;
        }
        catch(IndexOutOfBoundsException | OwnershipException e)
        {
            Log.e(TAG, "error while setPermissions: " + e.toString());
            writeErrorLog("", "setPermissions error: could not find permissions for " + packageDir);
        }
        return 1;
    }
    public int restoreUserApk(File backupDir, String label, String apk, String ownDataDir)
    {
        /* according to a comment in the android 8 source code for
         * /frameworks/base/cmds/pm/src/com/android/commands/pm/Pm.java
         * pm install is now discouraged / deprecated in favor of cmd
         * package install.
         */
        final String installCmd = Build.VERSION.SDK_INT >= 28 ?
            "cmd package install" : "pm install";
        // swapBackupDirPath is not needed with pm install
        List<String> commands = new ArrayList<>();
        /* in newer android versions selinux rules prevent system_server
         * from accessing many directories. in android 9 this prevents pm
         * install from installing from other directories that the package
         * staging directory (/data/local/tmp).
         * you can also pipe the apk data to the install command providing
         * it with a -S $apk_size value. but judging from this answer
         * https://issuetracker.google.com/issues/80270303#comment14 this
         * could potentially be unwise to use.
         */
        final File packageStagingDirectory = new File("/data/local/tmp");
        if(packageStagingDirectory.exists()) {
            final String apkDestPath = String.format("%s/%s",
                packageStagingDirectory, System.currentTimeMillis() + ".apk");
            commands.add(String.format("%s cp %s %s", busybox,
                swapBackupDirPath(backupDir.getAbsolutePath() + "/" + apk),
                apkDestPath));
            commands.add(String.format("%s -r %s", installCmd, apkDestPath));
            commands.add(String.format("%s rm -r %s", busybox, apkDestPath));
        } else if(backupDir.getAbsolutePath().startsWith(ownDataDir)) {
            /**
                * pm cannot install from a file on the data partition
                * Failure [INSTALL_FAILED_INVALID_URI] is reported
                * therefore, if the backup directory is oab's own data
                * directory a temporary directory on the external storage
                * is created where the apk is then copied to.
            */
            String tempPath = android.os.Environment.getExternalStorageDirectory() + "/apkTmp" + System.currentTimeMillis();
            commands.add(busybox + " mkdir " + swapBackupDirPath(tempPath));
            commands.add(busybox + " cp " + swapBackupDirPath(
                backupDir.getAbsolutePath() + "/" + apk) + " " +
                swapBackupDirPath(tempPath));
            commands.add(String.format("%s -r %s/%s", installCmd, tempPath, apk));
            commands.add(busybox + " rm -r " + swapBackupDirPath(tempPath));
        } else {
            commands.add(String.format("%s -r %s/%s", installCmd,
                backupDir.getAbsolutePath(), apk));
        }
        List<String> err = new ArrayList<>();
        int ret = commandHandler.runCmd("su", commands, line -> {},
            err::add, e -> Log.e(TAG, "restoreUserApk: ", e), this);
        // pm install returns 0 even for errors and prints part of its normal output to stderr
        // on api level 10 successful output spans three lines while it spans one line on the other api levels
        int limit = (Build.VERSION.SDK_INT == 10) ? 3 : 1;
        if(err.size() > limit)
        {
            for(String line : err)
            {
                writeErrorLog(label, line);
            }
            return 1;
        }
        else
        {
            return ret;
        }
    }

    public int restoreSystemApk(File backupDir, String label, String apk) {
        List<String> commands = new ArrayList<>();
        commands.add("mount -o remount,rw /system");
        // remounting with busybox mount seems to make android 4.4 fail the following commands without error

        // locations of apks have been changed in android 5
        String basePath = "/system/app/";
        if(Build.VERSION.SDK_INT >= 21)
        {
            basePath += apk.substring(0, apk.lastIndexOf(".")) + "/";
            commands.add("mkdir -p " + basePath);
            commands.add(busybox + " chmod 755 " + basePath);
        }
        // for some reason a permissions error is thrown if the apk path is not created first (W/zipro   ( 4433): Unable to open zip '/system/app/Term.apk': Permission denied)
        // with touch, a reboot is not necessary after restoring system apps
        // maybe use MediaScannerConnection.scanFile like CommandHelper from CyanogenMod FileManager
        commands.add(busybox + " touch " + basePath + apk);
        commands.add(busybox + " cp " + swapBackupDirPath(
            backupDir.getAbsolutePath()) + "/" + apk + " " + basePath);
        commands.add(busybox + " chmod 644 " + basePath + apk);
        commands.add("mount -o remount,ro /system");
        return commandHandler.runCmd("su", commands, line -> {},
            line -> writeErrorLog(label, line),
            e -> Log.e(TAG, "restoreSystemApk: ", e), this);
    }

    public int compress(File directoryToCompress)
    {
        int zipret = Compression.zip(directoryToCompress);
        if(zipret == 0)
        {
            deleteBackup(directoryToCompress);
        }
        else if(zipret == 2)
        {
            // handling empty zip
            deleteBackup(new File(directoryToCompress.getAbsolutePath() + ".zip"));
            return 0;
            // zipret == 2 shouldn't be treated as an error
        }
        return zipret;
    }
    public int uninstall(String packageName, String sourceDir, String dataDir, boolean isSystem)
    {
        List<String> commands = new ArrayList<>();
        if(!isSystem)
        {
            commands.add("pm uninstall " + packageName);
            commands.add(busybox + " rm -r /data/lib/" + packageName + "/*");
            // pm uninstall sletter ikke altid mapper og lib-filer ordentligt.
            // indføre tjek på pm uninstalls return
        }
        else
        {
            // it seems that busybox mount sometimes fails silently so use toolbox instead
            commands.add("mount -o remount,rw /system");
            commands.add(busybox + " rm " + sourceDir);
            if(Build.VERSION.SDK_INT >= 21)
            {
                String apkSubDir = Utils.getName(sourceDir);
                apkSubDir = apkSubDir.substring(0, apkSubDir.lastIndexOf("."));
                commands.add("rm -r /system/app/" + apkSubDir);
            }
            commands.add("mount -o remount,ro /system");
            commands.add(busybox + " rm -r " + dataDir);
            commands.add(busybox + " rm -r /data/app-lib/" + packageName + "*");
        }
        List<String> err = new ArrayList<>();
        int ret = commandHandler.runCmd("su", commands, line -> {},
            err::add, e -> Log.e(TAG, "uninstall", e), this);
        if(ret != 0)
        {
            for(String line : err)
            {
                if(line.contains("No such file or directory") && err.size() == 1)
                {
                    // ignore errors if it is only that the directory doesn't exist for rm to remove
                    ret = 0;
                }
                else
                {
                    writeErrorLog(packageName, line);
                }
            }
        }
        Log.i(TAG, "uninstall return: " + ret);
        return ret;
    }
    public int quickReboot()
    {
        List<String> commands = new ArrayList<>();
        commands.add(busybox + " pkill system_server");
//            dos.writeBytes("restart\n"); // restart doesn't seem to work here even though it works fine from ssh
        int ret = commandHandler.runCmd("su", commands, line -> {},
            line -> writeErrorLog("", line),
            e -> Log.e(TAG, "quickReboot: ", e), this);
        return ret;
    }
    public static void deleteBackup(File file)
    {
        if(file.exists())
        {
            if(file.isDirectory())
                if(file.list().length > 0 && file.listFiles() != null)
                    for(File child : file.listFiles())
                        deleteBackup(child);
            file.delete();
        }
    }
    public void deleteOldApk(File backupfolder, String newApkPath)
    {
        final String apk = new File(newApkPath).getName();
        File[] files = backupfolder.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String filename)
            {
                return (!filename.equals(apk) && filename.endsWith(".apk"));
            }
        });
        if(files != null)
        {
            for(File file : files)
            {
                file.delete();
            }
        }
        else
        {
            Log.e(TAG, "deleteOldApk: listFiles returned null");
        }
    }
    public void killPackage(Context context, String packageName)
    {
        List<ActivityManager.RunningAppProcessInfo> runningList;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        runningList = manager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo process : runningList)
        {
            if(process.processName.equals(packageName) && process.pid != android.os.Process.myPid())
            {
                List<String> commands = new ArrayList<>();
                commands.add("kill " + process.pid);
                commandHandler.runCmd("su", commands, line -> {},
                    line -> writeErrorLog(packageName, line),
                    e -> Log.e(TAG, "killPackage: ", e), this);
            }
        }
    }

    public void logReturnMessage(Context context, int returnCode)
    {
        String returnMessage = returnCode == 0 ? context.getString(R.string.shellReturnSuccess) : context.getString(R.string.shellReturnError);
        Log.i(TAG, "return: " + returnCode + " / " + returnMessage);
    }
    public static void writeErrorLog(String packageName, String err)
    {
        errors += packageName + ": " + err + "\n";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
        String dateFormated = dateFormat.format(date);
        try
        {
            File outFile = new FileCreationHelper().createLogFile(FileCreationHelper.getDefaultLogFilePath());
            if(outFile != null)
            {
                try(FileWriter fw = new FileWriter(outFile.getAbsoluteFile(),
                        true);
                        BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(dateFormated + ": " + err + " [" + packageName + "]\n");
                }
            }
        }
        catch(IOException e)
        {
            Log.e(TAG, e.toString());
        }
    }
    public static String getErrors()
    {
        return errors;
    }
    public static void clearErrors()
    {
        errors = "";
    }
    public static boolean checkSuperUser()
    {
        try
        {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            if(p.exitValue() == 0)
                return true;
        }
        catch(IOException e)
        {
            Log.e(TAG, "checkSuperUser: " + e.toString());
        }
        catch(InterruptedException e)
        {
            Log.e(TAG, "checkSuperUser: " + e.toString());
            Thread.currentThread().interrupt();
        }
        return false;
    }
    public boolean checkBusybox() {
        return checkBusybox(busybox);
    }
    public boolean checkBusybox(String busyboxPath)
    {
        int ret = commandHandler.runCmd("sh", busyboxPath,
            line -> {}, line -> writeErrorLog("busybox", line),
            e -> Log.e(TAG, "checkBusybox: ", e), this);
        return ret == 0;
    }
    public boolean checkOabUtils() {
        int ret = commandHandler.runCmd("su", String.format("%s -h", oabUtils),
            line -> {}, line -> writeErrorLog("oab-utils", line),
            e -> Log.e(TAG, "checkOabUtils: ", e), this);
        Log.d(TAG, String.format("checkOabUtils returned %s", ret == 0));
        if(ret != 0) {
            final List<String> commands = new ArrayList<>();
            commands.add(String.format("ls -l %s", oabUtils));
            commands.add(String.format("file %s", oabUtils));
            commandHandler.runCmd("su", commands, line -> {
                Log.i(TAG, "oab-utils" + line);
                writeErrorLog("oab-utils", line);
            }, line -> {
                Log.e(TAG, "oab-utils" + line);
                writeErrorLog("oab-utils", line);
            }, e -> Log.e(TAG, "checkOabUtils (ret != 0): ", e), this);
        }
        return ret == 0;
    }
    public void copyNativeLibraries(File apk, File outputDir, String packageName)
    {
        /*
         * first try the primary abi and then the secondary if the
         * first doesn't give any results.
         * see frameworks/base/core/jni/com_android_internal_content_NativeLibraryHelper.cpp:iterateOverNativeFiles
         * frameworks/base/core/java/com/android/internal/content/NativeLibraryHelper.java
         * in the android source
         */
        String libPrefix = "lib/";
        ArrayList<String> libs = Compression.list(apk, libPrefix + Build.CPU_ABI);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO && (libs == null || libs.size() == 0))
            libs = Compression.list(apk, libPrefix + Build.CPU_ABI2);
        if(libs != null && libs.size() > 0)
        {
            if(Compression.unzip(apk, outputDir, libs) == 0)
            {
                List<String> commands = new ArrayList<>();
                commands.add("mount -o remount,rw /system");
                String src = swapBackupDirPath(outputDir.getAbsolutePath());
                for(String lib : libs)
                {
                    commands.add("cp " + src + "/" + lib + " /system/lib");
                    commands.add("chmod 644 /system/lib/" + Utils.getName(lib));
                }
                commands.add("mount -o remount,ro /system");
                commandHandler.runCmd("su", commands, line -> {},
                    line -> writeErrorLog(packageName, line),
                    e -> Log.e(TAG, "copyNativeLibraries: ", e), this);
            }
            deleteBackup(new File(outputDir, "lib"));
        }
    }
    public ArrayList getUsers()
    {
        if(Build.VERSION.SDK_INT > 17)
        {
            if(users != null && users.size() > 0)
            {
                return users;
            }
            else
            {
    //            int currentUser = getCurrentUser();
                List<String> commands = new ArrayList<>();
                commands.add("pm list users | " + busybox + " sed -nr 's/.*\\{([0-9]+):.*/\\1/p'");
                ArrayList<String> users = new ArrayList<>();
                int ret = commandHandler.runCmd("su", commands, line -> {
                    if(line.trim().length() != 0)
                        users.add(line.trim());
                    }, line -> writeErrorLog("", line),
                    e -> Log.e(TAG, "getUsers: ", e), this);
                return ret == 0 ? users : null;
            }
        }
        else
        {
            ArrayList<String> users = new ArrayList<String>();
            users.add("0");
            return users;
        }
    }
    public static int getCurrentUser()
    {
        try
        {
            // using reflection to get id of calling user since method getCallingUserId of UserHandle is hidden
            // https://github.com/android/platform_frameworks_base/blob/master/core/java/android/os/UserHandle.java#L123
            Class userHandle = Class.forName("android.os.UserHandle");
            boolean muEnabled = userHandle.getField("MU_ENABLED").getBoolean(null);
            int range = userHandle.getField("PER_USER_RANGE").getInt(null);
            if(muEnabled)
                return android.os.Binder.getCallingUid() / range;
        }
        catch(ClassNotFoundException e){}
        catch(NoSuchFieldException e){}
        catch(IllegalAccessException e){}
        return 0;
    }
    public static ArrayList<String> getDisabledPackages(CommandHandler commandHandler)
    {
        List<String> commands = new ArrayList<>();
        commands.add("pm list packages -d");
        ArrayList<String> packages = new ArrayList<>();
        int ret = commandHandler.runCmd("sh", commands, line -> {
            if(line.contains(":"))
                packages.add(line.substring(line.indexOf(":") + 1).trim());
        }, line -> {}, e -> Log.e(TAG, "getDisabledPackages: ", e), e -> {});
        if(ret == 0 && packages.size() > 0)
            return packages;
        return null;
    }
    public void enableDisablePackage(String packageName, ArrayList<String> users, boolean enable)
    {
        String option = enable ? "enable" : "disable";
        if(users != null && users.size() > 0)
        {
            for(String user : users)
            {
                List<String> commands = new ArrayList<>();
                commands.add("pm " + option + " --user " + user + " " + packageName);
                commandHandler.runCmd("su", commands, line -> {},
                    line -> writeErrorLog(packageName, line),
                    e -> Log.e(TAG, "enableDisablePackage: ", e), this);
            }
        }
    }
    public void disablePackage(String packageName)
    {
        String userString = "";
        int currentUser = getCurrentUser();
        for(String user : users)
        {
            userString += " " + user;
        }
        List<String> commands = new ArrayList<>();
        // reflection could probably be used to find packages available to a given user: PackageManager.queryIntentActivitiesAsUser
        // http://androidxref.com/4.2_r1/xref/frameworks/base/core/java/android/content/pm/PackageManager.java#1880

        // editing package-restrictions.xml directly seems to require a reboot
        // sub=`grep $packageName package-restrictions.xml`
        // sed -i 's|$sub|"<pkg name=\"$packageName\" inst=\"false\" />"' package-restrictions.xml

        // disabling via pm has the unfortunate side-effect that packages can only be re-enabled via pm
        String disable = "pm disable --user $user " + packageName;
        // if packagename is in package-restriction.xml the app is probably not installed by $user
        String grep = busybox + " grep " + packageName + " /data/system/users/$user/package-restrictions.xml";
        // though it could be listed as enabled
        String enabled = grep + " | " + busybox + " grep enabled=\"1\"";
        // why doesn't ! enabled work
        commands.add("for user in " + userString + "; do if [ $user != " +
            currentUser + " ] && " + grep + " && " + enabled + "; then " +
            disable + "; fi; done");
        commandHandler.runCmd("su", commands, line -> {},
            line -> writeErrorLog(packageName, line),
            e -> Log.e(TAG, "disablePackage: ", e), this);
    }
    // manually installing can be used as workaround for issues with multiple users - have checkbox in preferences to toggle this
    /*
    public void installByIntent(File backupDir, String apk)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(backupDir, apk)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    */
    // due to changes in 4.3 (api level 18) the root user cannot see /storage/emulated/$user/ so calls using su (except pm in restoreApk) should swap the first part with /mnt/shell/emulated/, which is readable by the root user
    // api 23 (android 6) seems to have reverted to the old behaviour
    public String swapBackupDirPath(String path)
    {
        if(Build.VERSION.SDK_INT >= 18 &&
            Build.VERSION.SDK_INT < 23)
        {
            if(path.contains("/storage/emulated/"))
            {
                path = path.replace("/storage/emulated/", "/mnt/shell/emulated/");
            }
        }
        return path;
    }
    public void copySelfAPk(File backupSubDir, String apk)
    {
        if(prefs.getBoolean("copySelfApk", false))
        {
            String parent = backupSubDir.getParent() + "/" + TAG + ".apk";
            String apkPath = backupSubDir.getAbsolutePath() + "/" + new File(apk).getName();
            if(parent != null)
            {
                List<String> commands = new ArrayList<>();
                commands.add(busybox + " cp " + apkPath + " " + parent);
                commandHandler.runCmd("sh", commands, line -> {},
                    line -> writeErrorLog("", line),
                    e -> Log.e(TAG, "copySelfApk: ", e), this);
            }
        }
    }
    public File getExternalFilesDirPath(Context context, String packageData)
    {
        if(Build.VERSION.SDK_INT >= 8)
        {
            String externalFilesPath = context.getExternalFilesDir(null).getAbsolutePath();
            // get path of own externalfilesdir and then cutting at the packagename to get the general path
            externalFilesPath = externalFilesPath.substring(0, externalFilesPath.lastIndexOf(context.getApplicationInfo().packageName));
            File externalFilesDir = new File(externalFilesPath, new File(packageData).getName());
            if(externalFilesDir.exists())
                return externalFilesDir;
        }
        return null;
    }

    private class Ownership {
        private int uid;
        private int gid;
        private final boolean legacyMode;

        public Ownership(int uid, int gid) {
            this.uid = uid;
            this.gid = gid;
            this.legacyMode = false;
        }

        // only for legacy compatibility
        public Ownership(String uidStr, String gidStr) throws OwnershipException {
            if((uidStr == null || uidStr.isEmpty()) ||
                    (gidStr == null || gidStr.isEmpty())) {
                throw new OwnershipException(
                    "cannot initiate ownership object with empty uid or gid");
            }
            this.uidStr = uidStr;
            this.gidStr = gidStr;
            this.legacyMode = true;
        }
        private String uidStr;
        private String gidStr;

        @Override
        public String toString() {
            if(legacyMode) {
                return String.format("%s:%s", uidStr, gidStr);
            } else {
                return String.format("%s:%s", uid, gid);
            }
        }

    }

    private class OwnershipException extends Exception {
        public OwnershipException(String msg) {
            super(msg);
        }
    }
}
