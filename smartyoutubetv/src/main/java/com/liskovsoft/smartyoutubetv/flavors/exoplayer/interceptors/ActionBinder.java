package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.widget.Toast;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.SmartYouTubeTVExoXWalk;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.SyncButtonsCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.squareup.otto.Subscribe;

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
    private final String CLOSE_SUGGESTIONS = "action_close_suggestions";
    private final GenericStringResultReceiver mReceiver;
    private static int sCounter;

    private class GenericStringResultReceiver {


        public GenericStringResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEvent event) {
            String action = event.getResult();
            if (action.equals(CLOSE_SUGGESTIONS)) {
                new Handler(mContext.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "CLOSE_SUGGESTIONS: " + sCounter++, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public ActionBinder(Context context, ExoInterceptor interceptor) {
        mContext = context;
        mInterceptor = interceptor;
        mReceiver = new GenericStringResultReceiver();
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
        applyFixesForOldWebView(result);
        return result;
    }

    private void applyFixesForOldWebView(Map<String, Boolean> buttons) {
        if (VERSION.SDK_INT >= 21) {
            return;
        }
        if (mContext instanceof SmartYouTubeTVExoXWalk) {
            return;
        }
        // replace track_ended with button_next
        if (buttons.get(PlayerActivity.TRACK_ENDED)) {
            buttons.put(PlayerActivity.TRACK_ENDED, false);
            buttons.put(PlayerActivity.BUTTON_NEXT, true);
        }
    }
}
