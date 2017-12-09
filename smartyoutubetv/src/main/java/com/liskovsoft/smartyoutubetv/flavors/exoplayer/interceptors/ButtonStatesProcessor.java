package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GoogleConstants;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericStringResultEventWithId;
import com.squareup.otto.Subscribe;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ButtonStatesProcessor implements Runnable {
    private GenericStringResultReceiver mReceiver;
    private final Context mContext;
    private final Intent mIntent;
    private final Runnable mOnDone;
    // TODO: fixme: remove delay (button uninitialized)
    private final String mStatesCommand = "JSON.stringify(helpers.getButtonStates())";
    private final String mDateCommand = "document.querySelector('.uploaded-date').innerHTML";
    //private final String mStatesCommand = "setTimeout(function(){app.onGenericStringResult(JSON.stringify(helpers.getButtonStates()));}, 2000);";
    //private final String mJSCommandString = "app.onGenericStringResult(JSON.stringify(helpers.getButtonStates()));";
    private final int mStatesId = new Random().nextInt();
    private final int mDateId = new Random().nextInt();

    public ButtonStatesProcessor(Context context, Intent intent, Runnable onDone) {
        mContext = context;
        mIntent = intent;
        mOnDone = onDone;
    }

    private class GenericStringResultReceiver {
        private int mRunCount;

        public GenericStringResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEventWithId event) {
            String result = event.getResult();

            if (event.getId() == mStatesId) {
                ButtonStatesProcessor.this.processJSON(result);
                mRunCount++;
            }

            if (event.getId() == mDateId) {
                ButtonStatesProcessor.this.processDate(result);
                mRunCount++;
            }

            if (mRunCount == 2) {
                Browser.getBus().unregister(this);
                mOnDone.run();
            }
        }
    }

    private void processDate(String result) {
        mIntent.putExtra(PlayerActivity.VIDEO_DATE, result);
    }

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

    private Map<String, String> initSelectorNameMap() {
        Map<String, String> map = new HashMap<>();
        map.put(GoogleConstants.BUTTON_SUBSCRIBE, PlayerActivity.BUTTON_SUBSCRIBE);
        return map;
    }

    @Override
    public void run() {
        mReceiver = new GenericStringResultReceiver();
        passToBrowser(mStatesCommand, mStatesId);
        passToBrowser(mDateCommand, mDateId);
    }

    protected void passToBrowser(String hugeFunction, int id) {
        Browser.getBus().post(new GenericEventResourceInjector.JSStringResultEvent(hugeFunction, id));
    }
}
