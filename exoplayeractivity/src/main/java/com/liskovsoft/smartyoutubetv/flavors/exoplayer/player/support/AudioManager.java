package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import com.liskovsoft.sharedutils.helpers.Helpers;

public class AudioManager {
    public void setAudioDelay(String delay) {
        if (Helpers.isNumeric(delay)) {
            try {
                int delayMs = Integer.parseInt(delay);
                setAudioDelay(delayMs);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAudioDelay(int delay) {
        
    }
}
