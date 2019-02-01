package com.liskovsoft.smartyoutubetv.misc;

import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;

public class BrowserKeysTranslator {
    private static final String TAG = BrowserKeysTranslator.class.getSimpleName();
    private static final KeyEvent EMPTY_EVENT = new KeyEvent(0, 0);
    private static final int UNDEFINED = 0;
    private int mDownFired = 0;

    /**
     * Ignore non-paired key up events
     *
     * @param event event
     * @return is ignored
     */
    private boolean isEventIgnored(KeyEvent event) {
        mDownFired = mDownFired < 0 ? 0 : mDownFired; // do reset sometimes

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mDownFired++;
            return false;
        }

        if (event.getAction() == KeyEvent.ACTION_UP && mDownFired > 0) {
            mDownFired--;
            return false;
        }

        return true;
    }

    public KeyEvent doTranslateKeys(KeyEvent event) {
        if (isEventIgnored(event)) {
            Log.d(TAG, "Event is ignored: " + event);
            return EMPTY_EVENT;
        }

        int toKeyCode = UNDEFINED;

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BUTTON_B:
            case KeyEvent.KEYCODE_BACK:
                toKeyCode = KeyEvent.KEYCODE_ESCAPE;
                break;
            case KeyEvent.KEYCODE_MENU:
                toKeyCode = KeyEvent.KEYCODE_G; // menu to guide
                break;
            case KeyEvent.KEYCODE_NUMPAD_ENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                toKeyCode = KeyEvent.KEYCODE_ENTER;
                break;
            case KeyEvent.KEYCODE_BUTTON_Y:
                toKeyCode = KeyEvent.KEYCODE_SEARCH;
                break;
        }

        return translate(event, toKeyCode);
    }

    private KeyEvent translate(KeyEvent origin, int toKeyCode) {
        if (toKeyCode == UNDEFINED) {
            Log.d(TAG, "No need to translate: " + origin);
            return origin;
        }

        Log.d(TAG, "Translating from " + origin.getKeyCode() + " to " + toKeyCode);

        return new KeyEvent(
                origin.getDownTime(),
                origin.getEventTime(),
                origin.getAction(),
                toKeyCode,
                origin.getRepeatCount(),
                origin.getMetaState(),
                origin.getDeviceId(),
                origin.getScanCode(),
                origin.getFlags(),
                origin.getSource()
        );
    }
}
