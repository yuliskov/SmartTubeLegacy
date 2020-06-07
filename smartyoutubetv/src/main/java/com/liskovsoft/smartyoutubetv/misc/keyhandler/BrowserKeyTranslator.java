package com.liskovsoft.smartyoutubetv.misc.keyhandler;

import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.keytranslator.KeyTranslator;

import java.util.HashMap;
import java.util.Map;

public class BrowserKeyTranslator extends KeyTranslator {
    private final static Map<Integer, Integer> KEY_MAPPING = new HashMap<>();

    static {
        KEY_MAPPING.put(KeyEvent.KEYCODE_BUTTON_B, KeyEvent.KEYCODE_ESCAPE);
        KEY_MAPPING.put(KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE);
        KEY_MAPPING.put(KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_G); // menu to guide
        KEY_MAPPING.put(KeyEvent.KEYCODE_NUMPAD_ENTER, KeyEvent.KEYCODE_ENTER);
        KEY_MAPPING.put(KeyEvent.KEYCODE_BUTTON_A, KeyEvent.KEYCODE_ENTER);
        KEY_MAPPING.put(KeyEvent.KEYCODE_BUTTON_Y, KeyEvent.KEYCODE_SEARCH);
    }

    public static KeyTranslator create() {
        if (CommonApplication.getPreferences().getAltPlayerMappingEnabled()) {
            return new AltMediaBrowserKeyTranslator();
        } else {
            return new MediaBrowserKeyTranslator();
        }
    }

    @Override
    protected Map<Integer, Integer> getKeyMapping() {
        return KEY_MAPPING;
    }
}
