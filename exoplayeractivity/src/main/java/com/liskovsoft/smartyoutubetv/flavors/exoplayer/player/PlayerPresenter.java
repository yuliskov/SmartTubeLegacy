package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.widget.Toast;
import com.liskovsoft.exoplayeractivity.R;
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

    private void initIdTagMapping() {
        mIdTagMapping.put(R.id.exo_user, PlayerActivity.BUTTON_USER_PAGE);
        mIdTagMapping.put(R.id.exo_like, PlayerActivity.BUTTON_LIKE);
        mIdTagMapping.put(R.id.exo_dislike, PlayerActivity.BUTTON_DISLIKE);
        mIdTagMapping.put(R.id.exo_subscribe, PlayerActivity.BUTTON_SUBSCRIBE);
    }

    public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
        int id = button.getId();
        mButtonStates.put(id, isChecked);
        if (id == R.id.exo_user) {
            Toast.makeText(mView, "Go to the user page", Toast.LENGTH_LONG).show();
            mView.doGracefulExit(PlayerActivity.ACTION_NONE);
        }
    }

    public Intent createResultIntent() {
        Intent resultIntent = new Intent();
        for (Map.Entry<Integer, Boolean> entry : mButtonStates.entrySet()) {
            resultIntent.putExtra(mIdTagMapping.get(entry.getKey()), entry.getValue());
        }
        return resultIntent;
    }
}
