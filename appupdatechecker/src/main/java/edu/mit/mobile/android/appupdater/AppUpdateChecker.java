package edu.mit.mobile.android.appupdater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import com.liskovsoft.sharedutils.helpers.Helpers;
import com.liskovsoft.sharedutils.locale.LocaleUtility;
import com.liskovsoft.sharedutils.mylogger.Log;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManager;
import edu.mit.mobile.android.appupdater.downloadmanager.MyDownloadManager.MyRequest;
import edu.mit.mobile.android.utils.StreamUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * A fairly simple non-Market app update checker. Give it a URL pointing to a JSON file
 * and it will compare its version (from the manifest file) to the versions listed in the JSON.
 * If there are newer version(s), it will provide the changelog between the installed version
 * and the latest version. The updater checks against the versionCode, but displays the versionName.
 *
 * While you can create your own OnAppUpdateListener to listen for new updates, OnUpdateDialog is
 * a handy implementation that displays a Dialog with a bulleted list and a button to do the upgrade.
 *
 * The JSON format looks like this:
 * <pre>
 * {
 * "package": {
 * "downloadUrl": "http://locast.mit.edu/connects/lcc.apk"
 * },
 *
 * "1.4.3": {
 * "versionCode": 6,
 * "changelog": ["New automatic update checker", "Improved template interactions"]
 * "changelog_ru": ["Новая система проверки оьновлений", "Улучшенное взаимодействие шаблонов"]
 * },
 * "1.4.2": {
 * "versionCode": 5,
 * "changelog": ["fixed crash when saving cast"]
 * }
 * }
 * </pre>
 *
 * @author <a href="mailto:spomeroy@mit.edu">Steve Pomeroy</a>
 */
public class AppUpdateChecker {
    private final static String TAG = AppUpdateChecker.class.getSimpleName();

    public static final String SHARED_PREFERENCES_NAME = "edu.mit.mobile.android.appupdater.preferences";
    public static final String PREF_ENABLED = "enabled", PREF_MIN_INTERVAL = "min_interval", PREF_LAST_UPDATED = "last_checked";

    private final String[] mVersionListUrls;
    private int currentAppVersion;

    private JSONObject pkgInfo;
    private final Context mContext;

    private final OnAppUpdateListener mUpdateListener;
    private SharedPreferences mPrefs;

    private static final int MILLISECONDS_IN_MINUTE = 60000;

    /**
     * @param context
     * @param versionListUrl URL pointing to a JSON file with the update list.
     * @param updateListener
     */
    public AppUpdateChecker(Context context, String versionListUrl, OnAppUpdateListener updateListener) {
        this(context, new String[]{versionListUrl}, updateListener);
    }

