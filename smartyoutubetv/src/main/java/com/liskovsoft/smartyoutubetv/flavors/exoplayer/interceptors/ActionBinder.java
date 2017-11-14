package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressButtonCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SimpleCombinedCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.NoneCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressBackCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressNextCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.PressPrevCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

public class ActionBinder {
    private final Context mContext;
    private final ExoInterceptor mInterceptor;

    public ActionBinder(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
    }

    public void bindActions(Intent intent) {
        GenericCommand command1 = createCommandForAction(intent);
        GenericCommand command2 = createCommandForButton(intent);

        mInterceptor.updateLastCommand(new SimpleCombinedCommand(command1, command2));
    }

    private GenericCommand createCommandForButton(Intent intent) {
        boolean checked = intent.getBooleanExtra(PlayerActivity.BUTTON_USER_PAGE, false);
        if (checked) {
            Toast.makeText(mContext, "Going to user page...", Toast.LENGTH_LONG).show();
            return new PressButtonCommand("pivot-channel-tile");
        }
        return null;
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
