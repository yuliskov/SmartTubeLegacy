package com.liskovsoft.smartyoutubetv.misc.keyhandler;

import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.misc.SmartUtils;

import java.util.Map;

public class MediaBrowserKeyTranslator extends BrowserKeyTranslator {
    private static final String KEY_PRESS_MESSAGE = "key_press_message";

    @Override
    public KeyEvent doTranslateKeys(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                sendMessage(SmartUtils.KEYCODE_MEDIA_PLAY_PAUSE, event);
                break;
            case KeyEvent.KEYCODE_MEDIA_STOP:
                sendMessage(SmartUtils.KEYCODE_MEDIA_STOP, event);
                break;
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                sendMessage(SmartUtils.KEYCODE_MEDIA_FAST_FORWARD, event);
                break;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                sendMessage(SmartUtils.KEYCODE_MEDIA_REWIND, event);
            case KeyEvent.KEYCODE_MEDIA_NEXT:
            case KeyEvent.KEYCODE_CHANNEL_DOWN:
                sendMessage(SmartUtils.KEYCODE_MEDIA_NEXT, event);
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
            case KeyEvent.KEYCODE_CHANNEL_UP:
                sendMessage(SmartUtils.KEYCODE_MEDIA_PREVIOUS, event);
                break;
        }

        return super.doTranslateKeys(event);
    }

    private void sendMessage(String keyCode, KeyEvent event) {
        SmartUtils.sendMessage(
                KEY_PRESS_MESSAGE,
                SmartUtils.toJsonString("keyCode", keyCode, "action", event.getAction() == KeyEvent.ACTION_DOWN ? SmartUtils.KEY_DOWN : SmartUtils.KEY_UP));
    }

    @Override
    protected Map<Integer, Integer> getKeyMapping() {
        Map<Integer, Integer> keyMapping = super.getKeyMapping();

        // triggerEvent($('#watch'), 'keydown', 228);
        // triggerEvent($('.scrubber'), 'keydown', 228);

        // FF
        // triggerEvent($$('.focused'), 'keydown', 228);

        // P/P
        // triggerEvent($$('.focused'), 'keyup', 179);

        // test
        //keyMapping.put(KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_MEDIA_PLAY);

        // disable media keys
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, KeyEvent.KEYCODE_UNKNOWN);
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_PLAY, KeyEvent.KEYCODE_UNKNOWN);
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_STOP, KeyEvent.KEYCODE_UNKNOWN);
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD, KeyEvent.KEYCODE_UNKNOWN);
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_REWIND, KeyEvent.KEYCODE_UNKNOWN);
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_NEXT, KeyEvent.KEYCODE_UNKNOWN);
        keyMapping.put(KeyEvent.KEYCODE_MEDIA_PREVIOUS, KeyEvent.KEYCODE_UNKNOWN);

        return keyMapping;
    }
}
