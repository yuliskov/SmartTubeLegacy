package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.support;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.util.HashMap;
import java.util.Map;

public class PlayerButtonsManager {
    private static final String TAG = PlayerButtonsManager.class.getSimpleName();
    private final ExoPlayerBaseFragment mPlayerFragment;
    private final PlayerView mExoPlayerView;
    private final ExoPreferences mPrefs;
    private final View mRootView;
    private boolean mListenerAdded;
    private Map<Integer, Boolean> mButtonStates;
    private Map<Integer, String> mOutputIdTagMapping;
    private Map<Integer, String> mInputIdTagMapping;

    public PlayerButtonsManager(ExoPlayerBaseFragment playerFragment) {
        mPlayerFragment = playerFragment;
        mRootView = mPlayerFragment.getView();

        if (mRootView == null) {
            throw new IllegalStateException("Fragment's root view is null");
        }

        mExoPlayerView = mRootView.findViewById(R.id.player_view);
        mPrefs = ExoPreferences.instance(playerFragment.getActivity());

        initButtonStates();
        initOutputIdTagMapping();
        initInputIdTagMapping();
    }

    private void initButtonStates() {
        mButtonStates = new HashMap<>();
    }

    public void syncButtonStates() {
        doCleanup();
        initWebButtons();
        initNextButton(); // force enable next button
        initDebugButton();
        initRepeatButton();
        mListenerAdded = true;
    }

    /**
     * Cleanup from the previous run values
     */
    private void doCleanup() {
        mButtonStates.clear();
    }

    private void initWebButtons() {
        Intent intent = mPlayerFragment.getIntent();

        Bundle extras = intent.getExtras();

        if (extras == null) {
            return;
        }

        for (Map.Entry<Integer, String> entry : mInputIdTagMapping.entrySet()) {
            boolean isButtonDisabled = !extras.containsKey(entry.getValue()); // no such button in data
            // NOTE: fix phantom subscribe/unsubscribe
            if (isButtonDisabled) {
                Integer btnId = entry.getKey();
                ToggleButtonBase btn = mRootView.findViewById(btnId);
                // NOTE: if no such state then mark button as disabled
                btn.resetState();
                btn.disableEvents(); // don't visually disable button, only disable events
                continue;
            }

            boolean isChecked = intent.getBooleanExtra(entry.getValue(), false);
            Integer btnId = entry.getKey();
            ToggleButtonBase btn = mRootView.findViewById(btnId);
            btn.enable(); // could be set unchecked by previous video
            btn.setChecked(isChecked);

            Log.d(TAG, "Init button: " + entry.getValue() + ": " + isChecked);
        }
    }

    /**
     * Id/Tag map for all buttons
     */
    private void initOutputIdTagMapping() {
        mOutputIdTagMapping = new HashMap<>();
        mOutputIdTagMapping.put(R.id.exo_user, ExoPlayerFragment.BUTTON_USER_PAGE);
        mOutputIdTagMapping.put(R.id.exo_like, ExoPlayerFragment.BUTTON_LIKE);
        mOutputIdTagMapping.put(R.id.exo_dislike, ExoPlayerFragment.BUTTON_DISLIKE);
        mOutputIdTagMapping.put(R.id.exo_subscribe, ExoPlayerFragment.BUTTON_SUBSCRIBE);
        mOutputIdTagMapping.put(R.id.exo_prev, ExoPlayerFragment.BUTTON_PREV);
        mOutputIdTagMapping.put(R.id.exo_next2, ExoPlayerFragment.BUTTON_NEXT);
        mOutputIdTagMapping.put(R.id.exo_suggestions, ExoPlayerFragment.BUTTON_SUGGESTIONS);
        mOutputIdTagMapping.put(R.id.exo_favorites, ExoPlayerFragment.BUTTON_FAVORITES);
    }

    /**
     * Id/Tag map for toggle buttons
     */
    private void initInputIdTagMapping() {
        mInputIdTagMapping = new HashMap<>();
        mInputIdTagMapping.put(R.id.exo_like, ExoPlayerFragment.BUTTON_LIKE);
        mInputIdTagMapping.put(R.id.exo_dislike, ExoPlayerFragment.BUTTON_DISLIKE);
        mInputIdTagMapping.put(R.id.exo_subscribe, ExoPlayerFragment.BUTTON_SUBSCRIBE);
    }

    public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
        final int id = button.getId();

        //if (id == R.id.exo_subscribe && !isChecked) {
        //    Log.d(TAG, "Hmm. Suspicious. Subscribe button has been unchecked. Canceling unsubscribe...");
        //    return;
        //}

        Log.d(TAG, "Button is checked: " + mOutputIdTagMapping.get(id) + ": " + isChecked);

        mButtonStates.put(id, isChecked);

