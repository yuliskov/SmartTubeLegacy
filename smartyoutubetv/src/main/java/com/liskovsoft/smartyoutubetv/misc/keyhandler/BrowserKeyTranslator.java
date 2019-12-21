package com.liskovsoft.smartyoutubetv.misc.keyhandler;

import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.keytranslator.KeyTranslator;

import java.util.HashMap;
import java.util.Map;

public class BrowserKeyTranslator extends KeyTranslator {
    public static KeyTranslator create() {
        if (CommonApplication.getPreferences().getAltPlayerMappingEnabled()) {
            return new AltMediaBrowserKeyTranslator();
        } else {
            return new MediaBrowserKeyTranslator();
        }
    }

    @Override
    protected Map<Integer, Integer> getKeyMapping() {
        Map<Integer, Integer> keyMapping = new HashMap<>();
        keyMapping.put(KeyEvent.KEYCODE_BUTTON_B, KeyEvent.KEYCODE_ESCAPE);
        keyMapping.put(KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE);
        keyMapping.put(KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_G); // menu to guide
        keyMapping.put(KeyEvent.KEYCODE_NUMPAD_ENTER, KeyEvent.KEYCODE_ENTER);
        keyMapping.put(KeyEvent.KEYCODE_BUTTON_A, KeyEvent.KEYCODE_ENTER);
        keyMapping.put(KeyEvent.KEYCODE_BUTTON_Y, KeyEvent.KEYCODE_SEARCH);

        return keyMapping;
    }
}
