package com.liskovsoft.smartyoutubetv.flavors.exoplayer;

import android.content.Context;
import android.content.Intent;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericStringResultEvent;
import com.squareup.otto.Subscribe;
import java.util.Map;

public class ButtonStatesProcessor implements Runnable {
    private GenericStringResultReceiver mReceiver;
    private final Context mContext;
    private final Intent mIntent;
    private final Runnable mOnDone;
    private final String mJSCommandString = "app && app.onGenericStringResult(helpers.getButtonStates());";

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
        
    }

    private Map<String, Boolean> convertToObj(String result) {
        return null;
    }

    public ButtonStatesProcessor(Context context, Intent intent, Runnable onDone) {
        mContext = context;
        mIntent = intent;
        mOnDone = onDone;
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
