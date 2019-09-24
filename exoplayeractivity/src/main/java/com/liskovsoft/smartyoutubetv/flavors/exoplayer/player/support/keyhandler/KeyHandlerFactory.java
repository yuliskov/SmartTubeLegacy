package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.keyhandler;

import android.app.Activity;
import com.liskovsoft.smartyoutubetv.CommonApplication;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.PlayerInterface;

public class KeyHandlerFactory {
    public static KeyHandler create(Activity activity, PlayerInterface playerFragment) {
        if (CommonApplication.getPreferences().getAltPlayerMappingEnabled()) {
            return new AmazonKeyHandler(activity, playerFragment);
        }

        return new KeyHandler(activity, playerFragment);
    }
}
