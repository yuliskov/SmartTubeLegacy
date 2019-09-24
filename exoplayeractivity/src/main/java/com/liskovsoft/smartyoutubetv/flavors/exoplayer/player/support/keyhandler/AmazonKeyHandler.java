package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.keyhandler;

import android.app.Activity;
import android.view.KeyEvent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.PlayerInterface;

import java.util.HashMap;

public class AmazonKeyHandler extends KeyHandler {
    public AmazonKeyHandler(Activity activity, PlayerInterface playerFragment) {
        super(activity, playerFragment);

        HashMap<Integer, Integer> additionalMapping = new HashMap<>();
        additionalMapping.put(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD, KeyEvent.KEYCODE_MEDIA_NEXT);
        additionalMapping.put(KeyEvent.KEYCODE_MEDIA_REWIND, KeyEvent.KEYCODE_MEDIA_PREVIOUS);

        setAdditionalMapping(additionalMapping);
    }
}
