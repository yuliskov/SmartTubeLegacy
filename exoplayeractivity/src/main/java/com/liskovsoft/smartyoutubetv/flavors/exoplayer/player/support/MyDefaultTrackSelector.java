package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
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
    protected TrackSelection selectVideoTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports,
                                              Parameters params, @Nullable Factory adaptiveTrackSelectionFactory) throws ExoPlaybackException {

        MyFormat format = mStateManager.findProperVideoFormat(groups);

        if (format != null) {
            setParameters(buildUponParameters().setSelectionOverride(
                    ExoPlayerFragment.RENDERER_INDEX_VIDEO,
                    groups,
                    new SelectionOverride(format.pair.first, format.pair.second)
            ));
        }

        return super.selectVideoTrack(groups, formatSupports, mixedMimeTypeAdaptationSupports, params, adaptiveTrackSelectionFactory);
    }
}
