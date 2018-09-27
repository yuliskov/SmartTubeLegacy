package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GetButtonStatesCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Gets real button states from the WebView
 */
public class ActionsReceiver implements Runnable {
    private static final String TAG = ActionsReceiver.class.getSimpleName();
    private static final long RESPONSE_CHECK_DELAY_MS = 3000;
    private final Context mContext;
    private final Intent mIntent;
    private final Listener mListener;
    private boolean mRunOnce = false;
    private GenericCommand mStateCommand;

    interface Listener {
        void onDone();
        void onCancel();
    }

    public ActionsReceiver(Context context, Intent intent, Listener listener) {
        mContext = context;
        mIntent = intent;
        mListener = listener;
    }

    /**
     * Check that user didn't tapped back key before actual playback
     * @return true if user didn't tapped back key
     */
    private boolean checkIntent() {
        if (mIntent.hasExtra(PlayerActivity.BUTTON_SUBSCRIBE) ||
            mIntent.hasExtra(PlayerActivity.BUTTON_NEXT))
            return true;

        Log.w(TAG, "Action is cancelled. User tapped back key. Disable subsequent start of the player activity... ");
        // Uncomment next section to debug
        return false;
    }

    /**
     * Button states in JSON format
     */
    private void processJSON(String result) {
        Map<String, Object> states = convertToObj(result);
        syncWithIntent(states);
    }

    private void syncWithIntent(Map<String, Object> states) {
        for (Map.Entry<String, Object> entry : states.entrySet()) {
            Object val = entry.getValue();
            if (val instanceof Boolean || val == null)
                mIntent.putExtra(entry.getKey(), (Boolean) val);
            if (val instanceof String  || val == null)
                mIntent.putExtra(entry.getKey(), (String) val);
        }
    }

    // "{'.btn-selector': true, '.btn-selector2': false}"
    private Map<String, Object> convertToObj(String result) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(result, type);
    }

    @Override
    public void run() {
        mStateCommand = new GetButtonStatesCommand(new GetButtonStatesCommand.Callback() {
            @Override
            public void onResult(String result) {
                processJSON(result);
                doneResult();

                Log.d(TAG, "GetButtonStatesCommand: result");
            }
        });

        Log.d(TAG, "Before GetButtonStatesCommand");
        mStateCommand.call();

        startResponseCheck();
    }

    /**
     * Cancel callback if result contains wrong values (e.g. when player is closed)
     */
    private void doneResult() {
        if (runOnce()) // don't run this method if startResponseCheck() already executed
            return;

        if (checkIntent()) {
            mListener.onDone();
        } else {
            mListener.onCancel();
        }
    }

    /**
     * Cast from phone fix (unable to interrupt video).
     * Force do callback if there are no response from the WebView.
     */
    private void startResponseCheck() {
        new Handler(mContext.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (runOnce()) // don't run this method if doneResult() already executed
                    return;

                mListener.onDone();
            }
        }, RESPONSE_CHECK_DELAY_MS);
    }

    /**
     * Cast from phone fix (unable to interrupt video).
     * Force do callback if there are no response from the WebView.
     */
    private boolean runOnce() {
        boolean result = mRunOnce;
        mRunOnce = true;
        return result;
    }
}
