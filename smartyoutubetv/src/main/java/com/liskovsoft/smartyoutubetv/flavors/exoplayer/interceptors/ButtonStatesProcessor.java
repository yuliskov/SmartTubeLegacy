package com.liskovsoft.smartyoutubetv.flavors.exoplayer.interceptors;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands.GoogleConstants;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.player.PlayerActivity;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.squareup.otto.Subscribe;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ButtonStatesProcessor implements Runnable {
    private GenericStringResultReceiver mReceiver;
    private final Context mContext;
    private final Intent mIntent;
    private final Runnable mOnDone;
    // TODO: fixme: remove delay
    private final String mJSCommandString = "setTimeout(function(){app.onGenericStringResult(JSON.stringify(helpers.getButtonStates()));}, 2000);";
    //private final String mJSCommandString = "app.onGenericStringResult(JSON.stringify(helpers.getButtonStates()));";
    private Map<String, String> mSelectorNameMap;

    private class GenericStringResultReceiver {
        public GenericStringResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericStringResult(GenericStringResultEvent event) {
            String result = event.getResult();
            ButtonStatesProcessor.this.processJSON(result);
            Browser.getBus().unregister(this);
        }
    }

    private void processJSON(String result) {
        Map<String, Boolean> states = convertToObj(result);
        syncWithIntent(states);
        mOnDone.run();
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

    public ButtonStatesProcessor(Context context, Intent intent, Runnable onDone) {
        mContext = context;
        mIntent = intent;
        mOnDone = onDone;
        mSelectorNameMap = initSelectorNameMap();
    }

    private Map<String, String> initSelectorNameMap() {
        Map<String, String> map = new HashMap<>();
        map.put(GoogleConstants.BUTTON_SUBSCRIBE, PlayerActivity.BUTTON_SUBSCRIBE);
        return map;
    }

    @Override
    public void run() {
        mReceiver = new GenericStringResultReceiver();
        passToBrowser(mJSCommandString);
    }

    protected void passToBrowser(String hugeFunction) {
        Browser.getBus().post(new GenericEventResourceInjector.JSResourceEvent(hugeFunction));
    }
}
