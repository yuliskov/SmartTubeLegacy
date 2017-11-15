package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GoogleConstants;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressButtonCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SimpleCombinedCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.NoneCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressBackCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressPrevCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.util.ArrayList;

public class ActionBinder {
    private final Context mContext;
    private final ExoInterceptor mInterceptor;

    public ActionBinder(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
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

        if (intent.getBooleanExtra(PlayerActivity.BUTTON_LIKE, false)) {
            commands.add(new PressButtonCommand(GoogleConstants.BUTTON_LIKE));
        }

        if (intent.getBooleanExtra(PlayerActivity.BUTTON_DISLIKE, false)) {
            commands.add(new PressButtonCommand(GoogleConstants.BUTTON_DISLIKE));
        }

        if (intent.getBooleanExtra(PlayerActivity.BUTTON_SUBSCRIBE, false)) {
            commands.add(new PressButtonCommand(GoogleConstants.BUTTON_SUBSCRIBE));
        }

        return new SimpleCombinedCommand(((GenericCommand[]) commands.toArray()));
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
