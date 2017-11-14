package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.flavors.exoplayer.youtubeinfoparser.parser.injectors.GenericEventResourceInjector.GenericBooleanResultEvent;
import com.squareup.otto.Subscribe;

import java.util.Random;

public abstract class PressCommandBase extends GenericCommand {
    private final String mTriggerEventFunction = "function triggerEvent(el, type, keyCode) {\n"
            + 	"console.log('triggerEvent called', el, type, keyCode);\n"
            + 	"if ('createEvent' in document) {\n"
            + 	        "// modern browsers, IE9+\n"
            + 	        "var e = document.createEvent('HTMLEvents');\n"
            + 	        "e.keyCode = keyCode;\n"
            + 	        "e.initEvent(type, false, true);\n"
            + 	        "el.dispatchEvent(e);\n"
            +     "} else {\n"
            +         "// IE 8\n"
            +         "var e = document.createEventObject();\n"
            +         "e.keyCode = keyCode;\n"
            +         "e.eventType = type;\n"
            +         "el.fireEvent('on'+e.eventType, e);\n"
            +     "}\n"
            + "}\n";
    private final String mGetButtonFunction = "var targetButton = document.getElementsByClassName('%s')[0];\n";
    private final String mSimulateButtonPressFunction = "triggerEvent(targetButton, 'keyup', 13); // simulate mouse/enter key press\n";
    private final String mAsyncResultCallback = "function hasClass(elem, klass) {\n"
            + "    return (\" \" + elem.className + \" \" ).indexOf( \" \"+klass+\" \" ) > -1;\n"
            + "}\n"
            + "\n"
            + "function isDisabled(elem) {\n"
            + "	return hasClass(elem, 'disabled');\n"
            + "}\n"
            + "\n"
            + "isDisabled(targetButton) && app && app.onGenericBooleanResult(false, %s);\n";
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
        String formattedGetButtonFunction = String.format(mGetButtonFunction, mClassName);
        return mTriggerEventFunction + formattedGetButtonFunction + mSimulateButtonPressFunction;
    }
}
