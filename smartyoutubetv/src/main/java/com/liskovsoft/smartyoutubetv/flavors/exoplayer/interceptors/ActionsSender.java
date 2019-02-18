package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.liskovsoft.smartyoutubetv.common.mylogger.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SyncButtonsCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPlayerFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Pull actions from {@link ExoPlayerFragment} and sends them back to the WebView
 */
public class ActionsSender {
    private static final String TAG = ActionsSender.class.getSimpleName();
    private final Context mContext;
    private final ExoInterceptor mInterceptor;

    public ActionsSender(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
    }

    /**
     * Sends {@link ExoPlayerFragment} actions to the WebView (after delay)
     * @param intent contains {@link ExoPlayerFragment} actions
     */
    public void bindActions(Intent intent) {
        if (intent == null) {
            Log.d(TAG, "ActionsSender: activity result cannot be null");
            return;
        }

        Log.d(TAG, "Running SyncButtonsCommand");

        // applyAutoframerate(intent);
        Map<String, Boolean> buttonStates = extractButtonStates(intent);
        SyncButtonsCommand myCommand = new SyncButtonsCommand(buttonStates);
        mInterceptor.updateLastCommand(myCommand);
    }

    private Map<String, Boolean> extractButtonStates(Intent intent) {
        Map<String, Boolean> result = new HashMap<>();
        Bundle extras = intent.getExtras();
        for (String buttonId : extras.keySet()) {
            Object val = extras.get(buttonId);

            if (val instanceof Boolean) { // button states as boolean
                result.put(buttonId, (Boolean) val);
            }
        }
        
        return result;
    }
}
