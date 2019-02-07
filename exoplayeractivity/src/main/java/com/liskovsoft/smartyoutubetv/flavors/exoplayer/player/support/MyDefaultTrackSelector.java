package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection.Definition;
import com.google.android.exoplayer2.trackselection.TrackSelection.Factory;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.PlayerStateManagerBase.MyFormat;

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
    protected Definition selectVideoTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports, Parameters params,
                                          boolean enableAdaptiveTrackSelection) throws ExoPlaybackException {
        MyFormat format = mStateManager.findProperVideoFormat(groups);

        if (format != null) {
            setParameters(buildUponParameters().setSelectionOverride(
                    ExoPlayerFragment.RENDERER_INDEX_VIDEO,
                    groups,
                    new SelectionOverride(format.pair.first, format.pair.second)
            ));
        }

        return super.selectVideoTrack(groups, formatSupports, mixedMimeTypeAdaptationSupports, params, enableAdaptiveTrackSelection);
    }

    @Nullable
    @Override
    protected Pair<Definition, Integer> selectTextTrack(TrackGroupArray groups, int[][] formatSupport, Parameters params) throws ExoPlaybackException {
        MyFormat format = mStateManager.findProperSubtitleFormat(groups);

        if (format != null) {
            setParameters(buildUponParameters().setSelectionOverride(
                    ExoPlayerFragment.RENDERER_INDEX_SUBTITLE,
                    groups,
                    new SelectionOverride(format.pair.first, format.pair.second)
            ));
        }

        return super.selectTextTrack(groups, formatSupport, params);
    }
}
