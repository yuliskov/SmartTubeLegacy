package com.liskovsoft.smartyoutubetv.misc;

import android.view.KeyEvent;

public class KeysTranslator {
    private boolean mDownFired;
    private boolean mDisable;

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
            return event;
        }

        if (isEventIgnored(event)) {
            return new KeyEvent(0, 0);
        }

        event = translateBackToEscape(event);
        event = translateMenuToGuide(event);
        event = translateNumpadEnterToEnter(event);
        event = translateButtonAToEnter(event);
        return event;
    }

    private KeyEvent translateButtonAToEnter(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BUTTON_A) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_ENTER);
        }
        return event;
    }

    private KeyEvent translateNumpadEnterToEnter(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_NUMPAD_ENTER) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_ENTER);
        }
        return event;
    }

    private KeyEvent translateBackToEscape(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_ESCAPE);
        }
        return event;
    }

    private KeyEvent translateMenuToGuide(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            // pay attention, you must pass action_up instead of action_down
            event = new KeyEvent(event.getAction(), KeyEvent.KEYCODE_G);
        }
        return event;
    }

    public void disable() {
        mDisable = true;
    }
}
