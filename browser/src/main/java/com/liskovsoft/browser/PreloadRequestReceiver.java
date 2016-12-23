package com.liskovsoft.browser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Broadcast receiver for receiving browser preload requests
 */
public class PreloadRequestReceiver extends BroadcastReceiver {

    private final static String LOGTAG = "browser.preloader";
    private final static boolean LOGD_ENABLED = com.liskovsoft.browser.Browser.LOGD_ENABLED;

    private static final String ACTION_PRELOAD = "android.intent.action.PRELOAD";
    static final String EXTRA_PRELOAD_ID = "preload_id";
    static final String EXTRA_PRELOAD_DISCARD = "preload_discard";
    static final String EXTRA_SEARCHBOX_CANCEL = "searchbox_cancel";
    static final String EXTRA_SEARCHBOX_SETQUERY = "searchbox_query";

    private ConnectivityManager mConnectivityManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (LOGD_ENABLED) Log.d(LOGTAG, "received intent " + intent);
        if (isPreloadEnabledOnCurrentNetwork(context) &&
                intent.getAction().equals(ACTION_PRELOAD)) {
            handlePreload(context, intent);
        }
    }

    private boolean isPreloadEnabledOnCurrentNetwork(Context context) {
        String preload = BrowserSettings.getInstance().getPreloadEnabled();
        if (LOGD_ENABLED) Log.d(LOGTAG, "Preload setting: " + preload);
        if (BrowserSettings.getPreloadAlwaysPreferenceString(context).equals(preload)) {
            return true;
        } else if (BrowserSettings.getPreloadOnWifiOnlyPreferenceString(context).equals(preload)) {
            boolean onWifi = isOnWifi(context);
            if (LOGD_ENABLED) Log.d(LOGTAG, "on wifi:" + onWifi);
            return onWifi;
        } else {
            return false;
        }
    }

    private boolean isOnWifi(Context context) {
        if (mConnectivityManager == null) {
            mConnectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo ni = mConnectivityManager.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        }
        switch (ni.getType()) {
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_WIMAX: // separate case for this?
                return false;
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_ETHERNET:
            case ConnectivityManager.TYPE_BLUETOOTH:
                return true;
            default:
                return false;
        }
    }

    private void handlePreload(Context context, Intent i) {
        String url = UrlUtils.smartUrlFilter(i.getData());
        String id = i.getStringExtra(EXTRA_PRELOAD_ID);
        Map<String, String> headers = null;
        if (id == null) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Preload request has no " + EXTRA_PRELOAD_ID);
            return;
        }
        if (i.getBooleanExtra(EXTRA_PRELOAD_DISCARD, false)) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Got " + id + " preload discard request");
            Preloader.getInstance().discardPreload(id);
        } else if (i.getBooleanExtra(EXTRA_SEARCHBOX_CANCEL, false)) {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Got " + id + " searchbox cancel request");
            Preloader.getInstance().cancelSearchBoxPreload(id);
        } else {
            if (LOGD_ENABLED) Log.d(LOGTAG, "Got " + id + " preload request for " + url);
            if (url != null && url.startsWith("http")) {
                final Bundle pairs = i.getBundleExtra(Browser.EXTRA_HEADERS);
                if (pairs != null && !pairs.isEmpty()) {
                    Iterator<String> iter = pairs.keySet().iterator();
                    headers = new HashMap<String, String>();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        headers.put(key, pairs.getString(key));
                    }
                }
            }
            String sbQuery = i.getStringExtra(EXTRA_SEARCHBOX_SETQUERY);
            if (url != null) {
                if (LOGD_ENABLED){
                    Log.d(LOGTAG, "Preload request(" + id + ", " + url + ", " +
                            headers + ", " + sbQuery + ")");
                }
                Preloader.getInstance().handlePreloadRequest(id, url, headers, sbQuery);
            }
        }
    }

}
