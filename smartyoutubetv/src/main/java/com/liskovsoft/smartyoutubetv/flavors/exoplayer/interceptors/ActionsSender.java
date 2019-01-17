package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
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
    private final String[] mPlayerButtons = {
            ExoPlayerFragment.BUTTON_LIKE,
            ExoPlayerFragment.BUTTON_DISLIKE,
            ExoPlayerFragment.BUTTON_SUBSCRIBE,
            // ExoPlayerFragment.BUTTON_USER_PAGE,
            ExoPlayerFragment.BUTTON_PREV,
            ExoPlayerFragment.BUTTON_NEXT,
            ExoPlayerFragment.BUTTON_BACK,
            ExoPlayerFragment.BUTTON_SUGGESTIONS,
            ExoPlayerFragment.TRACK_ENDED
    };

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

    // TODO: cleanup
    //private void applyAutoframerate(Intent intent) {
    //    boolean autoframerateChecked = ExoPreferences.instance(mContext).getAutoframerateChecked();
    //    if (autoframerateChecked &&
    //        new DisplaySyncHelper(mContext).supportsDisplayModeChange()) {
    //        int displayModId = intent.getIntExtra(ExoPlayerFragment.DISPLAY_MODE_ID, 0);
    //        new UhdHelper(mContext).setPreferredDisplayModeId(((Activity) mContext).getWindow(), displayModId, true);
    //    }
    //}

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
