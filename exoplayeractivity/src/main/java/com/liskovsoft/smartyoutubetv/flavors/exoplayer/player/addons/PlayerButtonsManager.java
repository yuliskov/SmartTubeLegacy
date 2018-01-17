package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.addons;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

import java.util.HashMap;
import java.util.Map;

public class PlayerButtonsManager {
    private final PlayerActivity mPlayerActivity;
    private final Map<Integer, Boolean> mButtonStates;
    private final Map<Integer, String> mIdTagMapping;
    private final SimpleExoPlayerView mExoPlayerView;

    public PlayerButtonsManager(PlayerActivity playerActivity) {
        mPlayerActivity = playerActivity;
        mExoPlayerView = (SimpleExoPlayerView) mPlayerActivity.findViewById(R.id.player_view);
        mButtonStates = new HashMap<>();
        mIdTagMapping = new HashMap<>();
        initIdTagMapping();
        initNextButton(); // force enable next button
        initStatsButton();
    }

    public void syncButtonStates() {
        Intent intent = mPlayerActivity.getIntent();
        for (Map.Entry<Integer, String> entry : mIdTagMapping.entrySet()) {
            boolean isButtonDisabled = !intent.getExtras().containsKey(entry.getValue());
            if (isButtonDisabled) {
                Integer btnId = entry.getKey();
                ToggleButtonBase btn = (ToggleButtonBase) mPlayerActivity.findViewById(btnId);
                // NOTE: if no such state then mark button as disabled
                btn.disable();
                continue;
            }
            boolean isChecked = intent.getBooleanExtra(entry.getValue(), false);
            Integer btnId = entry.getKey();
            ToggleButtonBase btn = (ToggleButtonBase) mPlayerActivity.findViewById(btnId);
            btn.setChecked(isChecked);
        }
    }

    private void initIdTagMapping() {
        mIdTagMapping.put(R.id.exo_user, PlayerActivity.BUTTON_USER_PAGE);
        mIdTagMapping.put(R.id.exo_like, PlayerActivity.BUTTON_LIKE);
        mIdTagMapping.put(R.id.exo_dislike, PlayerActivity.BUTTON_DISLIKE);
        mIdTagMapping.put(R.id.exo_subscribe, PlayerActivity.BUTTON_SUBSCRIBE);
        mIdTagMapping.put(R.id.exo_prev, PlayerActivity.BUTTON_PREV);
        mIdTagMapping.put(R.id.exo_next, PlayerActivity.BUTTON_NEXT);
        mIdTagMapping.put(R.id.exo_suggestions, PlayerActivity.BUTTON_SUGGESTIONS);
    }

    public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
        int id = button.getId();
        mButtonStates.put(id, isChecked);

        boolean isUserPageButton = button.getId() == R.id.exo_user && isChecked;
        boolean isSubtitleButton = button.getId() == R.id.exo_captions;
        boolean isNextButton = button.getId() == R.id.exo_next && isChecked;
        boolean isPrevButton = button.getId() == R.id.exo_prev && isChecked;
        boolean isSuggestions = button.getId() == R.id.exo_suggestions && isChecked;
        boolean isShareButton = button.getId() == R.id.exo_share;

        if (isSubtitleButton) {
            View subBtn = mPlayerActivity.findViewById(R.id.exo_captions2); // we have two sub buttons with different ids
            if (subBtn == null) {
                Toast.makeText(mPlayerActivity, R.string.no_subtitle_msg, Toast.LENGTH_LONG).show();
            } else {
                mPlayerActivity.onClick(subBtn);
            }
        }

        if (isShareButton)
            displayShareDialog();

        if (isUserPageButton    ||
            isNextButton        ||
            isPrevButton        ||
            isSuggestions) {
            mPlayerActivity.doGracefulExit();
        }
    }

    @TargetApi(17)
    private void displayShareDialog() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String videoId = mPlayerActivity.getIntent().getStringExtra(PlayerActivity.VIDEO_ID);
        sendIntent.putExtra(Intent.EXTRA_TEXT, convertToUri(videoId).toString());
        sendIntent.setType("text/plain");
        Intent chooserIntent = Intent.createChooser(sendIntent, mPlayerActivity.getResources().getText(R.string.send_to));
        chooserIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        mPlayerActivity.startActivity(chooserIntent);
    }

    private Uri convertToUri(String videoId) {
        String url = String.format("https://www.youtube.com/watch?v=%s", videoId);
        return Uri.parse(url);
    }

    public Intent createResultIntent() {
        Intent resultIntent = new Intent();
        for (Map.Entry<Integer, Boolean> entry : mButtonStates.entrySet()) {
            String realKey = mIdTagMapping.get(entry.getKey());
            if (realKey == null)
                continue;
            resultIntent.putExtra(realKey, entry.getValue());
        }
        return resultIntent;
    }

    private void initStatsButton() {
        ToggleButtonBase statsButton = (ToggleButtonBase) mPlayerActivity.findViewById(R.id.exo_stats);
        statsButton.setOnCheckedChangeListener(new ToggleButtonBase.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
                mPlayerActivity.showDebugView(isChecked);
            }
        });
    }

    // NOTE: example of visibility change listener
    private void initNextButton() {
        final View nextButton = mExoPlayerView.findViewById(R.id.exo_next);
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
}
