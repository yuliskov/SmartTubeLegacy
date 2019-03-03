package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.subalignment;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * My wrapper<br/>
 * Main intent to bring subtitle alignment on some videos
 */
public class TextRendererDecorator implements TextOutput {
    private static final String TAG = TextRendererDecorator.class.getSimpleName();
    private final TextOutput mTextRendererOutput;

    public TextRendererDecorator(TextOutput textRendererOutput) {
        mTextRendererOutput = textRendererOutput;
    }

    @Override
    public void onCues(List<Cue> cues) {
        Log.d(TAG, cues);
        mTextRendererOutput.onCues(fixAlignment(cues));
    }

    private List<Cue> fixAlignment(List<Cue> cues) {
        List<Cue> result = new ArrayList<>();

        for (Cue cue : cues) {
            if (cue.position == 0 && cue.positionAnchor == Cue.ANCHOR_TYPE_START) { // unaligned sub encountered
                result.add(new Cue(cue.text));
            } else {
                result.add(cue);
            }
        }

        return result;
    }
}
