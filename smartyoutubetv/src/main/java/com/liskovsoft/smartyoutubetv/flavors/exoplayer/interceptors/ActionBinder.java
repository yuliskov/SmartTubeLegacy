package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SyncButtonsCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.util.HashMap;
import java.util.Map;

public class ActionBinder {
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private final String[] mPlayerButtons = {
            PlayerActivity.BUTTON_LIKE,
            PlayerActivity.BUTTON_DISLIKE,
            PlayerActivity.BUTTON_SUBSCRIBE,
            PlayerActivity.BUTTON_USER_PAGE,
            PlayerActivity.BUTTON_PREV,
            PlayerActivity.BUTTON_NEXT,
            PlayerActivity.BUTTON_BACK,
            PlayerActivity.BUTTON_SUGGESTIONS
    };

    public ActionBinder(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
    }

    public void bindActions(Intent intent) {
        Map<String, Boolean> buttonStates = extractButtonStates(intent);
        SyncButtonsCommand myCommand = new SyncButtonsCommand(buttonStates);
        mInterceptor.updateLastCommand(myCommand);
    }

    private Map<String, Boolean> extractButtonStates(Intent intent) {
        Map<String, Boolean> result = new HashMap<>();
        for (String buttonId : mPlayerButtons) {
            boolean isChecked = intent.getBooleanExtra(buttonId, false);
            result.put(buttonId, isChecked);
        }
        return result;
    }
}
