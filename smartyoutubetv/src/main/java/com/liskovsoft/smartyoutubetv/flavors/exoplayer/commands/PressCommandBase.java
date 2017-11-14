package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericBooleanResultEvent;
import com.liskovsoft.smartyoutubetv.misc.Helpers;
import com.squareup.otto.Subscribe;

import java.io.InputStream;
import java.util.Random;

public abstract class PressCommandBase extends GenericCommand {
    private final String mSimulateButtonPressFunction = "helpers.triggerEnter(helpers.$('%s'));\n";
    private final String mAsyncResultCallback = "helpers.isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);\n";
    private String mClassName;
    private GenericCommand mCallback;
    private GenericBooleanResultReceiver mGenericButtonReceiver;
    private final int mMyId = new Random().nextInt();

    private class GenericBooleanResultReceiver {
        public GenericBooleanResultReceiver() {
            Browser.getBus().register(this);
        }

        @Subscribe
        public void onGenericBooleanResult(GenericBooleanResultEvent event) {
            if (event.getId() != mMyId) {
                return;
            }

            if (!event.getResult()) {
                mCallback.call();
            }
            Browser.getBus().unregister(this);
        }
    }

    protected boolean pressButtonByClass(String className) {
        mClassName = className;
        String hugeFunction = combineAllTogetherByClass();
        passToBrowser(hugeFunction);
        return true;
    }


    protected void pressButtonByClassAsync(String className, final GenericCommand callback) {
        mClassName = className;
        mCallback = callback;
        mGenericButtonReceiver = new GenericBooleanResultReceiver();
        String hugeFunction = combineAllTogetherByClassAsync();
        passToBrowser(hugeFunction);
    }

    private String combineAllTogetherByClassAsync() {
        return combineAllTogetherByClass() + String.format(mAsyncResultCallback, mMyId);
    }

    private String combineAllTogetherByClass() {
        return String.format(mSimulateButtonPressFunction, mClassName);
    }
}
