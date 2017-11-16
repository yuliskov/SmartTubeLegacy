package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.widget.Toast;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GoogleConstants;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressButtonCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SimpleCombinedCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.NoneCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressBackCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressPrevCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SyncStateCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionBinder {
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private final HashMap<String, String> mMapping;

    public ActionBinder(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        mMapping = new HashMap<>();
        setUpMapping();
    }

    private void setUpMapping() {
        mMapping.put(PlayerActivity.BUTTON_LIKE, GoogleConstants.BUTTON_LIKE);
        mMapping.put(PlayerActivity.BUTTON_DISLIKE, GoogleConstants.BUTTON_DISLIKE);
        mMapping.put(PlayerActivity.BUTTON_SUBSCRIBE, GoogleConstants.BUTTON_SUBSCRIBE);
    }

    public void bindActions(Intent intent) {
        GenericCommand lastCommand = createCommandForAction(intent);
        GenericCommand commands = createCommandForButton(intent);

        mInterceptor.updateLastCommand(new SimpleCombinedCommand(commands, lastCommand));
    }

    private GenericCommand createCommandForButton(Intent intent) {
        ArrayList<GenericCommand> commands = new ArrayList<>();

        if (intent.getBooleanExtra(PlayerActivity.BUTTON_USER_PAGE, false)) {
            Toast.makeText(mContext, "Going to user page...", Toast.LENGTH_LONG).show();
            commands.add(new PressButtonCommand(GoogleConstants.BUTTON_USER_PAGE, "helpers.skipLastHistoryItem();"));
        }

        for (HashMap.Entry<String, String> entry : mMapping.entrySet()) {
            boolean isChecked = intent.getBooleanExtra(entry.getKey(), false);
            String buttonSelector = entry.getValue();
            commands.add(new SyncStateCommand(buttonSelector, isChecked));
        }

        return new SimpleCombinedCommand(commands.toArray(new GenericCommand[commands.size()]));
    }

    private String extractAction(Intent data) {
        return data.getStringExtra("action");
    }

    private GenericCommand createCommandForAction(Intent intent) {
        String action = extractAction(intent);
        GenericCommand command = new NoneCommand();
        switch (action) {
            case PlayerActivity.ACTION_NEXT:
                command = new PressNextCommand(new PressBackCommand());
                break;
            case PlayerActivity.ACTION_PREV:
                command = new PressPrevCommand(new PressBackCommand());
                break;
            case PlayerActivity.ACTION_BACK:
                command = new PressBackCommand();
                break;
            case PlayerActivity.ACTION_NONE:
                command = new NoneCommand();
                break;
        }
        return command;
    }
}
