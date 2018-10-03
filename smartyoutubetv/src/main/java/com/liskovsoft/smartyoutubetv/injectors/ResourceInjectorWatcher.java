package com.liskovsoft.smartyoutubetv.injectors;

import android.util.Log;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.events.AssetFileInjectEvent;
import com.squareup.otto.Subscribe;

import java.util.HashSet;

final class ResourceInjectorWatcher {
    private static final String TAG = ResourceInjectorWatcher.class.getSimpleName();
    private static ResourceInjectorWatcher sInstance;
    private HashSet<Listener> mListeners = new HashSet<>();

    public static ResourceInjectorWatcher instance() {
        if (sInstance == null) {
            sInstance = new ResourceInjectorWatcher();
        }
        return sInstance;
    }

    public interface Listener {
        void onAssetFileInjectEvent(String fileName);
    }

    private ResourceInjectorWatcher() {
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
