package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers;

import java.util.HashMap;
import java.util.Map;

public class ExtendedDataHolder {

    private static ExtendedDataHolder ourInstance = new ExtendedDataHolder();

    private final Map<String, Object> extras = new HashMap<>();

    private ExtendedDataHolder() {
    }

    public static ExtendedDataHolder getInstance() {
        return ourInstance;
    }

    public void putExtra(String name, Object object) {
        extras.put(name, object);
    }

    public Object getExtra(String name) {
        return extras.get(name);
    }

    public boolean hasExtra(String name) {
        return extras.containsKey(name);
    }

    public void clear() {
        extras.clear();
    }

}
