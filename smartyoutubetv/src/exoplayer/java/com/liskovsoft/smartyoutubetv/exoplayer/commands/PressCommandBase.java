package com.liskovsoft.smartyoutubetv.exoplayer.commands;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.GenericEventResourceInjector;

import java.util.concurrent.Callable;

public abstract class PressCommandBase implements Callable<Boolean> {
    private final String mTriggerEventFunction = "function triggerEvent(el, type, keyCode) {\n"
            + 	"console.log('triggerEvent called', el, type, keyCode)\n"
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
    private String mClassName;

    protected boolean pressButtonByClass(String className) {
        mClassName = className;
        String hugeFunction = combineAllTogetherByClass();
        passToBrowser(hugeFunction);
        return true;
    }

    private void passToBrowser(String hugeFunction) {
        Browser.getBus().post(new GenericEventResourceInjector.JSResourceEvent(hugeFunction));
    }

    private String combineAllTogetherByClass() {
        String formattedGetButtonFunction = String.format(mGetButtonFunction, mClassName);
        return mTriggerEventFunction + formattedGetButtonFunction + mSimulateButtonPressFunction;
    }
}
