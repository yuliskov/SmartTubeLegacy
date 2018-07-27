package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GenericCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GetButtonStatesCommand;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GetDateCommand;
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
    private final Runnable mOnDone;
    private boolean mRunOnce = false;
    private int mRunCount;
    private GenericCommand mStateCommand;
    private GenericCommand mDateCommand;

    public ActionsReceiver(Context context, Intent intent, Runnable onDone) {
        mContext = context;
        mIntent = intent;
        mOnDone = onDone;
    }

    /**
     * Check that user didn't tapped back key before actual playback
     * @return true if user didn't tapped back key
     */
    private boolean checkIntent() {
        if (runOnce()) {
            return false;
        }

        if (mIntent.hasExtra(PlayerActivity.BUTTON_SUBSCRIBE) ||
            mIntent.hasExtra(PlayerActivity.BUTTON_NEXT))
            return true;

        Log.w(TAG, "Action is cancelled. User tapped back key. Disable subsequent start of the player activity... ");
        // Uncomment next section to debug
        // Toast.makeText(mContext, "Action is cancelled. Do nothing...", Toast.LENGTH_LONG).show();
        return false;
    }

    private void processDate(String result) {
        mIntent.putExtra(PlayerActivity.VIDEO_DATE, result);
    }

    /**
     * Button states in JSON format
     */
    private void processJSON(String result) {
        Map<String, Boolean> states = convertToObj(result);
        syncWithIntent(states);
    }

    private void syncWithIntent(Map<String, Boolean> states) {
        for (Map.Entry<String, Boolean> entry : states.entrySet()) {
            mIntent.putExtra(entry.getKey(), entry.getValue());
        }
    }

    // "{'.btn-selector': true, '.btn-selector2': false}"
    private Map<String, Boolean> convertToObj(String result) {
        Type type = new TypeToken<Map<String, Boolean>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(result, type);
    }

    @Override
    public void run() {
        mStateCommand = new GetButtonStatesCommand(new GetButtonStatesCommand.Callback() {
            @Override
            public void onResult(String result) {
                mRunCount++;
                processJSON(result);
                doneResult();
            }
        });
        mDateCommand = new GetDateCommand(new GetDateCommand.Callback() {
            @Override
            public void onResult(String result) {
                mRunCount++;
                processDate(result);
                doneResult();
            }
        });
        mStateCommand.call();
        mDateCommand.call();

        startResponseCheck();
    }

    /**
     * Cancel callback if result contains wrong values (player is closed)
     */
    private void doneResult() {
        if (mRunCount == 2 && checkIntent()) {
            mOnDone.run();
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
                if (!runOnce())
                    mOnDone.run();
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
