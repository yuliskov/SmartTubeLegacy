package com.liskovsoft.smartyoutubetv.flavors.exoplayer.commands;

import com.liskovsoft.browser.Browser;
import com.liskovsoft.smartyoutubetv.youtubeinfoparser2.webviewstuff.GenericEventResourceInjector;

public abstract class GenericCommand {
    public abstract boolean call();
    protected void passToBrowser(String hugeFunction) {
        Browser.getBus().post(new GenericEventResourceInjector.JSResourceEvent(hugeFunction));
    }
}
