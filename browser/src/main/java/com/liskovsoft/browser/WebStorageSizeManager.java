package com.liskovsoft.browser;

import android.content.Context;
import android.os.StatFs;
import android.util.Log;
import android.webkit.WebStorage;

import java.io.File;

/**
 * Package level class for managing the disk size consumed by the WebDatabase
 * and ApplicationCaches APIs (henceforth called Web storage).
 *
 * Currently, the situation on the WebKit side is as follows:
 * - WebDatabase enforces a quota for each origin.
 * - Session/LocalStorage do not enforce any disk limits.
 * - ApplicationCaches enforces a maximum size for all origins.
 *
 * The WebStorageSizeManager maintains a global limit for the disk space
 * consumed by the WebDatabase and ApplicationCaches. As soon as WebKit will
 * have a limit for Session/LocalStorage, this class will manage the space used
 * by those APIs as well.
 *
 * The global limit is computed as a function of the size of the partition where
 * these APIs store their data (they must store it on the same partition for
 * this to work) and the size of the available space on that partition.
 * The global limit is not subject to user configuration but we do provide
 * a debug-only setting.
 * TODO(andreip): implement the debug setting.
 *
 * The size of the disk space used for Web storage is initially divided between
 * WebDatabase and ApplicationCaches as follows:
 *
 * 75% for WebDatabase
 * 25% for ApplicationCaches
 *
 * When an origin's database usage reaches its current quota, WebKit invokes
 * the following callback function:
 * - exceededDatabaseQuota(Frame* frame, const String& database_name);
 * Note that the default quota for a new origin is 0, so we will receive the
 * 'exceededDatabaseQuota' callback before a new origin gets the chance to
 * create its first database.
 *
 * When the total ApplicationCaches usage reaches its current quota, WebKit
 * invokes the following callback function:
 * - void reachedMaxAppCacheSize(int64_t spaceNeeded);
 *
 * The WebStorageSizeManager's main job is to respond to the above two callbacks
 * by inspecting the amount of unused Web storage quota (i.e. global limit -
 * sum of all other origins' quota) and deciding if a quota increase for the
 * out-of-space origin is allowed or not.
 *
 * The default quota for an origin is its estimated size. If we cannot satisfy
 * the estimated size, then WebCore will not create the database.
 * Quota increases are done in steps, where the increase step is
 * min(QUOTA_INCREASE_STEP, unused_quota).
 *
 * When all the Web storage space is used, the WebStorageSizeManager creates
 * a system notification that will guide the user to the WebSettings UI. There,
 * the user can free some of the Web storage space by deleting all the data used
 * by an origin.
 */
public class WebStorageSizeManager {
    // Logging flags.
    private final static boolean LOGV_ENABLED = com.liskovsoft.browser.Browser.LOGV_ENABLED;
    private final static boolean LOGD_ENABLED = com.liskovsoft.browser.Browser.LOGD_ENABLED;
    private final static String LOGTAG = "browser";
    // The default quota value for an origin.
    public final static long ORIGIN_DEFAULT_QUOTA = 3 * 1024 * 1024;  // 3MB
    // The default value for quota increases.
    public final static long QUOTA_INCREASE_STEP = 1 * 1024 * 1024;  // 1MB
    // Extra padding space for appcache maximum size increases. This is needed
    // because WebKit sends us an estimate of the amount of space needed
    // but this estimate may, currently, be slightly less than what is actually
    // needed. We therefore add some 'padding'.
    // TODO(andreip): fix this in WebKit.
    public final static long APPCACHE_MAXSIZE_PADDING = 512 * 1024; // 512KB
    // The system status bar notification id.
    private final static int OUT_OF_SPACE_ID = 1;
    // The time of the last out of space notification
    private static long mLastOutOfSpaceNotificationTime = -1;
    // Delay between two notification in ms
    private final static long NOTIFICATION_INTERVAL = 5 * 60 * 1000;
    // Delay in ms used when resetting the notification time
    private final static long RESET_NOTIFICATION_INTERVAL = 3 * 1000;
    // The application context.
    private final Context mContext;
    // The global Web storage limit.
    private final long mGlobalLimit;
    // The maximum size of the application cache file.
    private long mAppCacheMaxSize;

    /**
     * Interface used by the WebStorageSizeManager to obtain information
     * about the underlying file system. This functionality is separated
     * into its own interface mainly for testing purposes.
     */
    public interface DiskInfo {
        /**
         * @return the size of the free space in the file system.
         */
        public long getFreeSpaceSizeBytes();

        /**
         * @return the total size of the file system.
         */
        public long getTotalSizeBytes();
    }

    ;

    private DiskInfo mDiskInfo;

    // For convenience, we provide a DiskInfo implementation that uses StatFs.
    public static class StatFsDiskInfo implements DiskInfo {
        private StatFs mFs;

