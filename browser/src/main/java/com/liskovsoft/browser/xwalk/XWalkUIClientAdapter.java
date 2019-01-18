package com.liskovsoft.browser.xwalk;

import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.WebChromeClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

public class XWalkUIClientAdapter extends XWalkUIClient {
    private WebChromeClient mChromeClient;

    public XWalkUIClientAdapter(XWalkView view) {
        super(view);
    }

    public void setWebChromeClient(WebChromeClient chromeClient) {
        mChromeClient = chromeClient;
    }

    @Override
    public boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId, ConsoleMessageType messageType) {
        if (mChromeClient != null) {
            mChromeClient.onConsoleMessage(new ConsoleMessage(message, sourceId, lineNumber, MessageLevel.valueOf(messageType.name())));
        }

        return super.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
    }
}
