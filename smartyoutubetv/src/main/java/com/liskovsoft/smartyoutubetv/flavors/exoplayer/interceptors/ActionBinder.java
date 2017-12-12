package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoXWalk;
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
            PlayerActivity.BUTTON_SUGGESTIONS,
            PlayerActivity.TRACK_ENDED
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
        // fixes for old android
        // applyFixesForOldWebView(result);
        return result;
    }

    private void applyFixesForOldWebView(Map<String, Boolean> buttons) {
        if (VERSION.SDK_INT >= 21) {
            return;
        }
        if (mContext instanceof SmartYouTubeTVExoXWalk) {
            return;
        }
        if (buttons.get(PlayerActivity.TRACK_ENDED)) {
            buttons.put(PlayerActivity.TRACK_ENDED, false);
            buttons.put(PlayerActivity.BUTTON_NEXT, true);
        }
    }
}
