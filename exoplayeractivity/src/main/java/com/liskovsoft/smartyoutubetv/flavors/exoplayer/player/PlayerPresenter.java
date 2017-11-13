package com.liskovsoft.smartyoutubetv.flavors.exoplayer.player;

import android.content.Intent;
import android.widget.Toast;
import com.liskovsoft.exoplayeractivity.R;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.widgets.ToggleButtonBase;

import java.util.HashMap;

public class PlayerPresenter {
    private final PlayerActivity mView;
    private final HashMap<Integer, Boolean> mButtonStates;

    public PlayerPresenter(PlayerActivity view) {
        mView = view;
        mButtonStates = new HashMap<>();
    }

    public void onCheckedChanged(ToggleButtonBase button, boolean isChecked) {
        int id = button.getId();
        mButtonStates.put(id, isChecked);
        updateActivityResult();
        if (id == R.id.exo_user) {
            Toast.makeText(mView, "Go to the user page", Toast.LENGTH_LONG).show();
            //mView.doGracefulExit();
        }
    }

    private void updateActivityResult() {
        //Intent intent = mView.getActivityResult();
    }
}
