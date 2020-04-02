package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.subalignment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.liskovsoft.exoplayeractivity.R;
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
    private static final int COLOR_BLACK_TRANSPARENT = Color.argb(100, 0, 0, 0);


    public SubtitleRendererDecorator(TextOutput textRendererOutput) {
        mTextRendererOutput = textRendererOutput;
    }

    @Override
    public void onCues(List<Cue> cues) {
        Log.d(TAG, cues);
        mTextRendererOutput.onCues(forceCenterAlignment(cues));
    }

    private List<Cue> forceCenterAlignment(List<Cue> cues) {
        List<Cue> result = new ArrayList<>();

        for (Cue cue : cues) {
            result.add(new Cue(cue.text)); // sub centered by default

            // CONDITION DOESN'T WORK
            //if (cue.position == 0 && (cue.positionAnchor == Cue.ANCHOR_TYPE_START)) { // unaligned sub encountered
            //    result.add(new Cue(cue.text));
            //} else {
            //    result.add(cue);
            //}
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
                                COLOR_BLACK_TRANSPARENT, Color.TRANSPARENT,
                                CaptionStyleCompat.EDGE_TYPE_OUTLINE,
                                outlineColor, Typeface.DEFAULT);
                subtitleView.setStyle(style);
                float textSize = subtitleView.getContext().getResources().getDimension(R.dimen.subtitle_text_size);
                subtitleView.setFixedTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
        }
    }
}
