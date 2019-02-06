package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection.Definition;
import com.google.android.exoplayer2.trackselection.TrackSelection.Factory;

public class MyDefaultTrackSelector extends DefaultTrackSelector {
    public MyDefaultTrackSelector(Factory trackSelectionFactory) {
        super(trackSelectionFactory);
    }

    @Nullable
    @Override
    protected Definition selectVideoTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports, Parameters params,
                                          boolean enableAdaptiveTrackSelection) throws ExoPlaybackException {
        Definition track = super.selectVideoTrack(groups, formatSupports, mixedMimeTypeAdaptationSupports, params, enableAdaptiveTrackSelection);
        return track;
    }

    @Nullable
    @Override
    protected Pair<Definition, Integer> selectTextTrack(TrackGroupArray groups, int[][] formatSupport, Parameters params) throws ExoPlaybackException {
        return super.selectTextTrack(groups, formatSupport, params);
    }
}