        public StatFsDiskInfo(String path) {
            mFs = new StatFs(path);
        }

        public long getFreeSpaceSizeBytes() {
            return (long) (mFs.getAvailableBlocks()) * mFs.getBlockSize();
        }

        public long getTotalSizeBytes() {
            return (long) (mFs.getBlockCount()) * mFs.getBlockSize();
        }
    }

    ;

    /**
     * Interface used by the WebStorageSizeManager to obtain information
     * about the appcache file. This functionality is separated into its own
     * interface mainly for testing purposes.
     */
    public interface AppCacheInfo {
        /**
         * @return the current size of the appcache file.
         */
        public long getAppCacheSizeBytes();
    }

    ;

    // For convenience, we provide an AppCacheInfo implementation.
    public static class WebKitAppCacheInfo implements AppCacheInfo {
        // The name of the application cache file. Keep in sync with
        // WebCore/loader/appcache/ApplicationCacheStorage.cpp
        private final static String APPCACHE_FILE = "ApplicationCache.db";
        private String mAppCachePath;

        public WebKitAppCacheInfo(String path) {
            mAppCachePath = path;
        }

        public long getAppCacheSizeBytes() {
            File file = new File(mAppCachePath + File.separator + APPCACHE_FILE);
            return file.length();
        }
    }

    ;

    /**
     * Public ctor
     *
     * @param ctx          is the application context
     * @param diskInfo     is the DiskInfo instance used to query the file system.
     * @param appCacheInfo is the AppCacheInfo used to query info about the
     *                     appcache file.
     */
    public WebStorageSizeManager(Context ctx, DiskInfo diskInfo, AppCacheInfo appCacheInfo) {
        mContext = ctx.getApplicationContext();
        mDiskInfo = diskInfo;
        mGlobalLimit = getGlobalLimit();
        // The initial max size of the app cache is either 25% of the global
        // limit or the current size of the app cache file, whichever is bigger.
        mAppCacheMaxSize = Math.max(mGlobalLimit / 4, appCacheInfo.getAppCacheSizeBytes());
    }

    /**
     * Returns the maximum size of the application cache.
     */
    public long getAppCacheMaxSize() {
        return mAppCacheMaxSize;
    }

