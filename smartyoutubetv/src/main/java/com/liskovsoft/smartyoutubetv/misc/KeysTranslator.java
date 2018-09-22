package com.liskovsoft.smartyoutubetv.misc;

import android.view.KeyEvent;

public class KeysTranslator {
    private static final KeyEvent EMPTY_EVENT = new KeyEvent(0, 0);
    private boolean mDownFired;
    private boolean mDisable = true;

    /**
     * Ignore non-paired key up events
     *
     * @param event event
     * @return is ignored
     */
    private boolean isEventIgnored(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mDownFired = true;
            return false;
        }

        if (event.getAction() == KeyEvent.ACTION_UP && mDownFired) {
            mDownFired = false;
            return false;
        }

        return true;
    }

    public KeyEvent doTranslateKeys(KeyEvent event) {
        if (mDisable) {
            // exit from loading by pressing escape or back keys
            event = translate(event, KeyEvent.KEYCODE_ESCAPE, KeyEvent.KEYCODE_BACK);
            return event;
        }

        if (isEventIgnored(event)) {
            return EMPTY_EVENT;
        }

        event = translate(event, KeyEvent.KEYCODE_BUTTON_B, KeyEvent.KEYCODE_ESCAPE);
        event = translate(event, KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE);
        event = translate(event, KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_G); // menu to guide
        event = translate(event, KeyEvent.KEYCODE_NUMPAD_ENTER, KeyEvent.KEYCODE_ENTER);
        event = translate(event, KeyEvent.KEYCODE_BUTTON_A, KeyEvent.KEYCODE_ENTER);
        return event;
    }

    private KeyEvent createNewEvent(KeyEvent event, int keyCode) {
        return new KeyEvent(event.getDownTime(), event.getEventTime(), event.getAction(), keyCode, event.getRepeatCount(), event.getMetaState(),
                event.getDeviceId(), event.getScanCode(), event.getFlags(), event.getSource());
    }

    private static KeyEvent translate(KeyEvent origin, int fromKeyCode, int toKeyCode) {
        if (origin.getKeyCode() == fromKeyCode) {
            // pay attention, you must pass action_up instead of action_down
            origin = new KeyEvent(origin.getAction(), toKeyCode);
        }
        return origin;
    }

    public void disable() {
        mDisable = true;
    }

    public void enable() {
        mDisable = false;
    }
}
