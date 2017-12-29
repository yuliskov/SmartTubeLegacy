/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom.ExoPreferences;

import java.util.Arrays;

/**
 * Helper class for displaying track selection dialogs.
 */
/* package */ final class TrackSelectionHelper implements View.OnClickListener, DialogInterface.OnClickListener {

    private static final TrackSelection.Factory FIXED_FACTORY = new FixedTrackSelection.Factory();
    private static final TrackSelection.Factory RANDOM_FACTORY = new RandomTrackSelection.Factory();
    private static final int TEXT_SIZE_DP = 15;
    private static final int VIDEO_GROUP_INDEX = 0; // is video

    private final MappingTrackSelector selector;
    private final TrackSelection.Factory adaptiveTrackSelectionFactory;

    private MappedTrackInfo trackInfo;
    private int rendererIndex;
    private TrackGroupArray trackGroups;
    private boolean[] trackGroupsAdaptive;
    private boolean isDisabled;
    private SelectionOverride override;

    private CheckedTextView disableView;
    private CheckedTextView defaultView;
    private CheckedTextView enableRandomAdaptationView;
    private CheckedTextView autoframerateView;
    private CheckedTextView[][] trackViews;
    private AlertDialog alertDialog;
    private Context context;

    /**
     * @param selector                      The track selector.
     * @param adaptiveTrackSelectionFactory A factory for adaptive {@link TrackSelection}s, or null
     *                                      if the selection helper should not support adaptive tracks.
     */
    public TrackSelectionHelper(MappingTrackSelector selector, TrackSelection.Factory adaptiveTrackSelectionFactory) {
        this.selector = selector;
        this.adaptiveTrackSelectionFactory = adaptiveTrackSelectionFactory;
    }

    /**
     * Shows the selection dialog for a given renderer.
     *
     * @param activity      The parent activity.
     * @param title         The dialog's title.
     * @param trackInfo     The current track information.
     * @param rendererIndex The index of the renderer.
     */
    public void showSelectionDialog(Activity activity, CharSequence title, MappedTrackInfo trackInfo, int rendererIndex) {
        this.trackInfo = trackInfo;
        this.rendererIndex = rendererIndex;
        context = activity;

        trackGroups = trackInfo.getTrackGroups(rendererIndex);
        trackGroupsAdaptive = new boolean[trackGroups.length];
        for (int i = 0; i < trackGroups.length; i++) {
            trackGroupsAdaptive[i] = adaptiveTrackSelectionFactory != null && trackInfo.getAdaptiveSupport(rendererIndex, i, false) !=
                    RendererCapabilities.ADAPTIVE_NOT_SUPPORTED && trackGroups.get(i).length > 1;
        }
        isDisabled = selector.getRendererDisabled(rendererIndex);
        override = selector.getSelectionOverride(rendererIndex, trackGroups);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        alertDialog = builder.setTitle(title).setView(buildView(builder.getContext())).create();
        alertDialog.show();
    }
    
    @SuppressLint("InflateParams")
    private View buildView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.track_selection_dialog, null);
        ViewGroup root = (ViewGroup) view.findViewById(R.id.root);

        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();

        // View for Autoframerate checkbox.
        autoframerateView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_multiple_choice, root, false);
        autoframerateView.setBackgroundResource(selectableItemBackgroundResourceId);
        autoframerateView.setText(R.string.enable_autoframerate);
        autoframerateView.setFocusable(true);
        autoframerateView.setOnClickListener(this);
        autoframerateView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DP);
        if (rendererIndex == VIDEO_GROUP_INDEX) { // is video
            root.addView(autoframerateView);
            root.addView(inflater.inflate(R.layout.list_divider, root, false));
        }

        // View for disabling the renderer.
        disableView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_single_choice, root, false);
        disableView.setBackgroundResource(selectableItemBackgroundResourceId);
        disableView.setText(R.string.selection_disabled);
        disableView.setFocusable(true);
        disableView.setOnClickListener(this);
        disableView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DP);
        root.addView(disableView);

        // View for clearing the override to allow the selector to use its default selection logic.
        defaultView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_single_choice, root, false);
        defaultView.setBackgroundResource(selectableItemBackgroundResourceId);
        defaultView.setText(R.string.selection_default);
        defaultView.setFocusable(true);
        defaultView.setOnClickListener(this);
        defaultView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DP);
        root.addView(inflater.inflate(R.layout.list_divider, root, false));
        root.addView(defaultView); // Auto check box

        //////////// MERGE TRACKS FROM DIFFERENT CODECS ////////////

        // divider
        root.addView(inflater.inflate(R.layout.list_divider, root, false));

        // Per-track views.
        boolean haveSupportedTracks = false;
        boolean haveAdaptiveTracks = false;
        trackViews = new CheckedTextView[trackGroups.length][];
        int maxTracks = 0;
        for (int groupIndex = 0; groupIndex < trackGroups.length; groupIndex++) {
            TrackGroup group = trackGroups.get(groupIndex);
            int maxTracksInGroup = group.length;
            if (maxTracksInGroup > maxTracks) {
                maxTracks = maxTracksInGroup;
            }

            trackViews[groupIndex] = new CheckedTextView[group.length];
        }

        // NOTE: group track by resolution
        int[] trackOffsets = new int[trackGroups.length];
        for (int trackIndex = (maxTracks - 1); trackIndex >= 0; trackIndex--) {
            for (int groupIndex = 0; groupIndex < trackGroups.length; groupIndex++) {
                int newTrackIndex = trackIndex - trackOffsets[groupIndex];
                if (newTrackIndex < 0) {
                    continue;
                }

                TrackGroup group = trackGroups.get(groupIndex);

                if (group.length <= newTrackIndex) { // one of the group have different number of tracks
                    trackOffsets[groupIndex] = maxTracks - group.length;
                    newTrackIndex = trackIndex - trackOffsets[groupIndex];;
                }

                boolean groupIsAdaptive = trackGroupsAdaptive[groupIndex];
                haveAdaptiveTracks |= groupIsAdaptive;
                haveSupportedTracks = true;

                int trackViewLayoutId = groupIsAdaptive ? android.R.layout.simple_list_item_single_choice : android.R.layout
                        .simple_list_item_single_choice;

                int offset = getRelatedTrackOffsets(group, newTrackIndex);

                for (int startOffset = 0; startOffset <= offset; startOffset++) {
                    CheckedTextView trackView = (CheckedTextView) inflater.inflate(trackViewLayoutId, root, false);
                    trackView.setBackgroundResource(selectableItemBackgroundResourceId);
                    int index = newTrackIndex - startOffset;
                    if (index < 0)
                        break;
                    trackView.setText(DemoUtil.buildTrackName(group.getFormat(index)));

                    trackView.setFocusable(true);
                    trackView.setTag(Pair.create(groupIndex, index));
                    trackView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DP);
                    trackView.setOnClickListener(this);
                    trackViews[groupIndex][index] = trackView;
                    root.addView(trackView);
                }

                trackOffsets[groupIndex] += offset;

            }
        }

        //////////// END MERGE TRACKS FROM DIFFERENT CODECS ////////////

        if (!haveSupportedTracks) {
            // Indicate that the default selection will be nothing.
            defaultView.setText(R.string.selection_default_none);
        } else if (haveAdaptiveTracks) {
            // View for using random adaptation.
            enableRandomAdaptationView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_multiple_choice, root, false);
            enableRandomAdaptationView.setBackgroundResource(selectableItemBackgroundResourceId);
            enableRandomAdaptationView.setText(R.string.enable_random_adaptation);
            enableRandomAdaptationView.setOnClickListener(this);
            root.addView(inflater.inflate(R.layout.list_divider, root, false));
            root.addView(enableRandomAdaptationView);
        }

        updateViews();
        return view;
    }

    /**
     * Get the number of tracks with the same resolution.
     * <p>I assume that the tracks already have been sorted in descendants order. <br/>
     * <p>Details: {@code com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.mpdbuilder.MyMPDBuilder}
     * @param group the group
     * @param trackIndex current track in group
     * @return
     */
    private int getRelatedTrackOffsets(TrackGroup group, int trackIndex) {
        int prevHeight = 0;
        int offset = 0;
        for (int i = trackIndex; i > 0; i--) {
            Format format = group.getFormat(i);
            if (prevHeight == 0) {
                prevHeight = format.height;
            } else if (prevHeight == format.height) {
                offset++;
            } else {
                break;
            }
        }
        return offset;
    }

    private void updateViews() {
        AutoFrameRateManager autoFrameRateManager = ((PlayerActivity) context).getAutoFrameRateManager();
        autoframerateView.setChecked(autoFrameRateManager.getEnabled());

        disableView.setChecked(isDisabled);
        defaultView.setChecked(!isDisabled && override == null);
        for (int i = 0; i < trackViews.length; i++) {
            for (int j = 0; j < trackViews[i].length; j++) {
                trackViews[i][j].setChecked(override != null && override.groupIndex == i && override.containsTrack(j));
            }
        }
        if (enableRandomAdaptationView != null) {
            boolean enableView = !isDisabled && override != null && override.length > 1;
            enableRandomAdaptationView.setEnabled(enableView);
            enableRandomAdaptationView.setFocusable(enableView);
            if (enableView) {
                enableRandomAdaptationView.setChecked(!isDisabled && override.factory instanceof RandomTrackSelection.Factory);
            }
        }
    }

    // DialogInterface.OnClickListener

    @Override
    public void onClick(DialogInterface dialog, int which) {
        selector.setRendererDisabled(rendererIndex, isDisabled);
        if (override != null) {
            selector.setSelectionOverride(rendererIndex, trackGroups, override);
        } else {
            selector.clearSelectionOverrides(rendererIndex);
        }
        ((PlayerActivity) context).retryIfNeeded();
    }

    // View.OnClickListener
    @Override
    public void onClick(View view) {
        if (view == disableView) {
            isDisabled = true;
            override = null;
        } else if (view == defaultView) {
            isDisabled = false;
            override = null;
        } else if (view == enableRandomAdaptationView) {
            setOverride(override.groupIndex, override.tracks, !enableRandomAdaptationView.isChecked());
        } else if (view == autoframerateView) {
            boolean checked = autoframerateView.isChecked();
            AutoFrameRateManager autoFrameRateManager = ((PlayerActivity) context).getAutoFrameRateManager();
            autoFrameRateManager.setEnabled(!checked);
        } else { // change quality
            isDisabled = false;
            @SuppressWarnings("unchecked") Pair<Integer, Integer> tag = (Pair<Integer, Integer>) view.getTag();
            int groupIndex = tag.first;
            int trackIndex = tag.second;

            // The group being modified is adaptive and we already have a non-null override.
            // imitate radio button behaviour
            boolean isEnabled = ((CheckedTextView) view).isChecked();
            if (isEnabled) {
                // override = null;
                // item already checked - do nothing
                return;
            } else {
                override = new SelectionOverride(FIXED_FACTORY, groupIndex, trackIndex);
            }
        }
        // Update the views with the new state.
        updateViews();

        // save immediately
        onClick(null, 0);

        // close dialog
        //if (alertDialog != null)
        //    alertDialog.dismiss();
        //alertDialog = null;
    }

    private void setOverride(int group, int[] tracks, boolean enableRandomAdaptation) {
        TrackSelection.Factory factory = tracks.length == 1 ? FIXED_FACTORY : (enableRandomAdaptation ? RANDOM_FACTORY :
                adaptiveTrackSelectionFactory);
        override = new SelectionOverride(factory, group, tracks);
    }

    // Track array manipulation.
    private static int[] getTracksAdding(SelectionOverride override, int addedTrack) {
        int[] tracks = override.tracks;
        tracks = Arrays.copyOf(tracks, tracks.length + 1);
        tracks[tracks.length - 1] = addedTrack;
        return tracks;
    }

    private static int[] getTracksRemoving(SelectionOverride override, int removedTrack) {
        int[] tracks = new int[override.length - 1];
        int trackCount = 0;
        for (int i = 0; i < tracks.length + 1; i++) {
            int track = override.tracks[i];
            if (track != removedTrack) {
                tracks[trackCount++] = track;
            }
        }
        return tracks;
    }
}
