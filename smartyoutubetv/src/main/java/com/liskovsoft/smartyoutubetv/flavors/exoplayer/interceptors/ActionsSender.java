package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SyncButtonsCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.ExoPreferences;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerFragment;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.DisplaySyncHelper;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.displaymode.UhdHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Pull actions from {@link PlayerFragment} and sends them back to the WebView
 */
public class ActionsSender {
    private static final String TAG = ActionsSender.class.getSimpleName();
    private final Context mContext;
    private final ExoInterceptor mInterceptor;
    private final String[] mPlayerButtons = {
            PlayerFragment.BUTTON_LIKE,
            PlayerFragment.BUTTON_DISLIKE,
            PlayerFragment.BUTTON_SUBSCRIBE,
            PlayerFragment.BUTTON_USER_PAGE,
            PlayerFragment.BUTTON_PREV,
            PlayerFragment.BUTTON_NEXT,
            PlayerFragment.BUTTON_BACK,
            PlayerFragment.BUTTON_SUGGESTIONS,
            PlayerFragment.TRACK_ENDED
    };

    public ActionsSender(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
    }

    /**
     * Sends {@link PlayerFragment} actions to the WebView (after delay)
     * @param intent contains {@link PlayerFragment} actions
     */
    public void bindActions(Intent intent) {
        if (intent == null) {
            Log.w(TAG, "ActionsSender: activity result cannot be null");
            return;
        }

        applyAutoframerate(intent);
        Map<String, Boolean> buttonStates = extractButtonStates(intent);
        SyncButtonsCommand myCommand = new SyncButtonsCommand(buttonStates);
        mInterceptor.updateLastCommand(myCommand);
    }

    private Map<String, Boolean> extractButtonStates(Intent intent) {
        Map<String, Boolean> result = new HashMap<>();
        for (String buttonId : mPlayerButtons) {
            // bypass buttons that not changed at all
            // details: http://4pda.ru/forum/index.php?act=qms&mid=1536707&t=4948881
            if (!intent.hasExtra(buttonId)) {
                continue;
            }

            boolean isChecked = intent.getBooleanExtra(buttonId, false);
            result.put(buttonId, isChecked);
        }
        
        // applyFixesForOldWebView(result);
        return result;
    }

    private void applyAutoframerate(Intent intent) {
        boolean autoframerateChecked = ExoPreferences.instance(mContext).getAutoframerateChecked();
        if (autoframerateChecked &&
            new DisplaySyncHelper(mContext).supportsDisplayModeChange()) {
            int displayModId = intent.getIntExtra(PlayerFragment.DISPLAY_MODE_ID, 0);
            new UhdHelper(mContext).setPreferredDisplayModeId(((Activity) mContext).getWindow(), displayModId, true);
        }
    }

    /**
     * Actually, on old devices not possible to stop the video. So skip to the next one.
     * @param buttons button states
     */
    //private void applyFixesForOldWebView(Map<String, Boolean> buttons) {
    //    if (VERSION.SDK_INT >= 21) {
    //        return;
    //    }
    //    if (mContext instanceof SmartYouTubeTVExoXWalk) {
    //        return;
    //    }
    //    // replace track_ended with button_next
    //    Boolean isEnded = buttons.get(PlayerActivity.TRACK_ENDED);
    //    isEnded = isEnded == null ? false : isEnded; // fix NPE
    //    if (isEnded) {
    //        buttons.put(PlayerActivity.TRACK_ENDED, false);
    //        buttons.put(PlayerActivity.BUTTON_NEXT, true);
    //    }
    //}
}
