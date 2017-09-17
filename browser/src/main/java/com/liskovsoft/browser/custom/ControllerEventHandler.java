package com.liskovsoft.browser.custom;

import android.os.Bundle;

public interface ControllerEventHandler {
    void afterStart();
    void beforeSaveInstanceState(Bundle state);
}
