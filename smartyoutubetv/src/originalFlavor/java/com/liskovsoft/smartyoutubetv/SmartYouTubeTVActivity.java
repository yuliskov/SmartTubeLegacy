package com.liskovsoft.smartyoutubetv;

import android.os.Bundle;
import com.liskovsoft.browser.Browser;
import com.liskovsoft.browser.Browser.EngineType;

public class SmartYouTubeTVActivity extends SmartYouTubeTVActivityBase {
    public SmartYouTubeTVActivity() {
        // we must set engine type as early as possible
        Browser.setEngineType(EngineType.WebView);
    }
}
