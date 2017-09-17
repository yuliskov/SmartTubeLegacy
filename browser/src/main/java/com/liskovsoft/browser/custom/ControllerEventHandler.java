package com.liskovsoft.browser.custom;

import android.os.Bundle;

public interface ControllerEventHandler {
    void onControllerStart();
    void beforeSaveInstanceState(Bundle state);
    void beforeRestoreInstanceState(Bundle state);
}
