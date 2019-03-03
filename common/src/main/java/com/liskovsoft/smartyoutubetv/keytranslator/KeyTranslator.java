package com.liskovsoft.smartyoutubetv.keytranslator;

import android.view.KeyEvent;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.Map;

public abstract class KeyTranslator {
    private static final String TAG = KeyTranslator.class.getSimpleName();
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

        Map<Integer, Integer> keyMapping = getKeyMapping();
        Integer outKey = keyMapping.get(event.getKeyCode());

        if (outKey != null) {
            toKeyCode = outKey;
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

    protected abstract Map<Integer, Integer> getKeyMapping();
}
