package com.liskovsoft.smartyoutubetv.misc.keyhandler;

import android.view.KeyEvent;
import com.liskovsoft.sharedutils.helpers.Helpers;

public class AltMediaBrowserKeyTranslator extends MediaBrowserKeyTranslator {
    @Override
    public KeyEvent doTranslateKeys(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                event = Helpers.newEvent(event, KeyEvent.KEYCODE_MEDIA_NEXT);
                break;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                event = Helpers.newEvent(event, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                break;
        }

        return super.doTranslateKeys(event);
    }
}
