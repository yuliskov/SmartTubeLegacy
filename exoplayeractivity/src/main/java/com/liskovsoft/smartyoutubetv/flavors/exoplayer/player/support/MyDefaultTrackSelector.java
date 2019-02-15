package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection.Factory;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.trackstate.PlayerStateManagerBase;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.trackstate.PlayerStateManagerBase.MyFormat;

public class MyDefaultTrackSelector extends DefaultTrackSelector {
    private final Context mContext;
    private final PlayerStateManagerBase mStateManager;

    public MyDefaultTrackSelector(Factory trackSelectionFactory, Context context) {
        super(trackSelectionFactory);
        mContext = context;
        mStateManager = new PlayerStateManagerBase(context);
    }

    @Nullable
    @Override
    protected TrackSelection selectVideoTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports,
                                              Parameters params, @Nullable Factory adaptiveTrackSelectionFactory) throws ExoPlaybackException {

        if (!params.hasSelectionOverride(ExoPlayerFragment.RENDERER_INDEX_VIDEO, groups)) {
            restoreVideoTrack(groups);
        }

        return super.selectVideoTrack(groups, formatSupports, mixedMimeTypeAdaptationSupports, params, adaptiveTrackSelectionFactory);
    }

    private void restoreVideoTrack(TrackGroupArray groups) {
        MyFormat format = mStateManager.findProperVideoFormat(groups);

        if (format != null) {
            setParameters(buildUponParameters().setSelectionOverride(
                    ExoPlayerFragment.RENDERER_INDEX_VIDEO,
                    groups,
                    new SelectionOverride(format.pair.first, format.pair.second)
            ));
        }
    }

    private void forceAllFormatsSupport(int[][][] rendererFormatSupports) {
        if (rendererFormatSupports == null) {
            return;
        }

        for (int i = 0; i < rendererFormatSupports.length; i++) {
            if (rendererFormatSupports[i] == null) {
                continue;
            }
            for (int j = 0; j < rendererFormatSupports[i].length; j++) {
                if (rendererFormatSupports[i][j] == null) {
                    continue;
                }
                for (int k = 0; k < rendererFormatSupports[i][j].length; k++) {
                    int supportLevel = rendererFormatSupports[i][j][k];
                    int notSupported = 6;
                    int formatSupported = 7;
                    if (supportLevel == notSupported) {
                        rendererFormatSupports[i][j][k] = formatSupported;
                    }
                }
            }
        }
    }
}
