package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.custom;

import android.content.Intent;
import android.widget.Toast;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

import java.util.HashMap;
import java.util.Map;

public class PlayerPresenter {
    private final PlayerActivity mView;
    private final Map<Integer, Boolean> mButtonStates;
    private final Map<Integer, String> mIdTagMapping;

    public PlayerPresenter(PlayerActivity view) {
        mView = view;
        mButtonStates = new HashMap<>();
        mIdTagMapping = new HashMap<>();
        initIdTagMapping();
    }

    public void syncButtonStates() {
        Intent intent = mView.getIntent();
        for (Map.Entry<Integer, String> entry : mIdTagMapping.entrySet()) {
            boolean isButtonDisabled = !intent.getExtras().containsKey(entry.getValue());
            if (isButtonDisabled) {
                Integer btnId = entry.getKey();
                ToggleButtonBase btn = (ToggleButtonBase) mView.findViewById(btnId);
                // NOTE: if no such state then mark button as disabled
                btn.disable();
                continue;
            }
            boolean isChecked = intent.getBooleanExtra(entry.getValue(), false);
            Integer btnId = entry.getKey();
            ToggleButtonBase btn = (ToggleButtonBase) mView.findViewById(btnId);
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
        boolean isSubtitleButton = button.getId() == R.id.exo_captions && isChecked;
        boolean isNextButton = button.getId() == R.id.exo_next && isChecked;
        boolean isPrevButton = button.getId() == R.id.exo_prev && isChecked;
        boolean isSuggestions = button.getId() == R.id.exo_suggestions && isChecked;

        if (isSubtitleButton)
            Toast.makeText(mView, R.string.not_implemented_msg, Toast.LENGTH_LONG).show();

        if (isUserPageButton    ||
            isNextButton        ||
            isPrevButton        ||
            isSuggestions) {
            mView.doGracefulExit();
        }
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
}
