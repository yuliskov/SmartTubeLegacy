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
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride;
import com.google.android.exoplayer2.trackselection.FixedTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.sharedutils.helpers.MessageHelpers;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.autoframerate.AutoFrameRateManager;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support.ExoPreferences;

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

    private final DefaultTrackSelector mSelector;
    private final TrackSelection.Factory mTrackSelectionFactory;

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
    private CheckedTextView mAutoframerateDelayView;
    private CheckedTextView mHideErrorsView;
    private CheckedTextView[][] mTrackViews;
    private AlertDialog mAlertDialog;
    private Context mContext;
    private ExoPlayerFragment mPlayerFragment;

    private class TrackViewComparator implements Comparator<CheckedTextView> {
        @Override
        public int compare(CheckedTextView view1, CheckedTextView view2) {
            Format format1 = (Format) view1.getTag(R.string.track_view_format);
            Format format2 = (Format) view2.getTag(R.string.track_view_format);

            // sort subtitles by language code
            if (format1.language != null && format2.language != null) {
                return format1.language.compareTo(format2.language);
            }

            int leftVal = format2.width + (int) format2.frameRate + (format2.codecs != null && format2.codecs.contains("avc") ? 31 : 0);
            int rightVal = format1.width + (int) format1.frameRate + (format1.codecs != null && format1.codecs.contains("avc") ? 31 : 0);

            int delta = leftVal - rightVal;
            if (delta == 0) {
                return format2.bitrate - format1.bitrate;
            }

            return leftVal - rightVal;
        }
    }

    /**
     * @param selector                      The track selector.
     * @param trackSelectionFactory A factory for adaptive {@link TrackSelection}s, or null
     *                                      if the selection helper should not support adaptive tracks.
     */
    public TrackSelectionHelper(DefaultTrackSelector selector, TrackSelection.Factory trackSelectionFactory) {
        this.mSelector = selector;
        this.mTrackSelectionFactory = trackSelectionFactory;
    }

    /**
     * Shows the selection dialog for a given renderer.
     *
     * @param fragment      The parent activity.
     * @param title         The dialog's title.
     * @param trackInfo     The current track information.
     * @param rendererIndex The index of the renderer.
     */
    public void showSelectionDialog(ExoPlayerFragment fragment, CharSequence title, MappedTrackInfo trackInfo, int rendererIndex) {
        mTrackInfo = trackInfo;
        mRendererIndex = rendererIndex;
        mContext = fragment.getActivity();
        mPlayerFragment = fragment;

        mTrackGroups = trackInfo.getTrackGroups(rendererIndex);
        mTrackGroupsAdaptive = new boolean[mTrackGroups.length];
        for (int i = 0; i < mTrackGroups.length; i++) {
            mTrackGroupsAdaptive[i] = mTrackSelectionFactory != null && trackInfo.getAdaptiveSupport(rendererIndex, i, false) !=
                    RendererCapabilities.ADAPTIVE_NOT_SUPPORTED && mTrackGroups.get(i).length > 1;
        }
        mIsDisabled = mSelector.getParameters().getRendererDisabled(rendererIndex);
        mOverride = mSelector.getParameters().getSelectionOverride(rendererIndex, mTrackGroups);

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
        ViewGroup root = view.findViewById(R.id.root);

        // Code error checkbox.

        mHideErrorsView = createCheckButton(context, R.string.hide_playback_errors, root);
        mHideErrorsView.setVisibility(View.GONE);

        if (mRendererIndex == ExoPlayerFragment.RENDERER_INDEX_VIDEO) { // is video
            append(mHideErrorsView, root);
            append(createDivider(context, root), root);
        }

        // Autoframerate checkbox.

        mAutoframerateView = createCheckButton(context, R.string.enable_autoframerate, root);
        mAutoframerateView.setVisibility(View.GONE);
        mAutoframerateDelayView = createCheckButton(context, R.string.enable_autoframerate_pause, root);
        mAutoframerateDelayView.setVisibility(View.GONE);

        if (mRendererIndex == ExoPlayerFragment.RENDERER_INDEX_VIDEO) { // is video
            append(mAutoframerateView, root);
            append(mAutoframerateDelayView, root);
            append(createDivider(context, root), root);
        }

        // Disable renderer checkbox.

        mDisableView = createRadioButton(context, R.string.selection_disabled, root);
        mDisableView.setVisibility(View.GONE);
        append(mDisableView, root);


        // View for clearing the override to allow the selector to use its default selection logic.

        mDefaultView = createRadioButton(context, R.string.selection_default, root);
        append(mDefaultView, root);

        //////////// MERGE TRACKS FROM DIFFERENT CODECS ////////////

        // divider
        append(createDivider(context, root), root);

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

                CheckedTextView trackView = createRadioButton(context, PlayerUtil.buildTrackNameShort(format), root);
                trackView.setTag(Pair.create(groupIndex, trackIndex));
                trackView.setTag(R.string.track_view_format, format);

                mTrackViews[groupIndex][trackIndex] = trackView;
                sortedViewList.add(trackView);
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

            mEnableRandomAdaptationView = createCheckButton(context, R.string.enable_random_adaptation, root);
            append(mEnableRandomAdaptationView, root);

            View divider = createDivider(context, root);
            append(divider, root);

            // it's useless: user can't select more than one track at one time
            mEnableRandomAdaptationView.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
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
        AutoFrameRateManager autoFrameRateManager = mPlayerFragment.getAutoFrameRateManager();
        if (autoFrameRateManager != null) {
            mAutoframerateView.setChecked(autoFrameRateManager.isEnabled());
            mAutoframerateDelayView.setChecked(autoFrameRateManager.isDelayEnabled());
        } else {
            mAutoframerateView.setChecked(false);
            mAutoframerateDelayView.setChecked(false);
        }

        mHideErrorsView.setChecked(mPlayerFragment.getHidePlaybackErrors());

        mDisableView.setChecked(mIsDisabled);

        boolean defaultQualitySelected = !mIsDisabled && mOverride == null;
        mDefaultView.setChecked(defaultQualitySelected);
        updateDefaultView();

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
                // TODO: could be a problems here
                //mEnableRandomAdaptationView.setChecked(!mIsDisabled && mOverride.factory instanceof RandomTrackSelection.Factory);
                mEnableRandomAdaptationView.setChecked(!mIsDisabled && mTrackSelectionFactory instanceof RandomTrackSelection.Factory);
            }
        }
    }

    private void updateDefaultView() {
        new Handler(mContext.getMainLooper()).postDelayed(() -> {
            boolean defaultQualitySelected = !mIsDisabled && mOverride == null;

            String title = mContext.getResources().getString(R.string.selection_default);
            String newTitle = String.format("%s %s", title, PlayerUtil.getTrackQualityLabel(mPlayerFragment.getPlayer(), mRendererIndex));

            mDefaultView.setText(defaultQualitySelected ? newTitle : title);
        }, 1_000);
    }

    // DialogInterface.OnClickListener

    @Override
    public void onClick(DialogInterface dialog, int which) {
        applySelection();
    }

    // View.OnClickListener

    @Override
    public void onClick(View view) {
        if (view == mDisableView) {
            mIsDisabled = true;
            mOverride = null;
        } else if (view == mDefaultView) { // Auto quality button selected
            mIsDisabled = false;
            clearOverride();
        } else if (view == mEnableRandomAdaptationView) {
            setOverride(mOverride.groupIndex, mOverride.tracks, !mEnableRandomAdaptationView.isChecked());
        } else if (view == mAutoframerateView) {
            boolean checked = mAutoframerateView.isChecked();
            AutoFrameRateManager autoFrameRateManager = mPlayerFragment.getAutoFrameRateManager();
            if (autoFrameRateManager != null) {
                autoFrameRateManager.setEnabled(!checked);
            }
        } else if (view == mAutoframerateDelayView) {
            boolean checked = mAutoframerateDelayView.isChecked();
            AutoFrameRateManager autoFrameRateManager = mPlayerFragment.getAutoFrameRateManager();
            if (autoFrameRateManager != null) {
                autoFrameRateManager.setDelayEnabled(!checked);
            }
        } else if (view == mHideErrorsView) {
            boolean checked = mHideErrorsView.isChecked();
            ExoPlayerFragment player = mPlayerFragment;
            player.setHidePlaybackErrors(!checked);
        } else { // change quality
            mIsDisabled = false;
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer> tag = (Pair<Integer, Integer>) view.getTag();
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
                setOverride(groupIndex, trackIndex);
            }
        }

        // Update the views with the new state.
        updateViews();

        // save immediately
        applySelection();
    }

    private void applySelection() {
        mSelector.setParameters(mSelector.buildUponParameters().setRendererDisabled(mRendererIndex, mIsDisabled));

        if (mOverride != null) {
            mSelector.setParameters(mSelector.buildUponParameters().setSelectionOverride(mRendererIndex, mTrackGroups, mOverride));
        } else {
            mSelector.setParameters(mSelector.buildUponParameters().clearSelectionOverrides(mRendererIndex)); // Auto quality button selected
        }

        mPlayerFragment.retryIfNeeded();
    }

    private void clearOverride() {
        if (canSwitchFormats()) {
            mOverride = null;
        }
    }

    private void setOverride(int groupIndex, int... tracks) {
        if (canSwitchFormats()) {
            mOverride = new SelectionOverride(groupIndex, tracks);
        }
    }

    private boolean canSwitchFormats() {
        if (mRendererIndex != ExoPlayerFragment.RENDERER_INDEX_VIDEO) {
            return true;
        }

        ExoPreferences prefs = ExoPreferences.instance(mContext);

        if (prefs.getPreferredFormat().equals(ExoPreferences.FORMAT_ANY)) {
            return true;
        }

        MessageHelpers.showMessage(mContext, R.string.toast_format_restricted);

        return false;
    }

    private void setOverride(int group, int[] tracks, boolean enableRandomAdaptation) {
        TrackSelection.Factory factory = tracks.length == 1 ? FIXED_FACTORY : (enableRandomAdaptation ? RANDOM_FACTORY : mTrackSelectionFactory);
        mOverride = new SelectionOverride(group, tracks);
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

    private View createDivider(Context context, ViewGroup root) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return inflater.inflate(R.layout.list_divider, root, false);
    }

    private void append(View view, ViewGroup root) {
        root.addView(view);
    }

    private CheckedTextView createRadioButton(Context context, CharSequence title, ViewGroup root) {
        return createDialogButton(context, R.layout.dialog_check_item_single, title, root);
    }

    private CheckedTextView createRadioButton(Context context, int titleResId, ViewGroup root) {
        return createDialogButton(context, R.layout.dialog_check_item_single, titleResId, root);
    }

    private CheckedTextView createCheckButton(Context context, int titleResId, ViewGroup root) {
        return createDialogButton(context, R.layout.dialog_check_item_multi, titleResId, root);
    }

    private CheckedTextView createDialogButton(Context context, int btnLayoutId, int titleResId, ViewGroup root) {
        String title = context.getResources().getString(titleResId);
        return createDialogButton(context, btnLayoutId, title, root);
    }

    private CheckedTextView createDialogButton(Context context, int btnLayoutId, CharSequence title, ViewGroup root) {
        LayoutInflater inflater = LayoutInflater.from(context);

        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();

        CheckedTextView view = (CheckedTextView) inflater.inflate(btnLayoutId, root, false);
        view.setBackgroundResource(selectableItemBackgroundResourceId);
        view.setText(title);
        view.setFocusable(true);
        view.setOnClickListener(this);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.dialog_text_size));

        return view;
    }
}
