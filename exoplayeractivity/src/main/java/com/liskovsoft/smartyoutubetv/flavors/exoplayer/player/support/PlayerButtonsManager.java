package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerBaseFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.helpers.PlayerUtil;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerButtonsManager {
    private static final String TAG = PlayerButtonsManager.class.getSimpleName();
    private final ExoPlayerBaseFragment mPlayerFragment;
    private final PlayerView mExoPlayerView;
    private final ExoPreferences mPrefs;
    private final View mRootView;
    private boolean mListenerAdded;

    private Map<Integer, OnChecked> mActions;
    private List<Integer> mStatefullButtons;
    private Map<Integer, String> mIdTagMapping;
    private Map<String, Boolean> mResultState;

    private interface OnChecked {
        boolean onChecked(boolean checked);
    }

    public PlayerButtonsManager(ExoPlayerBaseFragment playerFragment) {
        mPlayerFragment = playerFragment;
        mRootView = mPlayerFragment.getView();

        if (mRootView == null) {
            throw new IllegalStateException("Fragment's root view is null");
        }

        mExoPlayerView = mRootView.findViewById(R.id.player_view);
        mPrefs = ExoPreferences.instance(playerFragment.getActivity());

        initButtons();
    }

    private void initButtons() {
        mStatefullButtons = new ArrayList<>();

        mStatefullButtons.add(R.id.exo_like);
        mStatefullButtons.add(R.id.exo_dislike);
        mStatefullButtons.add(R.id.exo_subscribe);

        mResultState = new HashMap<>();

        mIdTagMapping = new HashMap<>();
        mIdTagMapping.put(R.id.exo_user, ExoPlayerFragment.BUTTON_USER_PAGE);
        mIdTagMapping.put(R.id.exo_like, ExoPlayerFragment.BUTTON_LIKE);
        mIdTagMapping.put(R.id.exo_dislike, ExoPlayerFragment.BUTTON_DISLIKE);
        mIdTagMapping.put(R.id.exo_subscribe, ExoPlayerFragment.BUTTON_SUBSCRIBE);
        mIdTagMapping.put(R.id.exo_prev, ExoPlayerFragment.BUTTON_PREV);
        mIdTagMapping.put(R.id.exo_next2, ExoPlayerFragment.BUTTON_NEXT);
        mIdTagMapping.put(R.id.down_catch_button, ExoPlayerFragment.BUTTON_SUGGESTIONS);
        mIdTagMapping.put(R.id.exo_suggestions, ExoPlayerFragment.BUTTON_SUGGESTIONS);
        mIdTagMapping.put(R.id.exo_favorites, ExoPlayerFragment.BUTTON_FAVORITES);
        mIdTagMapping.put(R.id.exo_back, ExoPlayerFragment.BUTTON_BACK);
        mIdTagMapping.put(R.id.exo_search, ExoPlayerFragment.BUTTON_SEARCH);

        mActions = new HashMap<>();
        // has state
        mActions.put(R.id.exo_like, (checked) -> false);
        mActions.put(R.id.exo_dislike, (checked) -> false);
        mActions.put(R.id.exo_subscribe, (checked) -> false);
        // no state
        mActions.put(R.id.exo_user, (checked) -> {
            // loop video while user page or suggestions displayed
            mPlayerFragment.setRepeatEnabled(true);
            return true;
        });
        mActions.put(R.id.down_catch_button, (checked) -> {
            // loop video while user page or suggestions displayed
            mPlayerFragment.setRepeatEnabled(true);
            return true;
        });
        mActions.put(R.id.exo_suggestions, (checked) -> {
            // loop video while user page or suggestions displayed
            mPlayerFragment.setRepeatEnabled(true);
            return true;
        });
        mActions.put(R.id.exo_favorites, (checked) -> {
            // loop video while user page or suggestions displayed
            mPlayerFragment.setRepeatEnabled(true);
            return true;
        });
        mActions.put(R.id.exo_prev, (checked) -> !restartVideo());
        mActions.put(R.id.exo_next2, (checked) -> true);
        mActions.put(R.id.exo_open_player, (checked) -> {openExternalPlayer(); return false;});
        mActions.put(R.id.exo_share, (checked) -> {displayShareDialog(); return false;});
        mActions.put(R.id.exo_speed, (checked) -> {mPlayerFragment.onSpeedClicked(); return false;});
        mActions.put(R.id.exo_captions, (checked) -> {
            View subBtn = mRootView.findViewById(R.id.exo_captions2); // we have two sub buttons with different ids
            if (subBtn == null) {
                Toast.makeText(mPlayerFragment.getActivity(), R.string.no_subtitle_msg, Toast.LENGTH_LONG).show();
            } else {
                mPlayerFragment.onClick(subBtn);
            }

            return false;
        });
        mActions.put(R.id.exo_repeat, (checked) -> {
            mPlayerFragment.setRepeatEnabled(checked);
            mPrefs.setCheckedState(R.id.exo_repeat, checked);
            return false;
        });
        mActions.put(R.id.exo_back, (checked) -> true);
        mActions.put(R.id.exo_search, (checked) -> true);
    }

    private boolean restartVideo() {
        SimpleExoPlayer player = mPlayerFragment.getPlayer();

        if (player != null) {
            long currentPosition = player.getCurrentPosition();
            if (currentPosition >= 10_000) {
                player.seekTo(0);
                return true;
            }
        }

        return false;
    }

    public void syncButtonStates(boolean isNewVideo) {
        if (isNewVideo) {
            doCleanup();
            initWebButtons();
            initNextButton(); // force enable next button
            initDebugButton();
            initRepeatButton();
            mListenerAdded = true;
        }

        syncRepeatButton();
    }

    /**
     * Cleanup from the previous run values
     */
    private void doCleanup() {
        mResultState.clear();
    }

    private void initWebButtons() {
        Intent intent = mPlayerFragment.getIntent();

        if (intent == null) {
            return;
        }

        Bundle extras = intent.getExtras();

        if (extras == null) {
            return;
        }

        for (Map.Entry<Integer, String> entry : mIdTagMapping.entrySet()) {
            boolean isButtonDisabled = !extras.containsKey(entry.getValue()); // no such button in data
            // NOTE: fix phantom subscribe/unsubscribe
            if (isButtonDisabled) {
                continue;
            }

            boolean isChecked = extras.getBoolean(entry.getValue(), false);
            Integer btnId = entry.getKey();
            ToggleButtonBase btn = mRootView.findViewById(btnId);

            if (btn != null) {
                btn.enable(); // could be set unchecked by previous video
                btn.setChecked(isChecked);
                Log.d(TAG, "Init button: " + entry.getValue() + ": " + isChecked);
            }
        }
    }

    public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
        int id = button.getId();
        String tag = mIdTagMapping.get(id);

        Log.d(TAG, "Button is checked: " + tag + ": " + isChecked );

        if (mActions.containsKey(id)) {
            OnChecked onChecked = mActions.get(id);
            boolean result = onChecked.onChecked(isChecked);

            if (tag != null) {
                mResultState.put(tag, isChecked);

                if (result) {
                    mPlayerFragment.onPlayerAction();
                }

                if (!mStatefullButtons.contains(id)) {
                    mResultState.remove(tag);
                }
            }
        }
    }

    @TargetApi(17)
    private void displayShareDialog() {
        if (mPlayerFragment.getIntent() == null)
            return;

        String videoId = mPlayerFragment.getIntent().getStringExtra(ExoPlayerFragment.VIDEO_ID);
        Uri videoUrl = PlayerUtil.convertToFullUrl(videoId);
        PlayerUtil.showMultiChooser(mPlayerFragment.getActivity(), videoUrl);
    }

    @TargetApi(17)
    private void openExternalPlayer() {
        if (mPlayerFragment.getIntent() == null)
            return;

        mPlayerFragment.openExternalPlayer(mPlayerFragment.getIntent());
    }

    public Intent createResultIntent() {
        Intent resultIntent;

        resultIntent = createStateIntent();

        return resultIntent;
    }

    private Intent createStateIntent() {
        Intent resultIntent = new Intent();

        for (Map.Entry<String, Boolean> entry : mResultState.entrySet()) {
            resultIntent.putExtra(entry.getKey(), entry.getValue());
        }

        return resultIntent;
    }

    private void initDebugButton() {
        ToggleButtonBase statsButton = mRootView.findViewById(R.id.exo_stats);

        initDebugListener(statsButton);

        statsButton.setChecked(mPrefs.getCheckedState(statsButton.getId()));
    }

    private void initDebugListener(ToggleButtonBase statsButton) {
        if (mListenerAdded) {
            return;
        }

        statsButton.setOnCheckedChangeListener((button, isChecked) -> {
            mPlayerFragment.showDebugView(isChecked);
            mPrefs.setCheckedState(button.getId(), isChecked);
        });
    }

    private void initRepeatButton() {
        ToggleButtonBase btn = mRootView.findViewById(R.id.exo_repeat);
        btn.setChecked(mPrefs.getCheckedState(btn.getId()));
    }

    // NOTE: example of visibility change listener
    private void initNextButton() {
        if (mListenerAdded) {
            return;
        }

        final View nextButton = mExoPlayerView.findViewById(R.id.exo_next2);
        nextButton.getViewTreeObserver().addOnGlobalLayoutListener(obtainSetButtonEnabledListener(nextButton));
    }

    private OnGlobalLayoutListener obtainSetButtonEnabledListener(final View nextButton) {
        return new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setButtonEnabled(true, nextButton);
            }
        };
    }

    private void setButtonEnabled(boolean enabled, View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(enabled);
        if (Util.SDK_INT >= 11) {
            setViewAlphaV11(view, enabled ? 1f : 0.3f);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @TargetApi(11)
    private void setViewAlphaV11(View view, float alpha) {
        view.setAlpha(alpha);
    }

    private void syncRepeatButton() {
        ToggleButtonBase btn = mRootView.findViewById(R.id.exo_repeat);
        boolean checked = btn.getChecked();
        mPlayerFragment.setRepeatEnabled(checked);
    }
}
