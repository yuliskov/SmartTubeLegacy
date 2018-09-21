package com.liskovsoft.smartyoutubetv.injectors;

import android.util.Log;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.AssetFileInjectEvent;
import com.squareup.otto.Subscribe;

import java.util.HashSet;

final class AssetFileInjectWatcher {
    private static final String TAG = AssetFileInjectWatcher.class.getSimpleName();
    private static AssetFileInjectWatcher sInstance;
    private HashSet<Listener> mListeners = new HashSet<>();

    public static AssetFileInjectWatcher instance() {
        if (sInstance == null) {
            sInstance = new AssetFileInjectWatcher();
        }
        return sInstance;
    }

    public interface Listener {
        void onAssetFileInjectEvent(String fileName);
    }

    private AssetFileInjectWatcher() {
        Browser.getBus().register(this);
    }

    @Subscribe
    public void onAssetFileInjectEvent(AssetFileInjectEvent event) {
        final String fileName = event.getFileName();
        final String targetHash = event.getListenerHash();
        Log.d(TAG, "Injecting asset: " + fileName);

        for (Listener listener : mListeners) {
            String hash = String.valueOf(listener.hashCode());
            if (hash.equals(targetHash))
                listener.onAssetFileInjectEvent(fileName);
        }
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }
}
