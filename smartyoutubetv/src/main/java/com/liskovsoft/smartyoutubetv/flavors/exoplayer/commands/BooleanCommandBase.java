package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericBooleanResultEvent;
import com.squareup.otto.Subscribe;

import java.util.Random;

public abstract class BooleanCommandBase extends GenericCommand {
    //private final String mButtonPressFunction = "helpers.triggerEnter('%s');\n";
    private final String mCallbackFunction = "helpers.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);\n";
    //private String mClassName;
    private GenericCommand mCallback;
    private GenericBooleanResultReceiver mGenericButtonReceiver;
    private final int mMyId = new Random().nextInt();
    //private String mAdditionalJSCode;

    private class GenericBooleanResultReceiver {
        public GenericBooleanResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericBooleanResult(GenericBooleanResultEvent event) {
            if (event.getId() != mMyId) {
                return;
            }

            // button is disabled
            if (!event.getResult()) {
                mCallback.call();
            }
            Browser.getBus().unregister(this);
        }
    }

    @Override
    public boolean call() {
        boolean result = false;
        String actionFunction = getActionFunction();
        if (actionFunction != null) {
            result = invokeAction();
        }
        //String callbackFunction = getCallbackFunction();
        //if (callbackFunction != null) {
        //    invokeActionAsync(callbackFunction);
        //}
        return result;
    }

    protected boolean invokeAction() {
        String hugeFunction = combineAllTogetherByClass();
        passToBrowser(hugeFunction);
        return true;
    }


    //protected void invokeActionAsync(final GenericCommand callback) {
    //    mCallback = callback;
    //    mGenericButtonReceiver = new GenericBooleanResultReceiver();
    //    String hugeFunction = combineAllTogetherByClassAsync();
    //    passToBrowser(hugeFunction);
    //}
    //
    //private String combineAllTogetherByClassAsync() {
    //    return combineAllTogetherByClass() + String.format(getCallbackFunction(), mMyId);
    //}

    //protected abstract String getCallbackFunction();
    protected abstract String getActionFunction();

    private String combineAllTogetherByClass() {
        return getActionFunction();
    }

    //protected void setAdditionalJSCode(String jsCode) {
    //    mAdditionalJSCode = jsCode;
    //}
}