    /**
     * The origin has exceeded its database quota.
     *
     * @param url                the URL that exceeded the quota
     * @param databaseIdentifier the identifier of the database on
     *                           which the transaction that caused the quota overflow was run
     * @param currentQuota       the current quota for the origin.
     * @param estimatedSize      the estimated size of a new database, or 0 if
     *                           this has been invoked in response to an existing database
     *                           overflowing its quota.
     * @param totalUsedQuota     is the sum of all origins' quota.
     * @param quotaUpdater       The callback to run when a decision to allow or
     *                           deny quota has been made. Don't forget to call this!
     */
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota,
                                        WebStorage.QuotaUpdater quotaUpdater) {
        if (LOGV_ENABLED) {
            Log.v(LOGTAG, "Received onExceededDatabaseQuota for " + url + ":" + databaseIdentifier + "(current quota: " + currentQuota + ", total " +
                    "used quota: " + totalUsedQuota + ")");
        }
        long totalUnusedQuota = mGlobalLimit - totalUsedQuota - mAppCacheMaxSize;

        if (totalUnusedQuota <= 0) {
            // There definitely isn't any more space. Fire notifications
            // if needed and exit.
            if (totalUsedQuota > 0) {
                // We only fire the notification if there are some other websites
                // using some of the quota. This avoids the degenerate case where
                // the first ever website to use Web storage tries to use more
                // data than it is actually available. In such a case, showing
                // the notification would not help at all since there is nothing
                // the user can do.
                scheduleOutOfSpaceNotification();
            }
            quotaUpdater.updateQuota(currentQuota);
            if (LOGV_ENABLED) {
                Log.v(LOGTAG, "onExceededDatabaseQuota: out of space.");
            }
            return;
        }

        // We have some space inside mGlobalLimit.
        long newOriginQuota = currentQuota;
        if (newOriginQuota == 0) {
            // This is a new origin, give it the size it asked for if possible.
            // If we cannot satisfy the estimatedSize, we should return 0 as
            // returning a value less that what the site requested will lead
            // to webcore not creating the database.
            if (totalUnusedQuota >= estimatedSize) {
                newOriginQuota = estimatedSize;
            } else {
                if (LOGV_ENABLED) {
                    Log.v(LOGTAG, "onExceededDatabaseQuota: Unable to satisfy" + " estimatedSize for the new database " + " (estimatedSize: " +
                            estimatedSize + ", unused quota: " + totalUnusedQuota);
                }
                newOriginQuota = 0;
            }
        } else {
            // This is an origin we have seen before. It wants a quota
            // increase. There are two circumstances: either the origin
            // is creating a new database or it has overflowed an existing database.

            // Increase the quota. If estimatedSize == 0, then this is a quota overflow
            // rather than the creation of a new database.
            long quotaIncrease = estimatedSize == 0 ? Math.min(QUOTA_INCREASE_STEP, totalUnusedQuota) : estimatedSize;
            newOriginQuota += quotaIncrease;

            if (quotaIncrease > totalUnusedQuota) {
                // We can't fit, so deny quota.
                newOriginQuota = currentQuota;
            }
        }

        quotaUpdater.updateQuota(newOriginQuota);

        if (LOGV_ENABLED) {
            Log.v(LOGTAG, "onExceededDatabaseQuota set new quota to " + newOriginQuota);
        }
    }

    /**
     * The Application Cache has exceeded its max size.
     *
     * @param spaceNeeded    is the amount of disk space that would be needed
     *                       in order for the last appcache operation to succeed.
     * @param totalUsedQuota is the sum of all origins' quota.
     * @param quotaUpdater   A callback to inform the WebCore thread that a new
     *                       app cache size is available. This callback must always be executed at
     *                       some point to ensure that the sleeping WebCore thread is woken up.
     */
    public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        if (LOGV_ENABLED) {
            Log.v(LOGTAG, "Received onReachedMaxAppCacheSize with spaceNeeded " + spaceNeeded + " bytes.");
        }

        long totalUnusedQuota = mGlobalLimit - totalUsedQuota - mAppCacheMaxSize;

        if (totalUnusedQuota < spaceNeeded + APPCACHE_MAXSIZE_PADDING) {
            // There definitely isn't any more space. Fire notifications
            // if needed and exit.
            if (totalUsedQuota > 0) {
                // We only fire the notification if there are some other websites
                // using some of the quota. This avoids the degenerate case where
                // the first ever website to use Web storage tries to use more
                // data than it is actually available. In such a case, showing
                // the notification would not help at all since there is nothing
                // the user can do.
                scheduleOutOfSpaceNotification();
            }
            quotaUpdater.updateQuota(0);
            if (LOGV_ENABLED) {
                Log.v(LOGTAG, "onReachedMaxAppCacheSize: out of space.");
            }
            return;
        }
        // There is enough space to accommodate spaceNeeded bytes.
        mAppCacheMaxSize += spaceNeeded + APPCACHE_MAXSIZE_PADDING;
        quotaUpdater.updateQuota(mAppCacheMaxSize);

        if (LOGV_ENABLED) {
            Log.v(LOGTAG, "onReachedMaxAppCacheSize set new max size to " + mAppCacheMaxSize);
        }
    }

    // Reset the notification time; we use this iff the user
    // use clear all; we reset it to some time in the future instead
    // of just setting it to -1, as the clear all method is asynchronous
    public static void resetLastOutOfSpaceNotificationTime() {
        mLastOutOfSpaceNotificationTime = System.currentTimeMillis() - NOTIFICATION_INTERVAL + RESET_NOTIFICATION_INTERVAL;
    }

    // Computes the global limit as a function of the size of the data
    // partition and the amount of free space on that partition.
    private long getGlobalLimit() {
        long freeSpace = mDiskInfo.getFreeSpaceSizeBytes();
        long fileSystemSize = mDiskInfo.getTotalSizeBytes();
        return calculateGlobalLimit(fileSystemSize, freeSpace);
    }

    /*package*/
    static long calculateGlobalLimit(long fileSystemSizeBytes, long freeSpaceBytes) {
        if (fileSystemSizeBytes <= 0 || freeSpaceBytes <= 0 || freeSpaceBytes > fileSystemSizeBytes) {
            return 0;
        }

        long fileSystemSizeRatio = 2 << ((int) Math.floor(Math.log10(fileSystemSizeBytes / (1024 * 1024))));
        long maxSizeBytes = (long) Math.min(Math.floor(fileSystemSizeBytes / fileSystemSizeRatio), Math.floor(freeSpaceBytes / 2));
        // Round maxSizeBytes up to a multiple of 1024KB (but only if
        // maxSizeBytes > 1MB).
        long maxSizeStepBytes = 1024 * 1024;
        if (maxSizeBytes < maxSizeStepBytes) {
            return 0;
        }
        long roundingExtra = maxSizeBytes % maxSizeStepBytes == 0 ? 0 : 1;
        return (maxSizeStepBytes * ((maxSizeBytes / maxSizeStepBytes) + roundingExtra));
    }

    // Schedules a system notification that takes the user to the WebSettings
    // activity when clicked.
    private void scheduleOutOfSpaceNotification() {
        // TODO: not implemented
    }
}
