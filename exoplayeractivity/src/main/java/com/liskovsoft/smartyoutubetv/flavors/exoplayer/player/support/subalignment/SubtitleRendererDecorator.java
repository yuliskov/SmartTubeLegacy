package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.subalignment;

import android.graphics.Color;
import android.graphics.Typeface;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.liskovsoft.sharedutils.mylogger.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * My wrapper<br/>
 * Main intent to bring subtitle alignment on some videos
 */
public class SubtitleRendererDecorator implements TextOutput {
    private static final String TAG = SubtitleRendererDecorator.class.getSimpleName();
    private final TextOutput mTextRendererOutput;
    private static final int COLOR_DARK_GREY = Color.argb(255, 43, 43, 43);
    private static final int COLOR_LIGHT_GREY = Color.argb(255, 218, 218, 218);
    private static final int COLOR_BLACK = Color.argb(255, 0, 0, 0);
    private static final int COLOR_YELLOW = Color.argb(255, 255, 255, 0);
    private static final int COLOR_GREEN = Color.argb(255, 0, 255, 0);

    public SubtitleRendererDecorator(TextOutput textRendererOutput) {
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

    public static void configureSubtitleView(PlayerView playerView) {
        if (playerView != null) {
            SubtitleView subtitleView = playerView.getSubtitleView();

            if (subtitleView != null) {
                // disable default style
                subtitleView.setApplyEmbeddedStyles(false);

                int defaultSubtitleColor = COLOR_YELLOW;
                int outlineColor = COLOR_BLACK;
                CaptionStyleCompat style =
                        new CaptionStyleCompat(defaultSubtitleColor,
                                Color.TRANSPARENT, Color.TRANSPARENT,
                                CaptionStyleCompat.EDGE_TYPE_OUTLINE,
                                outlineColor, Typeface.DEFAULT);
                subtitleView.setStyle(style);
            }
        }
    }
}