    /**
     * @param context
     * @param versionListUrls url array, tests url by access, first worked is used
     * @param updateListener
     */
    public AppUpdateChecker(Context context, String[] versionListUrls, OnAppUpdateListener updateListener) {
        mVersionListUrls = versionListUrls;
        mContext = context;
        mUpdateListener = updateListener;

        try {
            currentAppVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (final NameNotFoundException e) {
            Log.e(TAG, "Cannot get version for self! Who am I?! What's going on!? I'm so confused :-(");
            return;
        }

        mPrefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        // defaults are kept in the preference file for ease of tweaking
        // TODO put this on a thread somehow
        PreferenceManager.setDefaultValues(context, SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE, R.xml.preferences, true);
    }

    // min interval is stored as a string so a preference editor could potentially edit it using a text edit widget

    public int getMinInterval() {
        return Integer.parseInt(mPrefs.getString(PREF_MIN_INTERVAL, "60"));
    }

    public void setMinInterval(int minutes) {
        mPrefs.edit().putString(PREF_MIN_INTERVAL, String.valueOf(minutes)).apply();
    }

    public boolean getEnabled() {
        return mPrefs.getBoolean(PREF_ENABLED, true);
    }

    public void setEnabled(boolean enabled) {
        mPrefs.edit().putBoolean(PREF_ENABLED, enabled).apply();
    }

    /**
     * You normally shouldn't need to call this, as {@link #checkForUpdates()} checks it before doing any updates.
     *
     * @return true if the updater should check for updates
     */
    public boolean isStale() {
        return System.currentTimeMillis() - mPrefs.getLong(PREF_LAST_UPDATED, 0) > getMinInterval() * MILLISECONDS_IN_MINUTE;
    }

    /**
     * Checks for updates if updates haven't been checked for recently and if checking is enabled.
     */
    public void checkForUpdates() {
        if (getEnabled() && isStale()) {
            forceCheckForUpdates();
        }
    }

    /**
     * Like {@link #forceCheckForUpdates} but only if updates is enabled
     */
    public void forceCheckForUpdatesIfEnabled() {
        if (getEnabled()) {
            forceCheckForUpdates();
        }
    }

    /**
     * Minimize server payload!<br/>
     * Like {@link #forceCheckForUpdates} but only if prev update was long enough
     */
    public void forceCheckForUpdatesIfStalled() {
        if (isStale()) {
            forceCheckForUpdates();
        }
    }

    /**
     * Checks for updates regardless of when the last check happened or if checking for updates is enabled.
     */
    public void forceCheckForUpdates() {
        Log.d(TAG, "checking for updates...");

        if (mVersionListUrls == null || mVersionListUrls.length == 0) {
            Log.w(TAG, "Supplied url update list is null or empty");
        } else if (versionTask == null) {
            versionTask = new GetVersionJsonTask();
            versionTask.execute(mVersionListUrls);
        } else {
            Log.w(TAG, "checkForUpdates() called while already checking for updates. Ignoring...");
        }
    }

    // why oh why is the JSON API so poorly integrated into java?
    @SuppressWarnings("unchecked")
    private void triggerFromJson(JSONObject jo) throws JSONException {

        final ArrayList<String> changelog = new ArrayList<String>();

        // keep a sorted map of versionCode to the version information objects.
        // Most recent is at the top.
        final TreeMap<Integer, JSONObject> versionMap = new TreeMap<Integer, JSONObject>(new Comparator<Integer>() {
            public int compare(Integer object1, Integer object2) {
                return object2.compareTo(object1);
            }

            ;
        });

        for (final Iterator<String> i = jo.keys(); i.hasNext(); ) {
            final String versionName = i.next();
            if (versionName.equals("package")) {
                pkgInfo = jo.getJSONObject(versionName);
                continue;
            }
            final JSONObject versionInfo = jo.getJSONObject(versionName);
            versionInfo.put("versionName", versionName);

            final int versionCode = versionInfo.getInt("versionCode");
            versionMap.put(versionCode, versionInfo);
        }
        final int latestVersionNumber = versionMap.firstKey();
        final String latestVersionName = versionMap.get(latestVersionNumber).getString("versionName");

        final Uri[] downloadUrls;

        if (pkgInfo.has("downloadUrlList")) {
            JSONArray urls = pkgInfo.getJSONArray("downloadUrlList");
            downloadUrls = parse(urls);
        } else {
            String url = pkgInfo.getString("downloadUrl");
            downloadUrls = new Uri[]{Uri.parse(url)};
        }

        if (currentAppVersion > latestVersionNumber) {
            Log.d(TAG, "We're newer than the latest published version (" + latestVersionName + "). Living in the future...");
            mUpdateListener.appUpdateStatus(true, latestVersionName, null, downloadUrls);
            return;
        }

        if (currentAppVersion == latestVersionNumber) {
            Log.d(TAG, "We're at the latest version (" + currentAppVersion + ")");
            mUpdateListener.appUpdateStatus(true, latestVersionName, null, downloadUrls);
            return;
        }

        // construct the changelog. Newest entries are at the top.
        for (final Entry<Integer, JSONObject> version : versionMap.headMap(currentAppVersion).entrySet()) {
            final JSONObject versionInfo = version.getValue();

            JSONArray versionChangelog = versionInfo.optJSONArray("changelog_" + LocaleUtility.getCurrentLanguage(mContext));

            if (versionChangelog == null) {
                versionChangelog = versionInfo.optJSONArray("changelog");
            }

            if (versionChangelog != null) {
                final int len = versionChangelog.length();
                for (int i = 0; i < len; i++) {
                    changelog.add(versionChangelog.getString(i));
                }
            }
        }

        mUpdateListener.appUpdateStatus(false, latestVersionName, changelog, downloadUrls);
    }

    private Uri[] parse(JSONArray urls) {
        List<Uri> res = new ArrayList<>();
        for (int i = 0; i < urls.length(); i++) {
            String url = null;
            try {
                url = urls.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (url != null)
                res.add(Uri.parse(url));
        }
        return res.toArray(new Uri[] {});
    }

    private class VersionCheckException extends Exception {
        /**
         *
         */
        private static final long serialVersionUID = 397593559982487816L;

        public VersionCheckException(String msg) {
            super(msg);
        }
    }

    /**
     * Send off an intent to start the download of the app.
     */
    public void startUpgrade() {
        try {
            final Uri downloadUri = Uri.parse(pkgInfo.getString("downloadUrl"));
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, downloadUri));
        } catch (final JSONException e) {
            e.printStackTrace();
        }
    }

    private GetVersionJsonTask versionTask;

    private class GetVersionJsonTask extends AsyncTask<String[], Integer, JSONObject> {
        private String errorMsg = null;

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "update check progress: " + values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected JSONObject doInBackground(String[]... params) {
            publishProgress(0);

            final String[] urls = params[0];
            JSONObject jo = null;

            publishProgress(50);

            for (String url : urls) {
                jo = getJSON(url);
                if (jo != null)
                    break;
            }

            return jo;
        }

        private JSONObject getJSON(String urlStr) {
            JSONObject jo = null;
            try {
                MyDownloadManager manager = new MyDownloadManager(mContext);
                MyRequest request = new MyRequest(Uri.parse(urlStr));
                long reqId = manager.enqueue(request);

                InputStream content = manager.getStreamForDownloadedFile(reqId);
                jo = new JSONObject(StreamUtils.inputStreamToString(content));

                // this line may not be executed because of json error above
                mPrefs.edit().putLong(PREF_LAST_UPDATED, System.currentTimeMillis()).apply();
            } catch (final IllegalStateException | JSONException ex) {
                Log.e(TAG, ex.getMessage(), ex);
                errorMsg = Helpers.toString(ex);
            } catch (final Exception ex) {
                throw new IllegalStateException(ex);
            } finally {
                publishProgress(100);
            }

            return jo;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result == null) {
                Log.e(TAG, errorMsg);
                // Helpers.showMessage(mContext, TAG, errorMsg);
            } else {
                try {
                    triggerFromJson(result);

                } catch (final JSONException e) {
                    Log.e(TAG, "Error in JSON version file.", e);
                    // Helpers.showMessage(mContext, TAG, e);
                }
            }
            versionTask = null; // forget about us, we're done.
        }
    }
}
