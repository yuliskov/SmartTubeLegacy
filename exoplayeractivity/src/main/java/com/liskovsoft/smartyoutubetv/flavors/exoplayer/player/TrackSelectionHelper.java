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
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
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
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Helper class for displaying track selection dialogs.
 */
/* package */ final class TrackSelectionHelper implements View.OnClickListener, DialogInterface.OnClickListener {

    private static final TrackSelection.Factory FIXED_FACTORY = new FixedTrackSelection.Factory();
    private static final TrackSelection.Factory RANDOM_FACTORY = new RandomTrackSelection.Factory();
    private static final int VIDEO_GROUP_INDEX = 0; // is video

    private final MappingTrackSelector mSelector;
    private final TrackSelection.Factory mAdaptiveTrackSelectionFactory;

    private MappedTrackInfo mTrackInfo;
    private int mRendererIndex;
    private TrackGroupArray mTrackGroups;
    private boolean[] mTrackGroupsAdaptive;
    private boolean mIsDisabled;
    private SelectionOverride mOverride;

    private CheckedTextView mDisableView;
    private CheckedTextView mDefaultView;
    private CheckedTextView mEnableRandomAdaptationView;
    private CheckedTextView mAutoframerateView;
    private CheckedTextView mHideErrorsView;
    private CheckedTextView[][] mTrackViews;
    private AlertDialog mAlertDialog;
    private Context mContext;
    private PlayerCoreFragment mPlayerFragment;

    private class TrackViewComparator implements Comparator<CheckedTextView> {
        @Override
        public int compare(CheckedTextView view1, CheckedTextView view2) {
            Format format1 = (Format) view1.getTag(R.string.track_view_format);
            Format format2 = (Format) view2.getTag(R.string.track_view_format);

            // sort subtitles by language code
            if (format1.language != null) {
                return format1.language.compareTo(format2.language);
            }

            int leftVal = format2.width + (int) format2.frameRate + (format2.codecs.contains("avc") ? 31 : 0);
            int rightVal = format1.width + (int) format1.frameRate + (format1.codecs.contains("avc") ? 31 : 0);

            int delta = leftVal - rightVal;
            if (delta == 0) {
                return format2.bitrate - format1.bitrate;
            }

            return leftVal - rightVal;
        }
    }

    /**
     * @param selector                      The track selector.
     * @param adaptiveTrackSelectionFactory A factory for adaptive {@link TrackSelection}s, or null
     *                                      if the selection helper should not support adaptive tracks.
     */
    public TrackSelectionHelper(MappingTrackSelector selector, TrackSelection.Factory adaptiveTrackSelectionFactory) {
        this.mSelector = selector;
        this.mAdaptiveTrackSelectionFactory = adaptiveTrackSelectionFactory;
    }

    /**
     * Shows the selection dialog for a given renderer.
     *
     * @param fragment      The parent activity.
     * @param title         The dialog's title.
     * @param trackInfo     The current track information.
     * @param rendererIndex The index of the renderer.
     */
    public void showSelectionDialog(PlayerCoreFragment fragment, CharSequence title, MappedTrackInfo trackInfo, int rendererIndex) {
        this.mTrackInfo = trackInfo;
        this.mRendererIndex = rendererIndex;
        mContext = fragment.getActivity();
        mPlayerFragment = fragment;

        mTrackGroups = trackInfo.getTrackGroups(rendererIndex);
        mTrackGroupsAdaptive = new boolean[mTrackGroups.length];
        for (int i = 0; i < mTrackGroups.length; i++) {
            mTrackGroupsAdaptive[i] = mAdaptiveTrackSelectionFactory != null && trackInfo.getAdaptiveSupport(rendererIndex, i, false) !=
                    RendererCapabilities.ADAPTIVE_NOT_SUPPORTED && mTrackGroups.get(i).length > 1;
        }
        mIsDisabled = mSelector.getRendererDisabled(rendererIndex);
        mOverride = mSelector.getSelectionOverride(rendererIndex, mTrackGroups);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppDialog);
        mAlertDialog = builder.setCustomTitle(createCustomTitle(builder.getContext(), title))
                .setView(buildView(builder.getContext())).create();
        mAlertDialog.show();
    }

    private View createCustomTitle(Context context, CharSequence title) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleView = inflater.inflate(R.layout.dialog_custom_title, null);
        TextView textView = titleView.findViewById(R.id.title);
        textView.setText(title);
        return titleView;
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
        mHideErrorsView = (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_multi, root, false);
        mHideErrorsView.setBackgroundResource(selectableItemBackgroundResourceId);
        mHideErrorsView.setText(R.string.hide_playback_errors);
        mHideErrorsView.setFocusable(true);
        mHideErrorsView.setOnClickListener(this);
        mHideErrorsView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));
        if (mRendererIndex == VIDEO_GROUP_INDEX) { // is video
            root.addView(mHideErrorsView);
            root.addView(inflater.inflate(R.layout.list_divider, root, false));
        }

        // View for Autoframerate checkbox.
        mAutoframerateView = (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_multi, root, false);
        mAutoframerateView.setBackgroundResource(selectableItemBackgroundResourceId);
        mAutoframerateView.setText(R.string.enable_autoframerate);
        mAutoframerateView.setFocusable(true);
        mAutoframerateView.setOnClickListener(this);
        mAutoframerateView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));
        if (mRendererIndex == VIDEO_GROUP_INDEX) { // is video
            root.addView(mAutoframerateView);
            root.addView(inflater.inflate(R.layout.list_divider, root, false));
        }

        // View for disabling the renderer.
        mDisableView = (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
        mDisableView.setBackgroundResource(selectableItemBackgroundResourceId);
        mDisableView.setText(R.string.selection_disabled);
        mDisableView.setFocusable(true);
        mDisableView.setOnClickListener(this);
        mDisableView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));
        mDisableView.setVisibility(View.GONE);
        root.addView(mDisableView);

        // View for clearing the override to allow the selector to use its default selection logic.
        mDefaultView = (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
        mDefaultView.setBackgroundResource(selectableItemBackgroundResourceId);
        mDefaultView.setText(R.string.selection_default);
        mDefaultView.setFocusable(true);
        mDefaultView.setOnClickListener(this);
        mDefaultView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));
        root.addView(inflater.inflate(R.layout.list_divider, root, false));
        root.addView(mDefaultView); // Auto check box

        //////////// MERGE TRACKS FROM DIFFERENT CODECS ////////////

        // divider
        root.addView(inflater.inflate(R.layout.list_divider, root, false));

        // Per-track views.
        boolean haveSupportedTracks = false;
        boolean haveAdaptiveTracks = false;
        mTrackViews = new CheckedTextView[mTrackGroups.length][];
        Set<CheckedTextView> sortedViewList = new TreeSet<>(new TrackViewComparator());
        for (int groupIndex = 0; groupIndex < mTrackGroups.length; groupIndex++) {
            TrackGroup group = mTrackGroups.get(groupIndex);
            mTrackViews[groupIndex] = new CheckedTextView[group.length];

            for (int trackIndex = 0; trackIndex < group.length; trackIndex++) {
                boolean groupIsAdaptive = mTrackGroupsAdaptive[groupIndex];
                haveAdaptiveTracks |= groupIsAdaptive;
                haveSupportedTracks = true;
                Format format = group.getFormat(trackIndex);

                CheckedTextView trackView = (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_single, root, false);
                trackView.setBackgroundResource(selectableItemBackgroundResourceId);
                trackView.setText(PlayerUtil.buildTrackNameShort(format));
                trackView.setFocusable(true);
                trackView.setTag(Pair.create(groupIndex, trackIndex));
                trackView.setTag(R.string.track_view_format, format);
                trackView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));
                trackView.setOnClickListener(this);
                mTrackViews[groupIndex][trackIndex] = trackView;
                if (PlayerUtil.isPreferredFormat(context, format)) {
                    sortedViewList.add(trackView);
                }
            }
        }

        for (CheckedTextView trackView : sortedViewList) {
            root.addView(trackView);
        }

        //////////// END MERGE TRACKS FROM DIFFERENT CODECS ////////////

        if (!haveSupportedTracks) {
            // Indicate that the default selection will be nothing.
            mDefaultView.setText(R.string.selection_default_none);
        } else if (haveAdaptiveTracks) { // NOTE: adaptation is performed between selected tracks (you must select more than one)
            // View for using random adaptation.
            mEnableRandomAdaptationView = (CheckedTextView) inflater.inflate(R.layout.dialog_check_item_multi, root, false);
            mEnableRandomAdaptationView.setBackgroundResource(selectableItemBackgroundResourceId);
            mEnableRandomAdaptationView.setText(R.string.enable_random_adaptation);
            mEnableRandomAdaptationView.setOnClickListener(this);
            mEnableRandomAdaptationView.setFocusable(true);
            mEnableRandomAdaptationView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));
            View divider = inflater.inflate(R.layout.list_divider, root, false);
            root.addView(divider);
            root.addView(mEnableRandomAdaptationView);

            // it's useless: user can't select more than one track at one time
            divider.setVisibility(View.GONE);
            mEnableRandomAdaptationView.setVisibility(View.GONE);
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
        AutoFrameRateManager autoFrameRateManager = ((ExoPlayerFragment) mPlayerFragment).getAutoFrameRateManager();
        mAutoframerateView.setChecked(autoFrameRateManager.getEnabled());

        mHideErrorsView.setChecked(mPlayerFragment.getHidePlaybackErrors());

        mDisableView.setChecked(mIsDisabled);
        mDefaultView.setChecked(!mIsDisabled && mOverride == null);
        for (int i = 0; i < mTrackViews.length; i++) {
            for (int j = 0; j < mTrackViews[i].length; j++) {
                mTrackViews[i][j].setChecked(mOverride != null && mOverride.groupIndex == i && mOverride.containsTrack(j));
            }
        }
        if (mEnableRandomAdaptationView != null) {
            boolean enableView = !mIsDisabled && mOverride != null && mOverride.length > 1;
            mEnableRandomAdaptationView.setEnabled(enableView);
            mEnableRandomAdaptationView.setFocusable(enableView);
            if (enableView) {
                mEnableRandomAdaptationView.setChecked(!mIsDisabled && mOverride.factory instanceof RandomTrackSelection.Factory);
            }
        }
    }

    // DialogInterface.OnClickListener

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mSelector.setRendererDisabled(mRendererIndex, mIsDisabled);
        if (mOverride != null) {
            mSelector.setSelectionOverride(mRendererIndex, mTrackGroups, mOverride);
        } else {
            mSelector.clearSelectionOverrides(mRendererIndex); // Auto quality button selected
        }
        ((ExoPlayerFragment) mPlayerFragment).retryIfNeeded();
    }

    // View.OnClickListener
    @Override
    public void onClick(View view) {
        if (view == mDisableView) {
            mIsDisabled = true;
            mOverride = null;
        } else if (view == mDefaultView) { // Auto quality button selected
            mIsDisabled = false;
            mOverride = null;
        } else if (view == mEnableRandomAdaptationView) {
            setOverride(mOverride.groupIndex, mOverride.tracks, !mEnableRandomAdaptationView.isChecked());
        } else if (view == mAutoframerateView) {
            boolean checked = mAutoframerateView.isChecked();
            AutoFrameRateManager autoFrameRateManager = ((ExoPlayerFragment) mPlayerFragment).getAutoFrameRateManager();
            autoFrameRateManager.setEnabled(!checked);
        } else if (view == mHideErrorsView) {
            boolean checked = mHideErrorsView.isChecked();
            ExoPlayerFragment player = ((ExoPlayerFragment) mPlayerFragment);
            player.setHidePlaybackErrors(!checked);
        } else { // change quality
            mIsDisabled = false;
            @SuppressWarnings("unchecked") Pair<Integer, Integer> tag = (Pair<Integer, Integer>) view.getTag();
            if (tag == null) {
                return;
            }
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
                mOverride = new SelectionOverride(FIXED_FACTORY, groupIndex, trackIndex);
            }
        }
        // Update the views with the new state.
        updateViews();

        // save immediately
        onClick(null, 0);
    }

    private void setOverride(int group, int[] tracks, boolean enableRandomAdaptation) {
        TrackSelection.Factory factory = tracks.length == 1 ? FIXED_FACTORY : (enableRandomAdaptation ? RANDOM_FACTORY : mAdaptiveTrackSelectionFactory);
        mOverride = new SelectionOverride(factory, group, tracks);
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