        boolean isUserPageButton = id == R.id.exo_user && isChecked;
        boolean isSubtitleButton = id == R.id.exo_captions;
        boolean isNextButton = id == R.id.exo_next2 && isChecked;
        boolean isPrevButton = id == R.id.exo_prev && isChecked;
        boolean isSuggestions = id == R.id.exo_suggestions && isChecked;
        boolean isShareButton = id == R.id.exo_share;
        boolean isRepeatButton = id == R.id.exo_repeat;
        boolean isSpeedButton = id == R.id.exo_speed;
        boolean isBackButton = id == R.id.exo_back;
        boolean isFavorites = id == R.id.exo_favorites && isChecked;

        // TODO: move towards object oriented solution
        if (isSpeedButton) {
            mPlayerFragment.onSpeedClicked();
        } else if (isSubtitleButton) {
            View subBtn = mRootView.findViewById(R.id.exo_captions2); // we have two sub buttons with different ids
            if (subBtn == null) {
                Toast.makeText(mPlayerFragment.getActivity(), R.string.no_subtitle_msg, Toast.LENGTH_LONG).show();
            } else {
                mPlayerFragment.onClick(subBtn);
            }
        } else if (isRepeatButton) {
            mPlayerFragment.setRepeatEnabled(isChecked);
            mPrefs.setCheckedState(id, isChecked);
        } else if (isShareButton) {
            displayShareDialog();
        } else if (isUserPageButton || isSuggestions || isFavorites) {
            // loop video while user page or suggestions displayed
            mPlayerFragment.setRepeatEnabled(true);
            mPlayerFragment.onPlayerAction();
        } else if (isPrevButton) {
            if (!restartVideo()) {
                mPlayerFragment.onPlayerAction();
            }
        } else if (isNextButton) {
            mPlayerFragment.onPlayerAction();
        } else if (isBackButton) {
            mPlayerFragment.onPlayerAction(ExoPlayerFragment.BUTTON_BACK);
        }
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

    @TargetApi(17)
    private void displayShareDialog() {
        if (mPlayerFragment.getIntent() == null)
            return;

        String videoId = mPlayerFragment.getIntent().getStringExtra(ExoPlayerFragment.VIDEO_ID);
        Uri videoUrl = PlayerUtil.convertToFullUrl(videoId);
        PlayerUtil.showMultiChooser(mPlayerFragment.getActivity(), videoUrl);
    }

    @TargetApi(17)
    private void displayShareDialog2() {
        Intent openIntent = new Intent();
        openIntent.setAction(Intent.ACTION_VIEW);
        String videoId = mPlayerFragment.getIntent().getStringExtra(ExoPlayerFragment.VIDEO_ID);
        Uri videoUrl = PlayerUtil.convertToFullUrl(videoId);
        openIntent.setData(videoUrl);
        mPlayerFragment.startActivity(openIntent);
    }

    @TargetApi(17)
    private void displayShareDialogOld() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String videoId = mPlayerFragment.getIntent().getStringExtra(ExoPlayerFragment.VIDEO_ID);
        sendIntent.putExtra(Intent.EXTRA_TEXT, PlayerUtil.convertToFullUrl(videoId).toString());
        sendIntent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(sendIntent, mPlayerFragment.getResources().getText(R.string.send_to));
        chooserIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        mPlayerFragment.startActivity(chooserIntent);
    }

    public Intent createResultIntent() {
        Intent resultIntent;

        resultIntent = createStateIntent();

        resetState();

        return resultIntent;
    }

    private Intent createStateIntent() {
        Intent resultIntent = new Intent();

        // this buttons could be clicked only in the middle of the video
        // so return one of them
        for (int id : new int[]{R.id.exo_user, R.id.exo_suggestions, R.id.exo_favorites}) {
            Boolean checked = mButtonStates.get(id);
            mButtonStates.remove(id);
            if (checked != null && checked) { // one btn could be checked at a time
                resultIntent.putExtra(mOutputIdTagMapping.get(id), true);
                return resultIntent;
            }
        }

        for (Map.Entry<Integer, Boolean> entry : mButtonStates.entrySet()) {
            String realKey = mOutputIdTagMapping.get(entry.getKey());
            if (realKey == null) {
                continue;
            }
            resultIntent.putExtra(realKey, entry.getValue());
        }

        return resultIntent;
    }

    private void resetState() {
        mButtonStates.put(R.id.exo_favorites, false);
        mButtonStates.put(R.id.exo_suggestions, false);
        mButtonStates.put(R.id.exo_user, false);
        mButtonStates.put(R.id.exo_back, false);
        mButtonStates.put(R.id.exo_next2, false);
        mButtonStates.put(R.id.exo_prev, false);
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

        statsButton.setOnCheckedChangeListener(new ToggleButtonBase.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
                mPlayerFragment.showDebugView(isChecked);
                mPrefs.setCheckedState(button.getId(), isChecked);
            }
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

    public void syncRepeatButton() {
        ToggleButtonBase btn = mRootView.findViewById(R.id.exo_repeat);
        boolean checked = btn.getChecked();
        mPlayerFragment.setRepeatEnabled(checked);
    }
}
